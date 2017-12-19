package appChat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class UtilisateurList implements Serializable {

	private static final long serialVersionUID = 1L;
	// On utilise ArrayList car il n'y a pas d'ordre pour les utilisateurs
	private ArrayList<Utilisateur> utilisateurList;

	public UtilisateurList() {
		utilisateurList = new ArrayList<Utilisateur>();
	}

	/**
	 *
	 * @param u
	 *            l'utilisateur a ajouter dans la liste Permet d'ajouter un
	 *            utilisateur dans la liste si celui-ci n'y est pas deja (s'il y est
	 *            deja, on ne fait rien)
	 */
	public void ajouterUtilisateur(Utilisateur u) {
		// On verifie que l'utilisateur u n'est pas dﾃｩjﾃ� dans la liste
		if ((u != null) && !this.utilisateurList.contains(u)) {
			this.utilisateurList.add(u);
		}
	}

	public boolean contains(Utilisateur u) {
		return this.utilisateurList.contains(u);
	}

	public Utilisateur get(int i) {
		return this.utilisateurList.get(i);
	}

	public void retirerUtilisateur(Utilisateur u) {
		if (u != null) {
			this.utilisateurList.remove(u);
		}
	}

	public int length() {
		return this.utilisateurList.size();
	}

	public String toString() {
		return this.utilisateurList.toString();
	}

	public void setUtilisateurList(ArrayList<Utilisateur> utilisateurList) {
		this.utilisateurList = utilisateurList;
	}

	public Utilisateur getUtilisateur(String nomAuteur) throws UtilisateurInexistantException {

		Utilisateur temp = null;

		Iterator<Utilisateur> iterator = this.utilisateurList.iterator();
		while (iterator.hasNext()) {
			temp = iterator.next();
			if (temp.getNom().equals(nomAuteur)) {
				return temp;
			}
		}
		throw new UtilisateurInexistantException();
	}

	public ArrayList<Utilisateur> getUtilisateurList() {
		return this.utilisateurList;
	}

	/**
	 * Ajoute le message m dans la liste des messages de chaque utilisateurs dans
	 * cette liste (utile pour envoyer un message à tous ses follower)
	 * 
	 * @param m
	 */
	public void ajouterMessage(Message m) {
		if (m != null) {
			for (Utilisateur u : this.utilisateurList) {
				u.ajouterMessage(m);
			}
		}
	}
}
