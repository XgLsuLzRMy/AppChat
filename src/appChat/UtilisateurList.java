package appChat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * UtilisateurList permet de regrouper une liste d'utilisateur. Cela peut etre
 * utilise pour representer la liste des follower par exemple.
 * 
 *
 */
public class UtilisateurList implements Serializable {

	private static final long serialVersionUID = 1L;
	// On utilise ArrayList car il n'y a pas d'ordre pour les utilisateurs
	private ArrayList<Utilisateur> utilisateurList;

	/**
	 * Constructeur de UtilisateurList. Instancie une liste vide.
	 */
	public UtilisateurList() {
		utilisateurList = new ArrayList<Utilisateur>();
	}

	/**
	 *
	 * @param u
	 *            l'Utilisateur a ajouter dans la liste Permet d'ajouter un
	 *            utilisateur dans la liste si celui-ci n'y est pas deja (s'il y est
	 *            deja, on ne fait rien)
	 */
	public void ajouterUtilisateur(Utilisateur u) {
		// On verifie que l'utilisateur u n'est pas deja dans la liste
		if ((u != null) && !this.utilisateurList.contains(u)) {
			this.utilisateurList.add(u);
		}
	}
	/**
	 * Permet de savoir si un Utilisateur u donne en argument est present dans la liste
	 * @param u L'Utilisateur que l'on cherche
	 * @return true si l'Utilisateur est present, false sinon
	 */
	public boolean contains(Utilisateur u) {
		return this.utilisateurList.contains(u);
	}
	/**
	 * Permet de recuperer l'Utilisateur present a l'indice i de la liste
	 * @param i L'indice de l'utilisateur que l'on cherche
	 * @return L'utilisateur present a l'indice i
	 */
	public Utilisateur get(int i) {
		return this.utilisateurList.get(i);
	}
	/**
	 * Retire l'utilisateur u, donne en argument, de la liste
	 * @param u L'utilisateur que l'on souhaite retirer
	 */
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
	/**
	 * Permet de recuperer l'utilisateur portant le nom donne en argument si il est present. Sinon une exception est utilisée.
	 * @param nom Le nom de l'utilisateur que l'on cherche
	 * @return L'utilisateur portant le nom donne en argument s'il existe
	 * @throws UtilisateurInexistantException Si aucun utilisateur ne porte le nom donne en argument
	 */
	public Utilisateur getUtilisateur(String nom) throws UtilisateurInexistantException {

		Utilisateur temp = null;

		Iterator<Utilisateur> iterator = this.utilisateurList.iterator();
		while (iterator.hasNext()) {
			temp = iterator.next();
			if (temp.getNom().equals(nom)) {
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
	 * cette liste (utile pour envoyer un message a tous ses follower)
	 *
	 * @param m Le message a ajouter chez tous les utilisateurs de la liste
	 */
	public void ajouterMessage(Message m) {
		if (m != null) {
			for (Utilisateur u : this.utilisateurList) {
				u.ajouterMessage(m);
			}
		}
	}
	/**
	 * Permet d'appliquer la methode resetRegistry() a tous les utilisateurs de la liste
	 */
	public void resetRegistry() {
		Iterator<Utilisateur> it = this.utilisateurList.iterator();
		while (it.hasNext()) {
			it.next().resetRegistry();
		}
	}
}
