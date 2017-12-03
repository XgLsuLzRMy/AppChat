package appChat.rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AppRMIServeur extends Remote {

	public void publieMessage(String str) throws RemoteException;
		
}
