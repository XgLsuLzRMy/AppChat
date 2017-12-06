package appChat.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import appChat.Message;
import appChat.Utilisateur;

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
			choix = Integer.parseInt(lecture.nextLine());
			if (choix == 1) {
				System.out.print("Ecrire le contenu du tweet à publier : ");
				String str = lecture.nextLine();
				System.out.println("Utilisateur : " + this.utilisateur);
				appDistant.publieMessage(new Message(str, this.utilisateur.getNom()));
			}
		}
		lecture.close();
	}

	public static void main(String[] args) {
		Registry registry = null;
		Utilisateur utilisateur = null;
		AppRMIServeur a = null;
		UtilisateurServeurImpl utilisateurServeur = null;
		String mdp, nom;
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
					if(a.login(nom, mdp)) {
						utilisateur = utilisateurServeur.getUtilisateur();
					}else {
						System.out.println("erreur lors de la connection");
					}
					if (utilisateur != null) {
						correct = true;
					}
				}
			} else { // Si l'utilisateur n'a pas encore de compte
				System.out.println("Utilisateur inexistant --> Création d'un compte");
				System.out.print("Creer votre mot de passe : ");
				mdp = lecture.nextLine();
				a.ajouterUtilisateur(nom, mdp);
				if(a.login(nom, mdp)) { // Si le login a reussi alors le serveur a donner la liste des messages au serveurUtilisateur
					utilisateur = utilisateurServeur.getUtilisateur();
				}else {
					System.out.println("erreur lors de la connection");
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
