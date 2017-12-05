package appChat.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import appChat.AppChat;
import appChat.Message;
import appChat.Utilisateur;

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
	}



	@Override
	public void ajouterUtilisateur(String nom, String mdp) {
		if(!this.utilisateurDejaExistant(nom)){
			this.app.creerCompte(nom, mdp);
		}

	}

	@Override
	public boolean utilisateurDejaExistant(String nom) {
		if(AppChat.getUtilisateurList().getUtilisateur(nom) != null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Utilisateur login(String nom, String mdp) {
		if(this.app.verifierMdp(nom, mdp)) {
			return AppChat.getUtilisateurList().getUtilisateur(nom);
		}
		return null;
	}

	@Override
	public Utilisateur getUtilisateur(String nom) {
		// TODO Auto-generated method stub
		return null;
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
}
