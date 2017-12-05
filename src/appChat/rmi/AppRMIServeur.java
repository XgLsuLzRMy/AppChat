package appChat.rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;

import appChat.Utilisateur;

public interface AppRMIServeur extends Remote {
	
	/**
	 * 
	 * @param nom le nom de l'utilisateur à ajouter dans la liste des utilisateurs
	 * @param mdp le mot de passe correspondant à cet utilisateur
	 */
	public void ajouterUtilisateur(String nom, String mdp);
	
	/**
	 * 
	 * @param nom le nom de l'utilisateur à chercher dans la liste des utilisateurs inscrits (UtilisateurList dans AppChat)
	 * @return true si l'utilisateur possède déjà un compte, false sinon
	 */
	public boolean utilisateurDejaExistant(String nom);
	
	/**
	 * 
	 * @param nom le nom de l'utilisateur qui se connecte
	 * @param mdp le mot de passe de l'utilisateur qui se connecte
	 * @return les informations du compte sous la forme d'une variable Utilisateur si le mdp correspond au nom, sinon renvoie null
	 */
	public Utilisateur login(String nom, String mdp);
	
	/**
	 * 
	 * @param nom le nom de l'utilisateur souhaité
	 * @return l'utilisateur recherché
	 */
	public Utilisateur getUtilisateur(String nom);
	
	/**
	 * 
	 * @param str
	 * @throws RemoteException
	 */
	public void publieMessage(String str) throws RemoteException;
		
}
