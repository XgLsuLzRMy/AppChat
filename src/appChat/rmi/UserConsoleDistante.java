package appChat.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.LinkedList;
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
		System.out.println("Tentative de login " + nom + " " + mdp);
		Utilisateur u = null;
		try {
			if (UserConsoleDistante.appDistant.utilisateurDejaExistant(nom)) {
				u = UserConsoleDistante.appDistant.login(nom, mdp);
			} else {
				UserConsoleDistante.appDistant.ajouterUtilisateur(nom, mdp);
				u = UserConsoleDistante.appDistant.login(nom, mdp);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (u != null) {
			try {

				System.out.println("new utilisateurServeurImpl");

				// this.fenetreLogin.dispatchEvent(new WindowEvent(this.fenetreLogin,
				// WindowEvent.WINDOW_CLOSING));
				this.fenetreLogin.setVisible(false);

				this.utilisateurServeur = new UtilisateurServeurImpl(u, this);
				this.registry.rebind(u.getNom(), utilisateurServeur);

			} catch (RemoteException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("null");
		}
	}

	public UtilisateurList getListeUtilisateursConnectes() throws RemoteException {
		return UserConsoleDistante.appDistant.getListeUtilisateursConnectes();
	}

	/*public void run() throws RemoteException {
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
			while (str == "") {
				str = lecture.nextLine();
			}
			choix = Integer.parseInt(str);

			nom = "";
			nb = 0;
			switch (choix) {

			case 0:
				appDistant.logout(this.utilisateurServeur.getUtilisateur());
				break;

			case 1:
				System.out.print("Ecrire le contenu du tweet à publier : ");
				str = lecture.nextLine();
				while (str.isEmpty() == true) {
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
				while (nom.equals("")) {
					System.out.println("Donner le nom de l'utilisateur : ");
					nom = lecture.nextLine();
				}

				this.follow(nom);

				break;

			case 4:
				System.out.println(
						"\nVous suivez " + this.utilisateurServeur.getUtilisateur().getFollowCount() + " utilisateurs");
				System.out.println("Vous etes suivi par " + this.utilisateurServeur.getUtilisateur().getFollowerCount()
						+ " utilisateurs");
				System.out.println("Vous avez poste "
						+ this.utilisateurServeur.getUtilisateur().getListMessages().getNbMessage() + " messages");
				System.out.println("Vous avez fixe à "
						+ this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMaxMessage()
						+ " le nombre maximal de messages recents\n");
				break;

			case 5:
				System.out.println("Le nombre max de messages récents est actuellement de "
						+ this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMaxMessage());
				System.out.println("Donner le nouveau nombre max de messages récents : ");
				nb = Integer.parseInt(lecture.nextLine());
				if (nb >= 0) {
					this.utilisateurServeur.getUtilisateur().getListMessagesRecents().setNbMaxMessage(nb);

					try {
						UserConsoleDistante.appDistant.getUtilisateur(this.utilisateurServeur.getUtilisateur().getNom())
								.getListMessagesRecents().setNbMaxMessage(nb);
						System.out.println("OK");
					} catch (UtilisateurInexistantException e) {
						e.printStackTrace();
					}
				} else {
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
	}*/

	public void follow(String nom) {
		try {
			// On ajoute un nouveau follower sur le serveur
			UserConsoleDistante.appDistant.follow(this.utilisateurServeur.getUtilisateur().getNom(), nom);
		} catch (UtilisateurInexistantException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void unfollow(String nom) {
		try {
			UserConsoleDistante.appDistant.unfollow(this.utilisateurServeur.getUtilisateur().getNom(), nom);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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

	public Utilisateur getUtilisateur() throws RemoteException {
		return this.utilisateurServeur.getUtilisateur();
	}

	public ArrayList<String> getHashTagList() throws RemoteException {
		if(this.utilisateurServeur == null) {
			System.out.println("Attention hashTagList vide car le serveur utilisateur n'est pas encore instancié");
			return new ArrayList<String>();
		}else {
			return this.getUtilisateur().getHashTagList();
		}
	}
	
	public LinkedList<String> getHashTagsRecents() throws RemoteException {
		return UserConsoleDistante.appDistant.getHashTagsRecents();
	}
	
	public static void main(String[] args) {
		Registry registry = null;
		AppRMIServeur a = null;

		System.out.print("On cherche le registre... ");
		try {
			if (args.length > 0) {
				registry = LocateRegistry.getRegistry(args[0],1099);
			} else {
				registry = LocateRegistry.getRegistry(1099);
			}
			System.out.println("OK");
			System.out.print("On recupere le registre... ");
			a = (AppRMIServeur) registry.lookup("App");
			System.out.println("OK");

			new UserConsoleDistante(a, registry);

		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (NotBoundException ex) {
			ex.printStackTrace();
		}
	}

}
