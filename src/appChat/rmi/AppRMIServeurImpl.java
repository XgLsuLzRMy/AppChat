package appChat.rmi;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import appChat.AppChat;



public class AppRMIServeurImpl extends UnicastRemoteObject implements AppRMIServeur{
	
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

	public static void main(String[] args) {
		try {
			Registry registry;
			if (args.length > 0) registry = LocateRegistry.getRegistry(args[0]);
			else registry = LocateRegistry.getRegistry();
			AppRMIServeurImpl a = new AppRMIServeurImpl(registry);
			registry.rebind("App", a);
			System.out.println("L'application est enregistre");
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

	
}
