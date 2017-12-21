package appChat;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * 
 * Utilisateur permet de stocker toutes les informations relatives a un
 * Utilisateur de AppChat telles que son nom, ses followers, ses follow ou
 * encore ses messages.
 *
 */
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
	private int port_utilisateur;
	private transient Registry registry;

	/**
	 * Constructeur simple de Utilisateur
	 * 
	 * @param nom
	 *            Le nom de l'utilisateur
	 */
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
		this.port_utilisateur = 1099;
	}

	/**
	 * Cosntructeur complet de Utilisateur
	 * 
	 * @param nom
	 *            Le nom de l'utilisateur
	 * @param IPAddress
	 *            L'addresse IP avec laquelle il s'est connecte
	 * @param port_utilisateur
	 *            Le port sur lequel il s'est connecte
	 */
	public Utilisateur(String nom, String IPAddress, int port_utilisateur) {
		this.nom = nom;
		this.listFollower = new UtilisateurList();
		this.listFollow = new UtilisateurList();
		this.listMessages = new MessageList();
		this.listMessagesRecents = new MessageListRecent();
		this.listMessagesUtilisateur = new MessageList();
		this.listMessagesRetweetes = new MessageList();
		this.hashTagList = new ArrayList<String>();
		this.IPAddress = IPAddress;
		this.port_utilisateur = port_utilisateur;
		this.resetRegistry();
	}

	/**
	 * Acceseur permettant de recuperer le registre de l'utilisateur
	 * 
	 * @return Le registre de l'utilisateur
	 */
	public Registry getRegistry() {
		return this.registry;
	}

	/**
	 * Permet de recuperer le registre apres que l'utilisateur se soit reconnecte
	 */
	public void resetRegistry() {
		System.out.print("\nUtilisateur " + this.getNom() + "/" + this.IPAddress + " resetRegistry... ");
		try {
			// this.registry = LocateRegistry.getRegistry(this.IPAddress, PORT_UTILISATEUR);
			this.registry = LocateRegistry.getRegistry(this.IPAddress, this.port_utilisateur);
			System.out.println("OK");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Accesseur permettant de recuper l'addresse IP de l'utilisateur
	 * 
	 * @return l'addresse IP de l'utilisateur
	 */
	public String getIPAddress() {
		return this.IPAddress;
	}

	/**
	 * Setteur permettant de modifier l'addresse IP de l'utilisateur
	 * 
	 * @param IPAddress
	 *            La nouvelle IP
	 * @param port_utilisateur
	 *            Le nouveau port
	 */
	public void setIPAddress(String IPAddress, int port_utilisateur) {
		this.IPAddress = IPAddress;
		this.port_utilisateur = port_utilisateur;
	}

	/**
	 * Ajoute l'utilisateur u donne en argument a la liste des follow de
	 * l'utilisateur courant.
	 * 
	 * @param u
	 *            L'utilisateur a ajouter.
	 */
	public void follow(Utilisateur u) {
		if (!this.equals(u) && (u != null)) {
			this.listFollow.ajouterUtilisateur(u);
			u.ajouterFollower(this);
		}
	}

	/**
	 * Retire l'utilisateur u donne en argument de la liste des follow de
	 * l'utilisateur courant.
	 * 
	 * @param u
	 *            L'utilisateur a retirer.
	 */
	public void unfollow(Utilisateur u) {
		if (u != null) {
			this.listFollow.retirerUtilisateur(u);
			u.retirerFollower(this);
		}
	}

	/**
	 * Ajoute l'utilisateur follower donne en argument a la liste des follower de
	 * l'utilisateur courant.
	 * 
	 * @param follower
	 *            L'utilisateur a ajouter.
	 */
	private void ajouterFollower(Utilisateur follower) {
		if (!this.equals(follower) && (follower != null)) {
			this.listFollower.ajouterUtilisateur(follower);
		}
	}

	/**
	 * Retire l'utilisateur follower donne en argument de la liste des follower de
	 * l'utilisateur courant.
	 * 
	 * @param follower
	 *            L'utilisateur a retirer.
	 */
	private void retirerFollower(Utilisateur follower) {
		if (follower != null) {
			this.listFollower.retirerUtilisateur(follower);
		}
	}

	/**
	 * Ajoute le message m donne en argument a la liste des messages de
	 * l'utilisateur.
	 * 
	 * @param m
	 *            Le message a ajouter.
	 */
	public void publierMessage(Message m) {
		this.listMessagesUtilisateur.ajouterMessage(m);
	}

	public void retweetMessage(Message m) {
		// TODO
	}

	/**
	 * Setteur permettant de modifier la liste des messages retweetes
	 * 
	 * @param listMessagesRetweetes
	 *            La nouvelle liste
	 */
	public void setListMessagesRetweetes(MessageList listMessagesRetweetes) {
		this.listMessagesRetweetes = listMessagesRetweetes;
	}

	/**
	 * Accesseur permettant de recuperer la liste des messages retweetes
	 * 
	 * @return La liste des messages retweetes.
	 */
	public MessageList getListMessagesRetweetes() {
		return this.listMessagesRetweetes;
	}

	/**
	 * Compare deux utilisateurs sur la base de leur nom. Deux utilisateurs sont
	 * egaux si ils possedent le meme nom.
	 * 
	 * @param u
	 *            L'utilisateur a comparer a l'Utilisateur courant
	 * @return true si les Utilisateurs sont egaux, false sinon
	 */
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

	/**
	 * Accesseur permettant de recuperer la liste des hashtags auxquels est abonne
	 * l'utilisateur
	 * 
	 * @return La liste des hashtags sous forme d'un ArrayList
	 */
	public ArrayList<String> getHashTagList() {
		return this.hashTagList;
	}

	/**
	 * Ajoute un hashtag a la liste des abonnements de l'utilisateur si cet hashtag
	 * n'y est pas deja present.
	 * 
	 * @param hashTag
	 *            Le hashtag a rajouter
	 */
	public void ajouterHashTag(String hashTag) {
		if (this.hashTagList.contains(hashTag) == false) {
			this.hashTagList.add(hashTag);
		}
	}

	/**
	 * Retire un hashtag de la liste des abonnements de l'utilisateur si cet hashtag
	 * y est present.
	 * 
	 * @param hashTag
	 *            Le hashtag a retirer
	 */
	public void retirerHashTag(String hashTag) {
		if (this.hashTagList.contains(hashTag)) {
			this.hashTagList.remove(hashTag);
		}
	}

	/**
	 * Accesseur permettant de recuperer le nom de l'utilisateur
	 * 
	 * @return Le nom de l'utilisateur sous forme de String
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Donne une representation de l'utilisateur sous la forme de String.
	 * 
	 * @return L'utilisateur sous la forme de String "nom ( nombreDeFollower
	 *         follower et nombreDeMessages msg)".
	 */
	public String toString() {
		return this.nom + " (" + this.getFollowerCount() + " followers et "
				+ this.listMessagesUtilisateur.getNbMessage() + "msg )";
	}

	/**
	 * Accesseur permettant de recuperer le nombre de follower de l'utilisateur
	 * 
	 * @return Le nombre de follower de l'utilisateur
	 */
	public int getFollowerCount() {
		return this.listFollower.length();
	}

	/**
	 * Accesseur permettant de recuperer le nombre de follow de l'utilisateur
	 * 
	 * @return Le nombre de follow de l'utilisateur
	 */
	public int getFollowCount() {
		return this.listFollow.length();
	}

	/**
	 * Accesseur permettant de recuperer la liste de follower de l'utilisateur
	 * 
	 * @return La liste de follower de l'utilisateur
	 */
	public UtilisateurList getFollowerList() {
		return this.listFollower;
	}

	/**
	 * Ajoute un message a la liste des messages recents de l'utilisateur. Si le
	 * nombre maximal de messages recents est atteint, le message le + ancien est
	 * place dans la liste des messages.
	 * 
	 * @param m
	 *            Le message a ajouter. (C'est un message recu par l'utilisateur)
	 */
	public void ajouterMessage(Message m) {
		Message m2 = this.listMessagesRecents.ajouterMessage(m);
		if (m2 != null) {
			this.listMessages.ajouterMessage(m);
		}
	}

	/**
	 * Ajoute le message m donne en argument a la liste des message dont
	 * l'utilisateur courant est l'auteur
	 * 
	 * @param m
	 *            Le message a ajouter
	 */
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
