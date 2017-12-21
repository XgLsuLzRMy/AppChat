package appChat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represente un message qui peut etre publie sur AppChat. Contient un contenu,
 * l'auteur, la date et une liste de hashtags.
 * 
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 4300497796996046007L;

	private String auteur;
	private String contenu;
	private Date date;
	private int retweetCount;
	// Liste des hashtags presents dans le message
	private ArrayList<String> hashTags;

	/**
	 * Constructeur de Message simple. Le contenu et l'auteur sont vide.
	 */
	public Message() {
		this.auteur = "";
		this.contenu = "";
		this.date = new Date();
		this.retweetCount = 0;
		this.hashTags = new ArrayList<String>();
	}

	/**
	 * Constructeur de Message avec un contenu et un auteur.
	 * 
	 * @param contenu
	 *            Le contenu du message.
	 * @param auteur
	 *            L'auteur du message.
	 */
	public Message(String contenu, String auteur) {
		this.auteur = auteur;
		this.contenu = contenu;
		this.date = new Date();
		this.retweetCount = 0;
		this.hashTags = new ArrayList<String>();
		this.recupererLesHashtags();
	}

	/**
	 * Lit le contenu du message et modifie this.hashTags avec la totalite des
	 * hashtags qui ont ete trouves dans le message.
	 */
	private void recupererLesHashtags() {
		/*
		 * On scan le contenu du message a la recherche de hashtag On stocke les hashtag
		 * dans un arraylist Un hashtag est une sous-chaine du contenu qui commence par
		 * le caractere '#' et qui se finit par un espace. On cherche donc le caractere
		 * '#' dans le contenu du message et on recupere la sous-chaine a partir de ce
		 * caractere jusqu'au prochain espace.
		 */
		int indexHashTag = this.getContenu().indexOf('#');
		int indexEspace = -1;
		String hashTag;
		while (indexHashTag >= 0 && indexHashTag < this.getContenu().length() - 1) {
			indexEspace = this.getContenu().indexOf(' ', indexHashTag);
			if (indexEspace == -1) {
				indexEspace = this.getContenu().length();
			}
			hashTag = this.getContenu().substring(indexHashTag + 1, indexEspace);
			this.hashTags.add(hashTag);

			indexHashTag = this.getContenu().indexOf('#', indexHashTag + 1);
		}
	}

	/**
	 * Accesseur permettant de recuperer la liste des hashtags presents dans le
	 * message.
	 * 
	 * @return Une ArrayList regroupant la totalite des hashtags dans le message.
	 */
	public ArrayList<String> getHashtags() {
		return this.hashTags;
	}

	/**
	 * Ajoute un hashtag a la liste des hashtags associee au message.
	 * 
	 * @param hashtag
	 *            Le hashtage a rajouter.
	 */
	public void ajouterHashTag(String hashtag) {
		if (this.hashTags.contains(hashtag) == false) {
			this.hashTags.add(hashtag);
		}
	}

	/**
	 * Retire un hashtag de la liste des hashtags associee au message
	 * 
	 * @param hashtag
	 *            Le hashtag a retirer
	 */
	public void retirerHashTag(String hashtag) {
		if (this.hashTags.contains(hashtag)) {
			this.hashTags.remove(hashtag);
		}
	}

	/**
	 * Remplace la liste des hashtags du message par celle donnee en parametre.
	 * 
	 * @param hashtags
	 *            La nouvelle liste de hashtags.
	 */
	public void setHashTags(ArrayList<String> hashtags) {
		this.hashTags = hashtags;
	}

	/**
	 * Accesseur permettant de recuperer le contenu du message.
	 * 
	 * @return Le contenu du message sous forme de String.
	 */
	public String getContenu() {
		return this.contenu;
	}

	/**
	 * Accesseur permettant de recuperer la date du message.
	 * 
	 * @return La date du message.
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Accesseur permettant de recuperer le nombre de retweet du message.
	 * 
	 * @return Le nombre de retweet du message.
	 */
	public int getRetweetCount() {
		return this.retweetCount;
	}

	/**
	 * Accesseur permettant de recuperer l'auter du message.
	 * 
	 * @return L'auteur du message sous forme de String.
	 */
	public String getAuteur() {
		return this.auteur;
	}

	/**
	 * Setteur permettant de modifier le contenu du message.
	 * 
	 * @param contenu
	 *            Le nouveau contenu du message.
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	/**
	 * Incremente le nombre de retweet associe au message.
	 */
	public void incrementerRewtweetCount() {
		this.retweetCount++;
	}

	/**
	 * Donne une representation sous la forme de String du message.
	 * 
	 * @return Le message sous la forme de String "auteur : contenu".
	 */
	public String toString() {
		return "\n" + this.auteur + " : " + this.contenu + "\n";
	}

}
