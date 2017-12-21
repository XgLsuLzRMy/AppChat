package appChat.rmi;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import appChat.AppChat;
import appChat.Message;
import appChat.SauvegardeReguliere;
import appChat.Utilisateur;
import appChat.UtilisateurInexistantException;
import appChat.UtilisateurList;

/**
 * Implementation de AppRMIServeur. Permet de gerer le serveur RMI. C'est
 * l'objet recupere par le client via le registre.
 * 
 *
 */
public class AppRMIServeurImpl extends UnicastRemoteObject implements AppRMIServeur, Runnable {

	private static final long serialVersionUID = 1L;

	public static final int PORT_SERVEUR = 1099;

	public static AppRMIServeurImpl a;

	private AppChat app;
	public static Registry registry;
	public static UtilisateurList utilisateursConnectes = new UtilisateurList();
	/**
	 * Constructeur de AppRMIServeurImpl
	 * @param registry Le registre local sur lequel cet objet est bind
	 * @throws RemoteException
	 */
	public AppRMIServeurImpl(Registry registry) throws RemoteException {
		// super();
		super(PORT_SERVEUR);
		System.out.print("\n\tVerification de l'existence des fichiers... ");
		File f = new File("passwords");
		File f2 = new File("utilisateurs");
		if (f.exists() && !f.isDirectory() && f2.exists() && !f2.isDirectory()) {
			System.out.println("OK");
			this.app = new AppChat("utilisateurs", "passwords");
		} else {
			System.out.println("Pas de fichiers");
			this.app = new AppChat();
		}

		AppRMIServeurImpl.registry = registry;
	}

	public UtilisateurList getListeUtilisateursConnectes() throws RemoteException {
		return AppRMIServeurImpl.utilisateursConnectes;
	}

	@Override
	public void publieMessage(Message m) throws RemoteException {
		System.out.println("Nouveau message de " + m.getAuteur());
		UtilisateurList utilisateurANotifier = this.app.publieMessage(m);

		// On notifie les follower

		Iterator<Utilisateur> it = null;
		System.out.print("Notification des destinataires... ");

		it = utilisateurANotifier.getUtilisateurList().iterator();
		UtilisateurServeur uDistant = null;
		Utilisateur u = null;
		while (it.hasNext()) {

			u = it.next();
			// u.ajouterMessage(m);
			try {
				AppRMIServeurImpl.utilisateursConnectes.getUtilisateur(u.getNom()); // verification que
																					// l'utilisateur est
																					// connecte
				// uDistant = (UtilisateurServeur)
				// AppRMIServeurImpl.registry.lookup(u.getNom());
				uDistant = (UtilisateurServeur) u.getRegistry().lookup(u.getNom());
				uDistant.recevoirMessage(m);
			} catch (UtilisateurInexistantException e) {

			} catch (NotBoundException e) {
				e.printStackTrace();
			}

		}
		System.out.println("OK");

	}

	@Override
	public void ajouterUtilisateur(String nom, String mdp, String IPAddress, int port_utilisateur)
			throws RemoteException {
		System.out.print("Ajout d'un nouvel utilisateur " + nom + "/" + IPAddress + "... ");
		if (!this.utilisateurDejaExistant(nom)) {
			this.app.creerCompte(nom, mdp, IPAddress, port_utilisateur);
			System.out.println("OK");
		} else {
			System.out.println("deja existant");
		}

	}

	@Override
	public boolean utilisateurDejaExistant(String nom) throws RemoteException {
		try {
			AppChat.getUtilisateurList().getUtilisateur(nom);
			return true;
		} catch (UtilisateurInexistantException e) {
			return false;
		}
	}

	@Override
	public Utilisateur login(String nom, String mdp, String IPAddress, int port_utilisateur) throws RemoteException {
		System.out.print("Tentative de login de " + nom + " | " + IPAddress + ":" + port_utilisateur + "... ");
		if (this.app.verifierMdp(nom, mdp)) {
			System.out.println("OK");
			Utilisateur u = null;
			try {
				u = AppChat.getUtilisateurList().getUtilisateur(nom);
				u.setIPAddress(IPAddress, port_utilisateur);
				u.resetRegistry();
				AppRMIServeurImpl.utilisateursConnectes.ajouterUtilisateur(u);

				return u;
			} catch (UtilisateurInexistantException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			System.out.println("Refuse");
			return null;
		}
	}

	@Override
	public Utilisateur getUtilisateur(String nom) throws RemoteException, UtilisateurInexistantException {
		return AppChat.getUtilisateurList().getUtilisateur(nom);
	}

	@Override
	public UtilisateurList getUtilisateurList() {
		return AppChat.getUtilisateurList();
	}

	@Override
	public void logout(Utilisateur utilisateur) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void follow(String nom, String nom2) throws RemoteException, UtilisateurInexistantException {
		if (nom != nom2) {
			System.out.print(nom + " follow " + nom2 + "... ");
			Utilisateur u1 = this.getUtilisateur(nom);
			if (u1 != null) {
				Utilisateur u2 = this.getUtilisateur(nom2);
				u1.follow(u2);
				System.out.println("OK");
				try {
					Utilisateur u = AppRMIServeurImpl.utilisateursConnectes.getUtilisateur(nom2);
					System.out.print("Notification de " + nom2 + "... ");
					try {
						// UtilisateurServeur us = (UtilisateurServeur)
						// AppRMIServeurImpl.registry.lookup(nom2);
						UtilisateurServeur us = (UtilisateurServeur) u.getRegistry().lookup(nom2);
						System.out.println("OK");
						us.nouveauFollower(u1);

					} catch (NotBoundException e) {
						e.printStackTrace();
					}
				} catch (UtilisateurInexistantException e) {

				}
			} else {
				System.out.println("erreur : " + nom + " n'existe pas");
			}
		} else {
			System.out.println("impossible de se suivre soit meme");
		}
	}
	/**
	 * Accesseur permettant de recuperer AppChat
	 * @return
	 */
	private AppChat getApp() {
		return this.app;
	}

	@Override
	public LinkedList<String> getHashTagsRecents() throws RemoteException {
		return this.app.getHashTagsRecents();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unfollow(String nom, String nom2) throws RemoteException {
		System.out.print(nom + " unfollow " + nom2 + "... ");
		Utilisateur u1;
		try {
			u1 = this.getUtilisateur(nom);
			if (u1 != null) {
				Utilisateur u2 = this.getUtilisateur(nom2);
				u1.unfollow(u2);
				System.out.println("OK");
			} else {
				System.out.println("erreur : " + nom + " ou " + nom2 + " n'existe pas");
			}
		} catch (UtilisateurInexistantException e1) {
			System.out.println("erreur");
			e1.printStackTrace();
		}

	}



	@Override
	public void ajouterHashTag(String nom, String hashTag) throws RemoteException {
		try {
			Utilisateur u = this.getUtilisateur(nom);
			u.ajouterHashTag(hashTag);
			if (this.getListeUtilisateursConnectes().contains(u)) {
				try {
					// UtilisateurServeur us = (UtilisateurServeur)
					// AppRMIServeurImpl.registry.lookup(nom);
					UtilisateurServeur us = (UtilisateurServeur) u.getRegistry().lookup(nom);
					us.ajouterHashTag(hashTag);
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			}
		} catch (UtilisateurInexistantException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void retirerHashTag(String nom, String hashTag) throws RemoteException {
		try {
			Utilisateur u = this.getUtilisateur(nom);
			u.retirerHashTag(hashTag);
			if (this.getListeUtilisateursConnectes().contains(u)) {
				try {
					// UtilisateurServeur us = (UtilisateurServeur)
					// AppRMIServeurImpl.registry.lookup(nom);
					UtilisateurServeur us = (UtilisateurServeur) u.getRegistry().lookup(nom);
					us.retirerHashTag(hashTag);
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			}
		} catch (UtilisateurInexistantException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Utilisateur> chercherUtilisateur(String text) throws RemoteException {
		return this.app.chercherUtilisateur(text);
	}
	
	public static void main(String[] args) {
		try {
			Registry registry = null;
			try {
				String ipaddress = InetAddress.getLocalHost().toString();
				ipaddress = ipaddress.substring(ipaddress.indexOf("/") + 1, ipaddress.length());
				System.out.println("\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nAdresse ip locale du serveur : "
						+ ipaddress + "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			System.out.print("Recuperation du registre RMI... ");
			try {
				System.out.println("1");
				registry = LocateRegistry.createRegistry(PORT_SERVEUR);
				System.out.println("Registre cree !");
			} catch (ExportException ex) {
				System.out.println("2");
				// registry = LocateRegistry.getRegistry(PORT_SERVEUR);

				System.out.println("Registre recupere !");
			} catch (RemoteException ex) {
				System.out.println("3");
				ex.printStackTrace();
			}

			System.out.print("Instanciation du AppRMIServeur... ");
			AppRMIServeurImpl.a = new AppRMIServeurImpl(registry);
			System.out.println(" AppRMIServeurImpl instancie !");

			System.out.print("Enregistrement de l'application dans le registre... ");
			registry.rebind("App", AppRMIServeurImpl.a);
			System.out.println("OK");

			System.out.print("Instanciation de la classe qui check les utilisateurs connectes... ");
			CheckUtilisateursConnectes t = new CheckUtilisateursConnectes();
			System.out.println("OK");
			System.out.print("Lancement du check des utilisateurs connectes... ");
			t.start();
			System.out.println("OK");

			System.out.print("Instanciation de la classe qui sauvegarde regulierement... ");
			SauvegardeReguliere t2 = new SauvegardeReguliere(a.getApp());
			System.out.println("OK");
			System.out.print("Lancement de la sauvegarde reguliere... ");
			t2.start();
			System.out.println("OK");

		} catch (RemoteException ex) {
			ex.printStackTrace();
		}

	}

}
