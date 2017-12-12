package appChat.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurList;

public class UtilisateurServeurImpl extends UnicastRemoteObject implements UtilisateurServeur{

	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur;
	
	protected UtilisateurServeurImpl(Utilisateur utilisateur) throws RemoteException {
		super();
		this.utilisateur = utilisateur;
	}

	@Override
	public void recevoirMessage(Message message) throws RemoteException {
		System.out.println("\n\nNouveau message !\n" + message + "\n");
		
	}
	
	@Override
	public void nouveauFollower(Utilisateur follower) throws RemoteException{
		System.out.println("Nouveau follower " + follower.getNom());
		this.utilisateur.ajouterFollower(follower);
	}
	
	@Override
	public Utilisateur getUtilisateur() throws RemoteException {
		return this.utilisateur;
	}

	@Override
	public void ping() throws RemoteException {
		
	}

	@Override
	public Utilisateur setFollowerList(UtilisateurList followerList) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
