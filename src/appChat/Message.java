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
		this.hashTags = new ArrayList<String>();
		this.recupererLesHashtags();
	}

	private void recupererLesHashtags() {
		/*
		 * On scan le contenu du message a la recherche de hashtag On stocke les hashtag
		 * dans un arraylist
		 */
		int indexHashTag = this.getContenu().indexOf('#');
		int indexEspace = -1;
		String hashTag;
		while (indexHashTag >= 0 && indexHashTag < this.getContenu().length() - 1) {
			System.out.print("HashTag detecte : ");
			indexEspace = this.getContenu().indexOf(' ', indexHashTag);
			if (indexEspace == -1) {
				indexEspace = this.getContenu().length();
			}
			System.out.println("indexHashTag = " + indexHashTag + " indexEspace = " + indexEspace);
			hashTag = this.getContenu().substring(indexHashTag + 1, indexEspace);
			this.hashTags.add(hashTag);
			System.out.println(hashTag);

			indexHashTag = this.getContenu().indexOf('#', indexHashTag + 1);
		}
	}

	public ArrayList<String> getHashtags() {
		return this.hashTags;
	}

	public void ajouterHashTag(String hashtag) {
		if (this.hashTags.contains(hashtag) == false) {
			this.hashTags.add(hashtag);
		}
	}
	
	public void retirerHashTag(String hashtag) {
		if(this.hashTags.contains(hashtag)) {
			this.hashTags.remove(hashtag);
		}
	}
	
	public void setHashTags(ArrayList<String> hashtags) {
		this.hashTags = hashtags;
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
