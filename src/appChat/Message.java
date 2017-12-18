package appChat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Message implements Serializable {

	private static final long serialVersionUID = 4300497796996046007L;
	private String auteur;
	private String contenu;
	private Date date;
	private int retweetCount;
	private ArrayList<String> hashTags;

	public Message() {
		this.auteur = "";
		this.contenu = "";
		this.date = new Date();
		this.retweetCount = 0;
		this.hashTags = new ArrayList<String>();
	}

	public Message(String contenu, String auteur) {
		this.auteur = auteur;
		this.contenu = contenu;
		this.date = new Date();
		this.retweetCount = 0;
		this.recupererLesHashtags();
	}
	
	private void recupererLesHashtags() {
		/*
		 * On scan le contenu du message a la recherche de hashtag On stocke les hashtag
		 * dans un arraylist Si le hashtag est un nom d'utilisateur on leur envoie le
		 * message
		 */
		/*ArrayList<String> hashTagList = new ArrayList<String>();
		Utilisateur u;
		int indexHashTag = m.getContenu().indexOf('#');
		int indexEspace = -1;
		String hashTag;
		while (indexHashTag >= 0 && indexHashTag < m.getContenu().length() - 1) {
			System.out.print("HashTag detecte : ");
			indexEspace = m.getContenu().indexOf(' ', indexHashTag);
			if (indexEspace == -1) {
				indexEspace = m.getContenu().length();
			}
			hashTag = m.getContenu().substring(indexHashTag + 1, indexEspace);
			hashTagList.add(hashTag);
			System.out.println(hashTag);

			try {
				u = AppChat.utilisateurList.getUtilisateur(hashTag);
				u.ajouterMessage(m);
				res.ajouterUtilisateur(u);
			} catch (UtilisateurInexistantException e) {
			}

			indexHashTag = m.getContenu().indexOf('#', indexHashTag + 1);
		}

	} catch (UtilisateurInexistantException e) {
		e.printStackTrace();
	}*/
	}
	
	public String getContenu() {
		return this.contenu;
	}

	public Date getDate() {
		return this.date;
	}

	public int getRetweetCount() {
		return this.retweetCount;
	}

	public String getAuteur() {
		return this.auteur;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public void incrementerRewtweetCount() {
		this.retweetCount++;
	}

	public String toString() {
		return "\n" + this.auteur + " : " + this.contenu + "\n";
	}

}
