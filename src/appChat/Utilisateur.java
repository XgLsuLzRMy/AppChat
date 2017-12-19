package appChat;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Utilisateur implements Serializable {

	private static final long serialVersionUID = -2477264913071934274L;
	private String nom;
	private UtilisateurList listFollower;
	private UtilisateurList listFollow;
	private MessageList listMessages;
	private MessageListRecent listMessagesRecents; // les derniers messages recus
	private MessageList listMessagesUtilisateur; // les messages tweetés par l'utilisateur
	private MessageList listMessagesRetweetes; // pour ne pas retweeter 2 fois un même message
	private ArrayList<String> hashTagList; // Liste des hashtags auxquels l'utilisateur s'est abonne
	private String IPAddress;
	private transient Registry registry;

	public Utilisateur(String nom) {
		System.out.println("\nAttention : creation d'un utilisateur sans IPAddress\n");
		this.nom = nom;
		this.listFollower = new UtilisateurList();
		this.listFollow = new UtilisateurList();
		this.listMessages = new MessageList();
		this.listMessagesRecents = new MessageListRecent();
		this.listMessagesUtilisateur = new MessageList();
		this.listMessagesRetweetes = new MessageList();
		this.hashTagList = new ArrayList<String>();
		this.IPAddress = "localhost";
		this.resetRegistry();
	}

	public Utilisateur(String nom, String IPAddress) {
		this.nom = nom;
		this.listFollower = new UtilisateurList();
		this.listFollow = new UtilisateurList();
		this.listMessages = new MessageList();
		this.listMessagesRecents = new MessageListRecent();
		this.listMessagesUtilisateur = new MessageList();
		this.listMessagesRetweetes = new MessageList();
		this.hashTagList = new ArrayList<String>();
		this.IPAddress = IPAddress;
		this.resetRegistry();
	}

	public Registry getRegistry() {
		return this.registry;
	}

	public void resetRegistry() {
		System.out.print("\nUtilisateur " + this.getNom() + "/" + this.IPAddress + " resetRegistry... ");
		try {
			this.registry = LocateRegistry.getRegistry(this.IPAddress, 1099);
			System.out.println("OK");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public String getIPAddress() {
		return this.IPAddress;
	}

	public void setIPAddress(String IPAddress) {
		this.IPAddress = IPAddress;
	}

	public void follow(Utilisateur u) {
		if (!this.equals(u) && (u != null)) {
			this.listFollow.ajouterUtilisateur(u);
			u.ajouterFollower(this);
		}
	}

	public void unfollow(Utilisateur u) {
		if (u != null) {
			this.listFollow.retirerUtilisateur(u);
			u.retirerFollower(this);
		}
	}

	private void ajouterFollower(Utilisateur follower) {
		if (!this.equals(follower) && (follower != null)) {
			this.listFollower.ajouterUtilisateur(follower);
		}
	}

	private void retirerFollower(Utilisateur follower) {
		if (follower != null) {
			this.listFollower.retirerUtilisateur(follower);
		}
	}

	public void publierMessage(Message m) {
		this.listMessagesUtilisateur.ajouterMessage(m);
	}

	public void retweetMessage(Message m) {
		// TODO
	}

	public void setListMessagesRetweetes(MessageList listMessagesRetweetes) {
		this.listMessagesRetweetes = listMessagesRetweetes;
	}

	public MessageList getListMessagesRetweetes() {
		return this.listMessagesRetweetes;
	}

	public boolean equals(Utilisateur u) {
		if (u != null) {
			if (this.nom.equals(u.getNom())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public ArrayList<String> getHashTagList() {
		return this.hashTagList;
	}

	public void ajouterHashTag(String hashTag) {
		if (this.hashTagList.contains(hashTag) == false) {
			this.hashTagList.add(hashTag);
		}
	}

	public void retirerHashTag(String hashTag) {
		if (this.hashTagList.contains(hashTag)) {
			this.hashTagList.remove(hashTag);
		}
	}

	public String getNom() {
		return this.nom;
	}

	public String toString() {
		return this.nom + " (" + this.getFollowerCount() + " followers et "
				+ this.listMessagesUtilisateur.getNbMessage() + "msg )";
	}

	public int getFollowerCount() {
		return this.listFollower.length();
	}

	public int getFollowCount() {
		return this.listFollow.length();
	}

	public UtilisateurList getFollowerList() {
		return this.listFollower;
	}

	public void ajouterMessage(Message m) {
		Message m2 = this.listMessagesRecents.ajouterMessage(m);
		if (m2 != null) {
			this.listMessages.ajouterMessage(m);
		}
	}

	public void ajouterMessageUtilisateur(Message m) {
		this.listMessagesUtilisateur.ajouterMessage(m);
	}

	public MessageListRecent getListMessagesRecents() {
		return this.listMessagesRecents;
	}

	public MessageList getListMessages() {
		return this.listMessages;
	}

	public MessageList getListMessagesUtilisateur() {
		return this.listMessagesUtilisateur;
	}

	public void setFollowerList(UtilisateurList followerList) {
		this.listFollower = followerList;
	}

}
