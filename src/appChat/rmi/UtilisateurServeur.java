package appChat.rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;

import appChat.Utilisateur;

public interface UtilisateurServeur extends Remote{
	
	/**
	 * Permet de récupérer son compte utilisateur u qui est passé en paramètre par le serveur
	 * @param u le compte utilisateur
	 */
	public void login(Utilisateur u) throws RemoteException;

}
