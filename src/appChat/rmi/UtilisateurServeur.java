package appChat.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurList;

public interface UtilisateurServeur extends Remote {

	public void recevoirMessage(Message message) throws RemoteException;

	public void nouveauFollower(Utilisateur follower) throws RemoteException;

	public Utilisateur getUtilisateur() throws RemoteException;

	public void setFollowerList(UtilisateurList followerList) throws RemoteException;

	public void ping() throws RemoteException;

	public void refreshAffichageListeutilisateursConnectes() throws RemoteException;

}
