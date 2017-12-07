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

	private AppRMIServeur appDistant;
	private UtilisateurServeur utilisateurServeur;

	public UserConsoleDistante(AppRMIServeur a, UtilisateurServeur utilisateurServeur) {
		this.appDistant = a;
		this.utilisateurServeur = utilisateurServeur;
	}

	public void run() throws RemoteException {
		int choix = 1;
		Scanner lecture = new Scanner(System.in);
		Message m = null;
		String str = "";
		String nom = "";
		int nb = 0;
		while (choix != 0) {
			System.out.println("Que voulez-vous faire ?");
			System.out.println("0 - Quitter");
			System.out.println("1 - Publier un tweet");
			System.out.println("2 - Voir les messages récents");
			System.out.println("3 - Follow un utilisateur");
			System.out.println("4 - Afficher les infos sur mon compte");
			System.out.println("5 - Modifier le nombre max de messages récents");
			str = lecture.nextLine();
			while(str == "") {
				str = lecture.nextLine();
			}
			choix = Integer.parseInt(str);
			
			nom = "";
			nb = 0;
			switch(choix) {
			
			case 0:
				appDistant.logout(this.utilisateurServeur.getUtilisateur());
				break;
				
			case 1:
				System.out.print("Ecrire le contenu du tweet à publier : ");
				str = lecture.nextLine();
				m = new Message(str, this.utilisateurServeur.getUtilisateur().getNom());
				this.appDistant.publieMessage(m);
				this.utilisateurServeur.getUtilisateur().ajouterMessageUtilisateur(m);
				break;
				
			case 2:
				System.out.println(this.utilisateurServeur.getUtilisateur().getListMessagesRecents());
				break;
				
			case 3:
				System.out.println("Donner le nom de l'utilisateur : ");
				nom = lecture.nextLine();
				while(nom.equals("")) {
					System.out.println("Donner le nom de l'utilisateur : ");
					nom = lecture.nextLine();
				}
				
				try {
					this.utilisateurServeur.getUtilisateur().follow(appDistant.getUtilisateur(nom));
					this.appDistant.follow(this.utilisateurServeur.getUtilisateur().getNom(), nom);
				} catch (UtilisateurInexistantException e) {
					e.printStackTrace();
				}
				
				break;
				
			case 4:
				System.out.println("\nVous suivez " + this.utilisateurServeur.getUtilisateur().getFollowCount() + " utilisateurs");
				System.out.println("Vous êtes suivi par " + this.utilisateurServeur.getUtilisateur().getFollowerCount() + " utilisateurs");
				System.out.println("Vous avez posté " + this.utilisateurServeur.getUtilisateur().getListMessages().getNbMessage() + " messages");
				System.out.println("Vous avez fixé à " + this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMaxMessage() + " le nombre maximal de messages récents\n");
				break;
				
			case 5:
				System.out.println("Le nombre max de messages récents est actuellement de " + this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMaxMessage());
				System.out.println("Donner le nouveau nombre max de messages récents : ");
				nb = Integer.parseInt(lecture.nextLine());
				if(nb>=0) {
					this.utilisateurServeur.getUtilisateur().getListMessagesRecents().setNbMaxMessage(nb);
					try {
						this.appDistant.getUtilisateur(this.utilisateurServeur.getUtilisateur().getNom()).getListMessagesRecents().setNbMaxMessage(nb);
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
			
			System.out.print("Entrer votre nom : ");
			nom = lecture.nextLine();
			// On ne peut pas avoir un nom vide
			while(nom.equals("")) {
				System.out.print("Entrer votre nom : ");
				nom = lecture.nextLine();
			}
			
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
			
			System.out.print("On instancie le serveur de l'utilisateur... ");
			utilisateurServeur = new UtilisateurServeurImpl(utilisateur);
			System.out.println("OK");
			System.out.print("On ajoute le serveur de l'utilisateur au registre... ");
			registry.rebind(nom, utilisateurServeur);
			System.out.println("OK");
			
			UserConsoleDistante console = new UserConsoleDistante(a, utilisateurServeur);
			console.run();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (NotBoundException ex) {
			ex.printStackTrace();
		}
		lecture.close();
	}

	public Utilisateur getUtilisateur() throws RemoteException{
		return this.utilisateurServeur.getUtilisateur();
	}

}
