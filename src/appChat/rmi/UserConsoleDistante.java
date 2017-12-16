package appChat.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurInexistantException;
import appChat.UtilisateurList;
import appChat.ihm.FenetreLogin;

public class UserConsoleDistante {

	public static AppRMIServeur appDistant;
	private UtilisateurServeur utilisateurServeur;
	private Registry registry;
	private FenetreLogin fenetreLogin;

	public UserConsoleDistante(AppRMIServeur a, Registry registry) {
		this.utilisateurServeur = null;
		UserConsoleDistante.appDistant = a;
		this.registry = registry;
		this.fenetreLogin = new FenetreLogin(this);
		this.fenetreLogin.pack();
		this.fenetreLogin.setVisible(true);
		
	}
	
	public void login(String nom, String mdp) {
		System.out.println("Tentative de login "+nom+" "+mdp);
		Utilisateur u = null;
		try {
			if (UserConsoleDistante.appDistant.utilisateurDejaExistant(nom)) {
				u = UserConsoleDistante.appDistant.login(nom, mdp);
			}else {
				UserConsoleDistante.appDistant.ajouterUtilisateur(nom, mdp);
				u = UserConsoleDistante.appDistant.login(nom, mdp);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		if (u != null) {
			try {

				
				System.out.println("new utilisateurServeurImpl");

				//this.fenetreLogin.dispatchEvent(new WindowEvent(this.fenetreLogin, WindowEvent.WINDOW_CLOSING));
				this.fenetreLogin.setVisible(false);
				
				this.utilisateurServeur = new UtilisateurServeurImpl(u, this);
				this.registry.rebind(u.getNom(), utilisateurServeur);

				/*
				
				try {
					this.run();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
				*/
				
				
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		}else {
			System.out.println("null");
		}
		
		
		
		/*
		Scanner lecture = new Scanner(System.in);
		System.out.print("Entrer votre nom : ");
		nom = lecture.nextLine();
		// On ne peut pas avoir un nom vide
		while(nom.equals("")) {
			System.out.print("Entrer votre nom : ");
			nom = lecture.nextLine();
		}
		
		boolean correct = false; // vaut false si le mdp ne correspond pas au nom et true sinon
		try {
			if (UserConsoleDistante.appDistant.utilisateurDejaExistant(nom)) { // Si l'utilisateur existe déjà dans la liste d'utilisateurs alors on
													// lit le mot de passe et on essaie de se connecter
				System.out.println("Utilisateur existant --> Connexion au compte");
				while (!correct) { // on boucle pour avoir plusieurs tentatives (dans l'ihm il faudrait un moyen de
									// changer le nom si on s'est trompé)
					System.out.print("Entrer votre mot de passe : ");
					mdp = lecture.nextLine();
					utilisateur = UserConsoleDistante.appDistant.login(nom, mdp);
					
					if (utilisateur != null) {
						correct = true;
					}else {
						System.out.println("erreur lors du login");
					}
				}
			} else { // Si l'utilisateur n'a pas encore de compte
				System.out.println("Utilisateur inexistant --> Creation d'un compte");
				System.out.print("Creer votre mot de passe : ");
				mdp = lecture.nextLine();
				// On ne peut pas creer un compte avec mot de passe vide
				while(mdp.equals("")) {
					System.out.print("Creer votre mot de passe : ");
					mdp = lecture.nextLine();
				}
				UserConsoleDistante.appDistant.ajouterUtilisateur(nom, mdp);
				utilisateur = UserConsoleDistante.appDistant.login(nom, mdp);
				
				if (utilisateur != null) {
					correct = true;
				}else {
					System.out.println("erreur lors du login");
				}
			}
			
			System.out.print("On instancie le serveur de l'utilisateur... ");
			try {
				utilisateurServeur = new UtilisateurServeurImpl(utilisateur, this);
				System.out.print("On ajoute le serveur de l'utilisateur au registre... ");
				registry.rebind(utilisateur.getNom(), utilisateurServeur);
				System.out.println("OK");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println("OK");
			
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		lecture.close();
		*/
	}
	
	public UtilisateurList getListeUtilisateursConnectes() throws RemoteException {
		return UserConsoleDistante.appDistant.getListeUtilisateursConnectes();
	}
	
	public void run() throws RemoteException {
		int choix = 1;
		Scanner lecture = new Scanner(System.in);
		
		String str = "";
		String nom = "";
		int nb = 0;
		
		
		System.out.println("Messages recents : \n" + this.getUtilisateur().getListMessagesRecents());
		
		
		while (choix != 0) {
			System.out.println("Que voulez-vous faire ?");
			System.out.println("0 - Quitter");
			System.out.println("1 - Publier un tweet");
			System.out.println("2 - Voir les messages recents");
			System.out.println("3 - Follow un utilisateur");
			System.out.println("4 - Afficher les infos sur mon compte");
			System.out.println("5 - Modifier le nombre max de messages récents");
			System.out.println("6 - Afficher vos tweets");
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
				while(str.isEmpty() == true) {
					str = lecture.nextLine();
				}
				
				this.envoyerMessage(str);
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
					//this.utilisateurServeur.getUtilisateur().follow(appDistant.getUtilisateur(nom));
					// On ajoute un nouveau follower sur le serveur
					UserConsoleDistante.appDistant.follow(this.utilisateurServeur.getUtilisateur().getNom(), nom);
					// On met a jour la liste des follower en local
					this.utilisateurServeur.setFollowerList(UserConsoleDistante.appDistant.getUtilisateur(this.utilisateurServeur.getUtilisateur().getNom()).getFollowerList());
				} catch (UtilisateurInexistantException e) {
					e.printStackTrace();
				}
				
				break;
				
			case 4:
				System.out.println("\nVous suivez " + this.utilisateurServeur.getUtilisateur().getFollowCount() + " utilisateurs");
				System.out.println("Vous etes suivi par " + this.utilisateurServeur.getUtilisateur().getFollowerCount() + " utilisateurs");
				System.out.println("Vous avez poste " + this.utilisateurServeur.getUtilisateur().getListMessages().getNbMessage() + " messages");
				System.out.println("Vous avez fixe à " + this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMaxMessage() + " le nombre maximal de messages recents\n");
				break;
				
			case 5:
				System.out.println("Le nombre max de messages récents est actuellement de " + this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMaxMessage());
				System.out.println("Donner le nouveau nombre max de messages récents : ");
				nb = Integer.parseInt(lecture.nextLine());
				if(nb>=0) {
					this.utilisateurServeur.getUtilisateur().getListMessagesRecents().setNbMaxMessage(nb);
		
					try {
						UserConsoleDistante.appDistant.getUtilisateur(this.utilisateurServeur.getUtilisateur().getNom()).getListMessagesRecents().setNbMaxMessage(nb);
						System.out.println("OK");
					} catch (UtilisateurInexistantException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("Nombre incorrect, on ne change rien");
				}
				break;
			case 6:
				System.out.println(this.utilisateurServeur.getUtilisateur().getListMessagesUtilisateur());
				break;
			default:
				System.out.println(choix + " n'est pas un chox correct");
				break;
			}
		}
		lecture.close();
	}

	public void envoyerMessage(String str) {
		Message m;
		try {
			m = new Message(str, this.utilisateurServeur.getUtilisateur().getNom());
			try {
				UserConsoleDistante.appDistant.publieMessage(m);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) {
		Registry registry = null;
		AppRMIServeur a = null;
		
		System.out.print("On cherche le registre... ");
		try {
			if (args.length > 0) {
				registry = LocateRegistry.getRegistry(args[0]);
			} else {
				registry = LocateRegistry.getRegistry(1099);
			}
			System.out.println("OK");
			System.out.print("On recupere le registre... ");
			a = (AppRMIServeur) registry.lookup("App");
			System.out.println("OK");
			
			
			//UserConsoleDistante console = 
			new UserConsoleDistante(a, registry);
			
		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (NotBoundException ex) {
			ex.printStackTrace();
		}
	}

	public Utilisateur getUtilisateur() throws RemoteException{
		return this.utilisateurServeur.getUtilisateur();
	}

}
