package appChat;

import java.io.Serializable;

public class Utilisateur implements Serializable{

	private static final long serialVersionUID = -2477264913071934274L;
	private String nom;
	private UtilisateurList listFollower;
	private UtilisateurList listFollow;
	private MessageList listMessages;
	private MessageListRecent listMessagesRecents; // les derniers messages recus
	private MessageList listMessagesUtilisateur; // les messages tweetés par l'utilisateur
	private MessageList listMessagesRetweetes; // pour ne pas retweeter 2 fois un même message
	
	public Utilisateur(String nom) {
		this.nom = nom;
		this.listFollower = new UtilisateurList();
		this.listFollow = new UtilisateurList();
		this.listMessages = new MessageList();
		this.listMessagesRecents =  new MessageListRecent();
		this.listMessagesUtilisateur = new MessageList();
		listMessagesRetweetes = new MessageList();
	}
	
	public void follow(Utilisateur u){
		if (!this.equals(u) && (u!=null)){
			this.listFollow.ajouterUtilisateur(u);
			u.ajouterFollower(this);
		}
	}
	
	public void ajouterFollower(Utilisateur follower) {
		if (!this.equals(follower) && (follower!=null)){
			this.listFollower.ajouterUtilisateur(follower);
		}
	}
	
	public void publierMessage (Message m){
		this.listMessagesUtilisateur.ajouterMessage(m);
	}
	
	public void retweetMessage (Message m){
		//TODO
	}
	
	public void setListMessagesRetweetes(MessageList listMessagesRetweetes) {
		this.listMessagesRetweetes = listMessagesRetweetes;
	}
	
	public MessageList getListMessagesRetweetes() {
		return this.listMessagesRetweetes;
	}
	
	public boolean equals(Utilisateur u) {
		if(u!=null) {
			if(this.nom.equals(u.getNom())) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	public String getNom() {
		return this.nom;
	}
	
	public String toString() {
		return this.nom + " (" + this.getFollowerCount() + " followers et " + this.listMessagesUtilisateur.getNbMessage() + "msg )";
	}
	
	public int getFollowerCount() {
		return this.listFollower.length();
	}
	
	public int getFollowCount() {
		return this.listFollow.length();
	}
	
	public UtilisateurList getFollowerList(){
		return this.listFollower;
	}

	public void ajouterMessage(Message m) {
		Message m2 = this.listMessagesRecents.ajouterMessage(m);
		if(m2 != null) {
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

}
