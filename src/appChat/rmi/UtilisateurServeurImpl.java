package appChat.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurInexistantException;
import appChat.UtilisateurList;
import appChat.ihm.AutoRefreshFenetreChatThread;
import appChat.ihm.FenetreChat;

/**
 * UtilisateurServeurImpl est une implementation de UtilisateurServeur
 *
 */
public class UtilisateurServeurImpl extends UnicastRemoteObject implements UtilisateurServeur {

	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur;

	private FenetreChat fenetre;

	public UtilisateurServeurImpl(Utilisateur utilisateur, UserConsoleDistante uc) throws RemoteException {
		//super(UserConsoleDistante.PORT_UTILISATEUR);
		super();
		System.out.println(utilisateur);
		this.utilisateur = utilisateur;
		this.fenetre = new FenetreChat(this, uc);
		this.fenetre.pack();
		this.fenetre.setVisible(true);

		AutoRefreshFenetreChatThread t = new AutoRefreshFenetreChatThread(this.fenetre);
		t.start();
	}

	@Override
	public void recevoirMessage(Message message) throws RemoteException {
		System.out.println("\n\nNouveau message !\n" + message + "\n");
		this.utilisateur.ajouterMessage(message);
		this.fenetre.refreshMessages();
	}

	@Override
	public void nouveauFollower(Utilisateur follower) throws RemoteException {
		System.out.println("Nouveau follower " + follower.getNom());
		// this.utilisateur.ajouterFollower(follower); // deja fait dans
		// Utilisateur>follow
	}

	@Override
	public void ajouterHashTag(String hashtag) throws RemoteException {
		this.utilisateur.ajouterHashTag(hashtag);
	}

	@Override
	public void retirerHashTag(String hashtag) throws RemoteException {
		this.utilisateur.retirerHashTag(hashtag);
	}

	@Override
	public Utilisateur getUtilisateur() throws RemoteException {
		try {
			this.utilisateur = UserConsoleDistante.appDistant.getUtilisateur(this.utilisateur.getNom());
		} catch (UtilisateurInexistantException e) {
			e.printStackTrace();
		}
		return this.utilisateur;
	}

	@Override
	public void ping() throws RemoteException {

	}

	@Override
	public void setFollowerList(UtilisateurList followerList) throws RemoteException {
		this.utilisateur.setFollowerList(followerList);
	}

	@Override
	public void refreshAffichageListeutilisateursConnectes() {
		this.fenetre.refreshListeUtilisateursConnectes();
	}

}
