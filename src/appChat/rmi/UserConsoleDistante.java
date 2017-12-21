package appChat.rmi;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

/**
 * userConsoleDistante est la classe qui est executee par l'utilisateur pour
 * lancer le programme. La classe ouvre la fenetre d'acceuil. Elle va chercher
 * le registre et instancier un UtilisateurServeurImpl quand l'utilisateur aura
 * reussi sa connection avec le serveur
 *
 */
public class UserConsoleDistante {

	public static final int PORT_SERVEUR = 1099;
	public int port_utilisateur;

	public static AppRMIServeur appDistant;
	private UtilisateurServeur utilisateurServeur;
	private static Registry registry;
	private FenetreLogin fenetreLogin;

	public UserConsoleDistante(AppRMIServeur a) {
		this.utilisateurServeur = null;
		UserConsoleDistante.appDistant = a;
		// this.registry = registry;
		this.port_utilisateur = 1099;
		boolean reussi = false;
		while (!reussi) {
			try {
				// UserConsoleDistante.registry = LocateRegistry.getRegistry(PORT_UTILISATEUR);
				UserConsoleDistante.registry = LocateRegistry.getRegistry(this.port_utilisateur);
				reussi = true;
			} catch (RemoteException e) {
				this.port_utilisateur++;
				// e.printStackTrace();
			}
		}
		System.out.println("port_utilisateur = " + this.port_utilisateur);
		this.fenetreLogin = new FenetreLogin(this);
		this.fenetreLogin.pack();
		this.fenetreLogin.setVisible(true);

	}

	/**
	 * Permet de creer un compte sur le serveur si le nom n'est pas deja pris.
	 * 
	 * @param nom
	 *            Le nom du compte a creer
	 * @param mdp
	 *            Le mot de passe associe au compte
	 * @return true si le compte a ete cree, false sinon
	 */
	public boolean creerCompte(String nom, String mdp) {
		System.out.println("Tentative de creation de compte " + nom + " " + mdp + "... \n");

		String ipaddress = null;
		try {
			ipaddress = InetAddress.getLocalHost().toString();
			ipaddress = ipaddress.substring(ipaddress.indexOf("/") + 1, ipaddress.length());
			System.out.println("Adresse ip locale du client : " + ipaddress);

			try {
				if (UserConsoleDistante.appDistant.utilisateurDejaExistant(nom) == false) {
					UserConsoleDistante.appDistant.ajouterUtilisateur(nom, mdp, ipaddress, this.port_utilisateur);
					System.out.println("Creation compte OK");
					return true;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		return false;

	}

	/**
	 * Permet de connecte l'utilisateur au serveur, c'est a dire de recuperer ses
	 * message et de pouvoir en envoyer.
	 * 
	 * @param nom
	 *            Le nom de l'utilisateur qui se connecte
	 * @param mdp
	 *            Le mot de passe de connexion
	 */
	public void login(String nom, String mdp) {
		System.out.println("Tentative de login " + nom + " " + mdp);
		String ipaddress = null;
		try {
			ipaddress = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		ipaddress = ipaddress.substring(ipaddress.indexOf("/") + 1, ipaddress.length());
		System.out.println("Adresse ip locale du client : " + ipaddress);

		Utilisateur u = null;
		// Utilisateur u = new Utilisateur(nom, ipaddress);

		try {
			if (UserConsoleDistante.appDistant.utilisateurDejaExistant(nom)) {
				System.out.println("1");
				u = UserConsoleDistante.appDistant.login(nom, mdp, ipaddress, port_utilisateur);
				System.out.println("2");

				if (u != null) {
					this.fenetreLogin.dispose();

					try {

						System.out.println("new utilisateurServeurImpl");

						this.utilisateurServeur = new UtilisateurServeurImpl(u, this);

						// System.setProperty("java.rmi.server.hostname",this.utilisateurServeur.getUtilisateur().getIPAddress());
						System.out.println("On rebind");
						UserConsoleDistante.registry.rebind(u.getNom(), this.utilisateurServeur);

					} catch (RemoteException e) {
						e.printStackTrace();
					}

				}

			} else {
				// UserConsoleDistante.appDistant.ajouterUtilisateur(nom, mdp,
				// ipaddress);
				// u = UserConsoleDistante.appDistant.login(nom, mdp,
				// ipaddress);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Permet de contacter le serveur pour recuperer la liste des utilisateur
	 * connectes
	 * 
	 * @return La liste des utilisateurs connectes sous forme de UtilisateurList
	 * @throws RemoteException
	 */
	public UtilisateurList getListeUtilisateursConnectes() throws RemoteException {
		return UserConsoleDistante.appDistant.getListeUtilisateursConnectes();
	}

	/**
	 * Permet de contacter le serveur pour follow un autre utilisateur identifie par
	 * son nom donne en argument
	 * 
	 * @param nom
	 *            Le nom de l'utilisateur que l'on souhaite follow
	 */
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

	/**
	 * Permet de contacter le serveur pour unfollow un autre utilisateur identifie
	 * par son nom donne en argument
	 * 
	 * @param nom
	 *            Le nom de l'utilisateur que l'on souhaite unfollow
	 */
	public void unfollow(String nom) {
		try {
			UserConsoleDistante.appDistant.unfollow(this.utilisateurServeur.getUtilisateur().getNom(), nom);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de contacter le serveur pour envoyer un message
	 * 
	 * @param str
	 *            Le contenu du message
	 */
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

	/**
	 * Permet de recuperer les informations sur l'utilisateur
	 * 
	 * @return L'utilisateur connecte
	 * @throws RemoteException
	 */
	public Utilisateur getUtilisateur() throws RemoteException {
		return this.utilisateurServeur.getUtilisateur();
	}

	/**
	 * Permet de recuperer la liste des hashtags auxquels est abonne l'utilisateur
	 * 
	 * @return la liste des hashtags auxquels est abonne l'utilisateur sous forme de
	 *         ArrayList
	 * @throws RemoteException
	 */
	public ArrayList<String> getHashTagList() throws RemoteException {
		if (this.utilisateurServeur == null) {
			System.out.println("Attention hashTagList vide car le serveur utilisateur n'est pas encore instancié");
			return new ArrayList<String>();
		} else {
			return this.getUtilisateur().getHashTagList();
		}
	}

	/**
	 * Permet de recuperer la liste des hashtags recemment utilises par les
	 * utilisateurs de AppChat
	 * 
	 * @return la liste des hashtags recemment utilises
	 * @throws RemoteException
	 */
	public LinkedList<String> getHashTagsRecents() throws RemoteException {
		return UserConsoleDistante.appDistant.getHashTagsRecents();
	}

	/**
	 * Permet d'abonne l'utilisateur a un nouveau hashag
	 * 
	 * @param hashTag
	 *            Le hashtag auquel on souhaite s'abonner
	 */
	public void ajouterHashTag(String hashTag) {
		try {
			UserConsoleDistante.appDistant.ajouterHashTag(this.getUtilisateur().getNom(), hashTag);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de desabonne l'utilisateur a un hashag
	 * 
	 * @param hashTag
	 *            Le hashtag auquel on souhaite se desabonner
	 */
	public void retirerHashTag(String hashTag) {
		try {
			UserConsoleDistante.appDistant.retirerHashTag(this.getUtilisateur().getNom(), hashTag);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de contacter le serveur pour chercher un autre utilisateur a partir de
	 * son nom.
	 * 
	 * @param text
	 *            Le nom de l'utilisateur que l'on cherche. text n'est pas forcement
	 *            egale au nom de l'utilisateur : voir rechercherUtilisateur de
	 *            AppChat
	 * @return La liste des utilisateurs correspondant a la recherche
	 * @throws RemoteException
	 */
	public ArrayList<Utilisateur> chercherUtilisateur(String text) throws RemoteException {
		return UserConsoleDistante.appDistant.chercherUtilisateur(text);
	}

	/**
	 * Cherche sur le serveur l'utilisateur possedant le nom donne en argument
	 * 
	 * @param nom
	 *            Le nom de l'utilisateur que l'on cherche
	 * @return L'utilisateur que l'on cherche
	 */
	public Utilisateur getUtilisateur(String nom) {
		Utilisateur res = null;
		try {
			res = UserConsoleDistante.appDistant.getUtilisateur(nom);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (UtilisateurInexistantException e) {
		}
		return res;
	}

	/**
	 * La fonction qui est execute par l'utilisateur pour lander le programme
	 * 
	 * @param args
	 *            l'adresse IP du serveur
	 */
	public static void main(String[] args) {
		Registry registry = null;
		AppRMIServeur a = null;

		System.out.print("On cherche le registre distant... ");
		try {
			if (args.length > 0) {
				// registry = LocateRegistry.getRegistry(args[0], PORT_SERVEUR);
				registry = LocateRegistry.getRegistry(args[0]);
			} else {
				// registry = LocateRegistry.getRegistry(PORT_SERVEUR);
				registry = LocateRegistry.getRegistry();
			}
			System.out.println("OK");
			System.out.print("On recupere le serveur distant... ");
			a = (AppRMIServeur) registry.lookup("App");
			System.out.println("OK");

			new UserConsoleDistante(a);

		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (NotBoundException ex) {
			ex.printStackTrace();
		}
	}

}
