package appChat.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurInexistantException;

public class UserConsoleDistante {

	private Utilisateur utilisateur;
	private AppRMIServeur appDistant;

	public UserConsoleDistante(Utilisateur utilisateur, AppRMIServeur a) {
		this.utilisateur = utilisateur;
		this.appDistant = a;
	}

	public void run() throws RemoteException {
		int choix = 1;
		Scanner lecture = new Scanner(System.in);
		while (choix != 0) {
			System.out.println("Que voulez-vous faire ?");
			System.out.println("0 - Quitter");
			System.out.println("1 - Publier un tweet");
			System.out.println("2 - Voir les messages récents");
			System.out.println("3 - Follow un utilisateur");
			System.out.println("4 - Afficher les infos sur mon compte");
			System.out.println("5 - Modifier le nombre max de messages récents");
			String str = lecture.nextLine();
			while(str == "") {
				str = lecture.nextLine();
			}
			choix = Integer.parseInt(str);
			
			String nom = "";
			int nb = 0;
			switch(choix) {
			
			case 0:
				appDistant.logout(this.utilisateur);
				break;
				
			case 1:
				System.out.print("Ecrire le contenu du tweet à publier : ");
				str = lecture.nextLine();
				appDistant.publieMessage(new Message(str, this.utilisateur.getNom()));
				break;
				
			case 2:
				System.out.println(this.utilisateur.getListMessagesRecents());
				break;
				
			case 3:
				System.out.println("Donner le nom de l'utilisateur : ");
				nom = lecture.nextLine();
				while(nom.equals("")) {
					System.out.println("Donner le nom de l'utilisateur : ");
					nom = lecture.nextLine();
				}
				
				try {
					this.utilisateur.follow(appDistant.getUtilisateur(nom));
					this.appDistant.follow(this.utilisateur.getNom(), nom);
				} catch (UtilisateurInexistantException e) {
					e.printStackTrace();
				}
				
				break;
				
			case 4:
				System.out.println("\nVous suivez " + this.utilisateur.getFollowCount() + " utilisateurs");
				System.out.println("Vous êtes suivi par " + this.utilisateur.getFollowerCount() + " utilisateurs");
				System.out.println("Vous avez posté " + this.utilisateur.getListMessages().getNbMessage() + " messages");
				System.out.println("Vous avez fixé à " + this.utilisateur.getListMessagesRecents().getNbMaxMessage() + " le nombre maximal de messages récents\n");
				break;
				
			case 5:
				System.out.println("Le nombre max de messages récents est actuellement de " + this.utilisateur.getListMessagesRecents().getNbMaxMessage());
				System.out.println("Donner le nouveau nombre max de messages récents : ");
				nb = Integer.parseInt(lecture.nextLine());
				if(nb>=0) {
					this.utilisateur.getListMessagesRecents().setNbMaxMessage(nb);
					try {
						this.appDistant.getUtilisateur(this.utilisateur.getNom()).getListMessagesRecents().setNbMaxMessage(nb);
						System.out.println("OK");
					} catch (UtilisateurInexistantException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("Nombre incorrect, on ne change rien");
				}
			default:
				System.out.println(choix + " n'est pas un chox correct");
				break;
			}
		}
		lecture.close();
	}

	public static void main(String[] args) {
		Registry registry = null;
		Utilisateur utilisateur = null;
		AppRMIServeur a = null;
		UtilisateurServeurImpl utilisateurServeur = null;
		String mdp, nom = "";
		Scanner lecture = new Scanner(System.in);
		
		System.out.print("On cherche le registre... ");
		try {
			if (args.length > 0) {
				registry = LocateRegistry.getRegistry(args[0]);
			} else {
				registry = LocateRegistry.getRegistry(1099);
			}
			System.out.println("OK");
			System.out.print("On récupère le registre... ");
			a = (AppRMIServeur) registry.lookup("App");
			System.out.println("OK");
			
			System.out.print("On instancie le serveur de l'utilisateur... ");
			utilisateurServeur = new UtilisateurServeurImpl();
			System.out.println("OK\n");
			
			System.out.print("Entrer votre nom : ");
			nom = lecture.nextLine();
			// On ne peut pas avoir un nom vide
			while(nom.equals("")) {
				System.out.print("Entrer votre nom : ");
				nom = lecture.nextLine();
			}
			
			System.out.print("On ajoute le serveur de l'utilisateur au registre... ");
			registry.rebind(nom, utilisateurServeur);
			System.out.println("OK");
			
			
			boolean correct = false; // vaut false si le mdp ne correspond pas au nom et true sinon
			if (a.utilisateurDejaExistant(nom)) { // Si l'utilisateur existe déjà dans la liste d'utilisateurs alors on
													// lit le mot de passe et on essaie de se connecter
				System.out.println("Utilisateur existant --> Connexion au compte");
				while (!correct) { // on boucle pour avoir plusieurs tentatives (dans l'ihm il faudrait un moyen de
									// changer le nom si on s'est trompé)
					System.out.print("Entrer votre mot de passe : ");
					mdp = lecture.nextLine();
					utilisateur = a.login(nom, mdp);
					
					if (utilisateur != null) {
						correct = true;
					}else {
						System.out.println("erreur lors du login");
					}
				}
			} else { // Si l'utilisateur n'a pas encore de compte
				System.out.println("Utilisateur inexistant --> Création d'un compte");
				System.out.print("Creer votre mot de passe : ");
				mdp = lecture.nextLine();
				// On ne peut pas creer un compte avec mot de passe vide
				while(mdp.equals("")) {
					System.out.print("Creer votre mot de passe : ");
					mdp = lecture.nextLine();
				}
				a.ajouterUtilisateur(nom, mdp);
				utilisateur = a.login(nom, mdp);
				
				if (utilisateur != null) {
					correct = true;
				}else {
					System.out.println("erreur lors du login");
				}
			}
			
			UserConsoleDistante console = new UserConsoleDistante(utilisateur, a);
			console.run();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (NotBoundException ex) {
			ex.printStackTrace();
		}
		lecture.close();
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

}
