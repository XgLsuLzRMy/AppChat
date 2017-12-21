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
	/**
	 * Permet d'etre notifier de l'arrivee d'un nouveau message m donne en argument
	 * 
	 * @param message
	 *            Le nouveau message
	 * @throws RemoteException
	 */
	public void recevoirMessage(Message message) throws RemoteException;

	/**
	 * Permet d'etre notifier de l'arrivee d'un nouveau follower
	 * 
	 * @param follower
	 *            Le nouveau follower
	 * @throws RemoteException
	 */
	public void nouveauFollower(Utilisateur follower) throws RemoteException;

	/**
	 * Accesseur permettant de recuperer les donnes de l'utilisateur
	 * 
	 * @return Les donnees de l'utilisateur sous forme d'un objet Utilisateur
	 * @throws RemoteException
	 */
	public Utilisateur getUtilisateur() throws RemoteException;

	/**
	 * 
	 * @param followerList
	 * @throws RemoteException
	 */
	public void setFollowerList(UtilisateurList followerList) throws RemoteException;

	/**
	 * Cette methode ne fait rien en soit : elle permet au serveur de verifier que
	 * l'utilisateur est bien joignable par le reseau
	 * 
	 * @throws RemoteException
	 */
	public void ping() throws RemoteException;

	/**
	 * Permet de mettre a jour l'affichage des utilisateurs connectes. Cette methode
	 * peut etre appelee par le serveur lorsqu'un utilisateur se deconnecte
	 * 
	 * @throws RemoteException
	 */
	public void refreshAffichageListeutilisateursConnectes() throws RemoteException;
	
	public void ajouterHashTag(String hashtag) throws RemoteException;

	public void retirerHashTag(String hashtag) throws RemoteException;

}
