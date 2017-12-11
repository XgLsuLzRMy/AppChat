package appChat.rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurInexistantException;
import appChat.UtilisateurList;

public interface AppRMIServeur extends Remote {
	
	/**
	 * 
	 * @param nom le nom de l'utilisateur ﾃ� ajouter dans la liste des utilisateurs
	 * @param mdp le mot de passe correspondant ﾃ� cet utilisateur
	 */
	public void ajouterUtilisateur(String nom, String mdp) throws RemoteException;
	
	/**
	 * 
	 * @param nom le nom de l'utilisateur ﾃ� chercher dans la liste des utilisateurs inscrits (UtilisateurList dans AppChat)
	 * @return true si l'utilisateur possﾃｨde dﾃｩjﾃ� un compte, false sinon
	 */
	public boolean utilisateurDejaExistant(String nom) throws RemoteException;
	
	/**
	 * 
	 * @param nom le nom de l'utilisateur qui se connecte
	 * @param mdp le mot de passe de l'utilisateur qui se connecte
	 * @return les informations du compte sous la forme d'une variable Utilisateur si le mdp correspond au nom, sinon renvoie null
	 */
	public Utilisateur login(String nom, String mdp) throws RemoteException;
	/**
	 * 
	 * @param nom le nom de l'utilisateur souhaitﾃｩ
	 * @return l'utilisateur recherchﾃｩ
	 * @throws UtilisateurInexistantException 
	 */
	public Utilisateur getUtilisateur(String nom) throws RemoteException, UtilisateurInexistantException;
	
	/**
	 * 
	 * @param str
	 * @throws RemoteException
	 */
	public void publieMessage(Message m) throws RemoteException;

	public UtilisateurList getUtilisateurList() throws RemoteException;

	public void logout(Utilisateur utilisateur) throws RemoteException;
	
	/**
	 * L'utilisateur nom veut follow l'utilisateur nom2
	 * @param nom
	 * @param nom2
	 * @throws RemoteException
	 * @throws UtilisateurInexistantException 
	 */
	public void follow(String nom, String nom2) throws RemoteException, UtilisateurInexistantException;

		
}
