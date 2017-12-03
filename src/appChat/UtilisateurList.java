package appChat;

import java.util.ArrayList;

public class UtilisateurList {
	
	// On utilise ArrayList car il n'y a pas d'ordre pour les utilisateurs
	private ArrayList<Utilisateur> utilisateurList;
	
	public UtilisateurList() {
		utilisateurList = new ArrayList<Utilisateur>();
	}
	
	/**
	 * 
	 * @param u l'utilisateur à ajouter dans la liste
	 * Permet d'ajouter un utilisateur dans la liste si celui-ci n'y est pas déjà (s'il y est déjà, on ne fait rien)
	 */
	public void ajouterUtilisateur(Utilisateur u) {
		// On vérifie que l'utilisateur u n'est pas déjà dans la liste
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

}
