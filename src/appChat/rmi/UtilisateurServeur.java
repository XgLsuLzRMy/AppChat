package appChat.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurList;

/**
 * UtilisateurServeur est un serveur RMI du cote du client pour pouvoir etre
 * contacte par le serveur. On peut ainsi etre notifier de l'arrivee d'un
 * message ou d'un nouveau follower.
 *
 */
public interface UtilisateurServeur extends Remote {

	public void recevoirMessage(Message message) throws RemoteException;

	public void nouveauFollower(Utilisateur follower) throws RemoteException;

	public Utilisateur getUtilisateur() throws RemoteException;

	public void setFollowerList(UtilisateurList followerList) throws RemoteException;

	public void ping() throws RemoteException;

	public void refreshAffichageListeutilisateursConnectes() throws RemoteException;

	public void ajouterHashTag(String hashtag) throws RemoteException;

	public void retirerHashTag(String hashtag) throws RemoteException;

}
