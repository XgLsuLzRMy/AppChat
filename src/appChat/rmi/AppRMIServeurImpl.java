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
import appChat.UtilisateurList;

public class AppRMIServeurImpl extends UnicastRemoteObject implements AppRMIServeur {

	private static final long serialVersionUID = 1L;
	private AppChat app;
	private Registry registry;

	public AppRMIServeurImpl(Registry registry) throws RemoteException {
		super();
		System.out.println("Dans le constructeur du serveur");
		this.app = new AppChat();
		System.out.println("Instance de AppChat créée !");
		this.registry = registry;
	}

	@Override
	public void publieMessage(Message m) throws RemoteException {
		this.app.publieMessage(m);
		
		// On notifie les follower
		
		Iterator<Utilisateur> it = this.getUtilisateur(m.getAuteur()).getFollowerList().getUtilisateurList().iterator();
		UtilisateurServeur u = null;
		while(it.hasNext()) {
			try {
				u = (UtilisateurServeur) this.registry.lookup(it.next().getNom());
				u.notification(m);
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
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
		if(AppChat.getUtilisateurList().getUtilisateur(nom) != null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Utilisateur login(String nom, String mdp) throws RemoteException{
		System.out.println("Tentative de login de "+ nom);
		if(this.app.verifierMdp(nom, mdp)) {
			System.out.println("utilisateur trouvé");
			System.out.print("On cherche le serveur de l'utilisateur dans le registre... ");
			
			return AppChat.getUtilisateurList().getUtilisateur(nom);
			
		}else {
			System.out.println("utilisateur pas trouvé");
			return null;
		}
	}

	@Override
	public Utilisateur getUtilisateur(String nom)throws RemoteException  {
		return AppChat.getUtilisateurList().getUtilisateur(nom);
	}



	public static void main(String[] args) {
		try {
			Registry registry = null;
			try {
				registry = LocateRegistry.createRegistry(1099);
			} catch (ExportException ex) {
				registry = LocateRegistry.getRegistry(1099);
			} catch (RemoteException ex) {
				ex.printStackTrace();
			}
			System.out.println("Registre crée !");
			
			AppRMIServeurImpl a = new AppRMIServeurImpl(registry);
			System.out.println("AppRMIServeurImpl instancié !");
			
			registry.rebind("App", a);
			System.out.println("L'application est enregistrée");
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
	public void follow(String nom, String nom2) throws RemoteException {
		this.getUtilisateur(nom).follow(this.getUtilisateur(nom2));
	}
}
