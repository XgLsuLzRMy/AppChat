package appChat.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import appChat.Utilisateur;

public class UtilisateurServeurImpl extends UnicastRemoteObject implements UtilisateurServeur{

	private static final long serialVersionUID = 1L;

	protected UtilisateurServeurImpl() throws RemoteException {
		super();
	}

	@Override
	public void login(Utilisateur u) throws RemoteException{
		// TODO Auto-generated method stub
		
	}
	
	

}
