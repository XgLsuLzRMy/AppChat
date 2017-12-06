package appChat.rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;

import appChat.Message;

public interface UtilisateurServeur extends Remote{
	
	public void notification(Message message) throws RemoteException;

}
