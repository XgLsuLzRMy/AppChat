package appChat.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.sun.corba.se.spi.activation.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import appChat.AppChat;

public class AppRMIServeurImpl extends UnicastRemoteObject implements
		AppRMIServeur {

	private static final long serialVersionUID = 1L;
	private AppChat app;
	private Registry registry;

	public AppRMIServeurImpl(Registry registry) throws RemoteException {
		super();
		this.app = new AppChat();
		this.registry = registry;
	}

	public void publieMessage(String m) throws RemoteException {
		// TODO Auto-generated method stub

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
