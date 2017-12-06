package appChat.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import appChat.Message;
import appChat.Utilisateur;

public class UtilisateurServeurImpl extends UnicastRemoteObject implements UtilisateurServeur{

	private static final long serialVersionUID = 1L;
	protected UtilisateurServeurImpl() throws RemoteException {
		super();
	}

	@Override
	public void notification(Message message) throws RemoteException {
		System.out.println("\n\nMessage recu !\n" + message + "\n");
	}


	

	
	
	

}
