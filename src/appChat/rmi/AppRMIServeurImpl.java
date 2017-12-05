package appChat.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import appChat.AppChat;
import appChat.Utilisateur;

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
	public void publieMessage(String m) throws RemoteException {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void ajouterUtilisateur(String nom, String mdp) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean utilisateurDejaExistant(String nom) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Utilisateur login(String nom, String mdp) {
		// TODO Auto-generated method stub
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

			AppRMIServeurImpl a = new AppRMIServeurImpl(registry);

			registry.rebind("App", a);

			System.out.println("L'application est enregistre");
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}
}
