package appChat;

import java.util.ArrayList;
import java.util.Iterator;

public class UtilisateurList {

	// On utilise ArrayList car il n'y a pas d'ordre pour les utilisateurs
	private ArrayList<Utilisateur> utilisateurList;

	public UtilisateurList() {
		utilisateurList = new ArrayList<Utilisateur>();
	}

	/**
	 *
	 * @param u l'utilisateur ﾃ� ajouter dans la liste
	 * Permet d'ajouter un utilisateur dans la liste si celui-ci n'y est pas dﾃｩjﾃ� (s'il y est dﾃｩjﾃ�, on ne fait rien)
	 */
	public void ajouterUtilisateur(Utilisateur u) {
		// On vﾃｩrifie que l'utilisateur u n'est pas dﾃｩjﾃ� dans la liste
		if(!this.utilisateurList.contains(u)) {
			this.utilisateurList.add(u);
		}
	}

	public void retirerUtilisateur(Utilisateur u) {
		this.utilisateurList.remove(u);
	}

	public int length() {
		return this.utilisateurList.size();
	}

	public String toString(){
		return this.utilisateurList.toString();
	}

	public Utilisateur getUtilisateur(String nomAuteur) {
		
		Utilisateur temp = null;
		
		Iterator<Utilisateur> iterator = this.utilisateurList.iterator();
		while(iterator.hasNext()) {
			temp = iterator.next();
			if (temp.getNom().equals(nomAuteur)) {
				return temp;
			}
		}
		
		return null;
	}
	/**
	 * Ajoute le message m dans la liste des messages de chaque utilisateurs dans cette liste (utile pour envoyer un message à tous ses follower) 
	 * @param m
	 */
	public void ajouterMessage(Message m) {
		for(Utilisateur u : this.utilisateurList) {
			u.ajouterMessage(m);
		}
	}
}
