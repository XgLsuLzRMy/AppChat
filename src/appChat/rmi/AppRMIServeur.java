package appChat.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurInexistantException;
import appChat.UtilisateurList;

/**
 * AppRMIServeur permet de creer un serveur RMI.
 * 
 *
 */
public interface AppRMIServeur extends Remote {

	/**
	 * Ajoute un utilisateur dans la liste des comptes utilisateurs de AppChat
	 * 
	 * @param nom
	 *            le nom de l'utilisateur a ajouter dans la liste des utilisateurs
	 * @param mdp
	 *            le mot de passe correspondant a cet utilisateur
	 */
	public void ajouterUtilisateur(String nom, String mdp, String IPAddress, int port_utilisateur)
			throws RemoteException;

	/**
	 * Permet de savoir si un utilisateur possede deja un compte.
	 * 
	 * @param nom
	 *            le nom de l'utilisateur a chercher dans la liste des utilisateurs
	 *            inscrits (UtilisateurList dans AppChat)
	 * @return true si l'utilisateur possede deja un compte, false sinon
	 */
	public boolean utilisateurDejaExistant(String nom) throws RemoteException;

	/**
	 * Permet a un client de recuperer ses informations (sous la forme d'un objet
	 * Utilisateur) si son mot de passe est correct.
	 *
	 * @param nom
	 *            le nom de l'utilisateur qui se connecte
	 * @param mdp
	 *            le mot de passe de l'utilisateur qui se connecte
	 * @return les informations du compte sous la forme d'un objet Utilisateur si le
	 *         mdp est correct, sinon renvoie null
	 */
	public Utilisateur login(String nom, String mdp, String IPAdress, int port_utilisateur) throws RemoteException;

	/**
	 * Permet de recuperer les information d'un utilisateur a partir de son nom
	 * donne en argument
	 * 
	 * @param nom
	 *            le nom de l'utilisateur souhaite
	 * @return l'utilisateur recherche
	 * @throws UtilisateurInexistantException
	 */
	public Utilisateur getUtilisateur(String nom) throws RemoteException, UtilisateurInexistantException;

	/**
	 * Permet d'envoyer un message a tous les destinataires comme defini dans
	 * AppChat
	 * 
	 * @param m
	 *            Le message a envoyer
	 * @throws RemoteException
	 */
	public void publieMessage(Message m) throws RemoteException;

	/**
	 * Permet de recuperer la liste des utilisateurs possedant un compte.
	 * 
	 * @return La liste des utilisateurs possedant un compte
	 * @throws RemoteException
	 */
	public UtilisateurList getUtilisateurList() throws RemoteException;

	/**
	 * Permet a un client de se deconnecte prorement.
	 * 
	 * @deprecated
	 * @param utilisateur
	 *            L'utilisateur qui souhaite se deconnecter
	 * @throws RemoteException
	 */
	public void logout(Utilisateur utilisateur) throws RemoteException;

	/**
	 * Permet a l'utilisateur nom de follow l'utilisateur nom2
	 *
	 * @param nom
	 *            Le nom de l'utilisateur qui follow
	 * @param nom2
	 *            Le nom de l'utilisateur qui aura un nouveau follower
	 * @throws RemoteException
	 * @throws UtilisateurInexistantException
	 */
	public void follow(String nom, String nom2) throws RemoteException, UtilisateurInexistantException;

	/**
	 * Permet de recuperer la liste des utilisateur actuellemnt connectes
	 * 
	 * @return La liste des utilisateurs connectes
	 * @throws RemoteException
	 */
	public UtilisateurList getListeUtilisateursConnectes() throws RemoteException;

	/**
	 * Permet a l'utilisateur nom de unfollow l'utilisateur nom2
	 *
	 * @param nom
	 *            Le nom de l'utilisateur qui unfollow
	 * @param nom2
	 *            Le nom de l'utilisateur qui perdra un follower
	 * @throws RemoteException
	 */
	public void unfollow(String nom, String nom2) throws RemoteException;

	/**
	 * Permet de recuperer la liste des hashtags recemment utilises
	 * 
	 * @return La liste des hashtags utilises recemment
	 * @throws RemoteException
	 */
	public LinkedList<String> getHashTagsRecents() throws RemoteException;

	/**
	 * Permet d'ajouter un hashtag donne en argument a la liste des hashtag d'un utilisateur dont le nom est donne en argument
	 * @param nom Le nom de l'utilisateur a qui le hashtag est ajoute
	 * @param hashTag Le hashtag qui est ajoute
	 * @throws RemoteException
	 */
	public void ajouterHashTag(String nom, String hashTag) throws RemoteException;
	/**
	 * Permet de retirer un hashtag donne en argument de la liste des hashtag d'un utilisateur dont le nom est donne en argument
	 * @param nom Le nom de l'utilisateur a qui le hashtag est retire
	 * @param hashTag Le hashtag qui est retire
	 * @throws RemoteException
	 */
	public void retirerHashTag(String nom, String hashTag) throws RemoteException;
	/**
	 * Permet de recuperer une liste d'utilisateur repondant a une recherche sur leur nom selon les regles definies dans AppChat.
	 * @param nom Le nom de l'utilisateur que l'on cherche
	 * @return Une liste des utilisateur repondant au critere de recherche
	 * @throws RemoteException
	 */
	public ArrayList<Utilisateur> chercherUtilisateur(String nom) throws RemoteException;

}
