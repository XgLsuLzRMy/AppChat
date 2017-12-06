package appChat.rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import appChat.Utilisateur;

public interface UtilisateurServeur extends Remote{
	
	public Utilisateur getUtilisateur() throws RemoteException;
	
	public void recupererDonnees(Utilisateur utilisateur)throws RemoteException;

}
