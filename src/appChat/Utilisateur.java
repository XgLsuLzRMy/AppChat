package appChat;

import java.io.Serializable;

public class Utilisateur implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nom;
	private UtilisateurList listFollower;
	private UtilisateurList listFollow;
	private MessageList listMessagesRecents;
	private MessageList listMessagesUtilisateur;
	private MessageList listMessagesRetweetes; // pour ne pas retweeter 2 fois un même message
	
	public Utilisateur(String nom) {
		this.nom = nom;
		this.listFollower = new UtilisateurList();
		this.listFollow = new UtilisateurList();
		this.listMessagesRecents =  new MessageList();
		this.listMessagesUtilisateur = new MessageList();
		listMessagesRetweetes = new MessageList();
	}
	
	public void follow(Utilisateur u){
		if (!this.equals(u) && (u!=null)){
			this.listFollow.ajouterUtilisateur(u);
			u.ajouterFollower(this);
		}
	}
	
	public void ajouterFollower(Utilisateur u) {
		if (!this.equals(u) && (u!=null)){
			this.listFollower.ajouterUtilisateur(u);
		}
	}
	
	public void publierMessage (Message m){
		this.listMessagesUtilisateur.ajouterMessage(m);
	}
	
	public void retweetMessage (Message m){
		//TODO
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
		this.listMessagesRecents.ajouterMessage(m);
	}
	
	public void ajouterMessageUtilisateur(Message m) {
		this.listMessagesUtilisateur.ajouterMessage(m);
	}
	
	public MessageList getListMessagesRecents() {
		return this.listMessagesRecents;
	}
	
	public MessageList getListMessages() {
		return this.listMessagesUtilisateur;
	}

}
