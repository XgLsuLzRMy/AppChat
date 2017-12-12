package appChat.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurList;
import appChat.ihm.FenetreChat;

public class UtilisateurServeurImpl extends UnicastRemoteObject implements UtilisateurServeur{

	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur;
	
	private FenetreChat fenetre;
	
	public UtilisateurServeurImpl(Utilisateur utilisateur) throws RemoteException {
		super();
		this.utilisateur = utilisateur;
		this.fenetre = new FenetreChat(this);
		this.fenetre.pack();
		this.fenetre.setVisible(true);
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
	public void setFollowerList(UtilisateurList followerList) throws RemoteException {
		this.utilisateur.setFollowerList(followerList);
	}
	
	
	

}
