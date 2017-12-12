package appChat.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;

import appChat.AppChat;
import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurInexistantException;
import appChat.UtilisateurList;

public class AppRMIServeurImpl extends UnicastRemoteObject implements AppRMIServeur{

	private static final long serialVersionUID = 1L;
	private AppChat app;
	public static Registry registry;
	public static UtilisateurList utilisateursConnectes = new UtilisateurList();

	public AppRMIServeurImpl(Registry registry) throws RemoteException {
		super();
		this.app = new AppChat();
		AppRMIServeurImpl.registry = registry;
	}
	
	@Override
	public void publieMessage(Message m) throws RemoteException {
		System.out.println("Nouveau message de " + m.getAuteur());
		this.app.publieMessage(m);
		
		// On notifie les follower
		
		Iterator<Utilisateur> it = null;
		System.out.print("Notification des destinataires... ");
		try {
			it = this.getUtilisateur(m.getAuteur()).getFollowerList().getUtilisateurList().iterator();
			UtilisateurServeur uDistant = null;
			Utilisateur u = null;
			while(it.hasNext()) {
				try {
					u = it.next();
					u.ajouterMessage(m);
					try{
						AppRMIServeurImpl.utilisateursConnectes.getUtilisateur(u.getNom()); // verification que l'utilisateur est connecte
						uDistant = (UtilisateurServeur) AppRMIServeurImpl.registry.lookup(u.getNom());
						uDistant.recevoirMessage(m);
					}catch(UtilisateurInexistantException e) {
						
					}
					
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			}
			System.out.println("OK");
		} catch (UtilisateurInexistantException e1) {
			e1.printStackTrace();
		}
		
	}



	@Override
	public void ajouterUtilisateur(String nom, String mdp)throws RemoteException {
		System.out.print("Ajout d'un nouvel utilisateur " + nom + "... ");
		if(!this.utilisateurDejaExistant(nom)){
			this.app.creerCompte(nom, mdp);
			System.out.println("OK");
		}else {
			System.out.println("déjà existant");
		}

	}
	
	@Override
	public boolean utilisateurDejaExistant(String nom) throws RemoteException {
		try {
			AppChat.getUtilisateurList().getUtilisateur(nom);
			return true;
		}catch(UtilisateurInexistantException e) {
			return false;
		}
	}

	@Override
	public Utilisateur login(String nom, String mdp) throws RemoteException{
		System.out.print("Tentative de login de "+ nom + "... ");
		if(this.app.verifierMdp(nom, mdp)) {
			System.out.println("OK");
			Utilisateur u = null;
			try {
				u = AppChat.getUtilisateurList().getUtilisateur(nom);
				AppRMIServeurImpl.utilisateursConnectes.ajouterUtilisateur(u);
				return u;
			} catch (UtilisateurInexistantException e) {
				e.printStackTrace();
				return null;
			}
		}else {
			System.out.println("Refusé");
			return null;
		}
	}

	@Override
	public Utilisateur getUtilisateur(String nom)throws RemoteException, UtilisateurInexistantException  {
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
		System.out.print(nom + " follow " + nom2 + "... ");
		Utilisateur u1 = this.getUtilisateur(nom);
		if(u1 != null) {
			Utilisateur u2 = this.getUtilisateur(nom2);
			u1.follow(u2);
			System.out.println("OK");
			try{
				AppRMIServeurImpl.utilisateursConnectes.getUtilisateur(nom2);
				System.out.print("Notification de " + nom2 + "... ");
				try {
					UtilisateurServeur us = (UtilisateurServeur) AppRMIServeurImpl.registry.lookup(nom2);
					System.out.println("OK");
					us.nouveauFollower(u1);
					
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			}catch(UtilisateurInexistantException e) {
				
			}
		}else {
			System.out.println("erreur : " + nom + " n'existe pas");
		}
	}
	
	
	public static void main(String[] args) {
		try {
			Registry registry = null;
			System.out.print("Récupération du registre RMI... ");
			try {
				registry = LocateRegistry.createRegistry(1099);
				System.out.println("Registre crée !");
			} catch (ExportException ex) {
				registry = LocateRegistry.getRegistry(1099);
				System.out.println("Registre recupéré !");
			} catch (RemoteException ex) {
				ex.printStackTrace();
			}
			System.out.print("Instanciation du AppRMIServeur... ");
			AppRMIServeurImpl a = new AppRMIServeurImpl(registry);
			System.out.println("AppRMIServeurImpl instancié !");
			
			System.out.print("Enregistrement de l'application dans le registre... ");
			registry.rebind("App", a);
			System.out.println("OK");
			
			System.out.print("Instanciation de la classe qui check les utilisateurs connectes... ");
			CheckUtilisateursConnectes t = new CheckUtilisateursConnectes();
			System.out.println("OK");
			System.out.println("Lancement de la thread");
			t.start();
			
			
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
		
		
		
	}

	
}
