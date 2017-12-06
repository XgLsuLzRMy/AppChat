package appChat.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import appChat.Utilisateur;

public class UtilisateurServeurImpl extends UnicastRemoteObject implements UtilisateurServeur{

	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur;

	protected UtilisateurServeurImpl() throws RemoteException {
		super();
		this.utilisateur = null;
	}


	

	
	
	

}
