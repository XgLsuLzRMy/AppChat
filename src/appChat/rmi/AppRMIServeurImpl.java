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

public class AppRMIServeurImpl extends UnicastRemoteObject implements AppRMIServeur {

	private static final long serialVersionUID = 1L;
	private AppChat app;
	private Registry registry;

	public AppRMIServeurImpl(Registry registry) throws RemoteException {
		super();
		this.app = new AppChat();
		this.registry = registry;
	}

	@Override
	public void publieMessage(Message m) throws RemoteException {
		this.app.publieMessage(m);
		
		// On notifie les follower
		
		Iterator<Utilisateur> it = null;
		try {
			it = this.getUtilisateur(m.getAuteur()).getFollowerList().getUtilisateurList().iterator();
			UtilisateurServeur u = null;
			while(it.hasNext()) {
				try {
					u = (UtilisateurServeur) this.registry.lookup(it.next().getNom());
					u.notification(m);
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			}
		} catch (UtilisateurInexistantException e1) {
			e1.printStackTrace();
		}
		
	}



	@Override
	public void ajouterUtilisateur(String nom, String mdp)throws RemoteException {
		if(!this.utilisateurDejaExistant(nom)){
			this.app.creerCompte(nom, mdp);
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
			try {
				return AppChat.getUtilisateurList().getUtilisateur(nom);
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
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
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
		Utilisateur u1 = this.getUtilisateur(nom);
		if(u1 != null) {
			Utilisateur u2 = this.getUtilisateur(nom2);
			u1.follow(u2);
		}
	}
}
