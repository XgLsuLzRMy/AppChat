package appChat;
import java.util.ArrayList;
import java.util.Hashtable;

import appChat.Utilisateur;
import appChat.UtilisateurList;


public class AppChat {

	public static int COMPTEUR_USER = 0;
	private static UtilisateurList utilisateurList = new UtilisateurList();
	private static Hashtable<String, String> passwordTable = new Hashtable<String, String>();

	public AppChat (){
		
	}

	private static void ajouterUtilisateur(Utilisateur u) {
		AppChat.utilisateurList.ajouterUtilisateur(u);
	}

	public void creerCompte (String nom,String mdp) {
		AppChat.ajouterUtilisateur(new Utilisateur(nom));
		AppChat.ajouterPassword(nom, mdp);
	}
	
	/**
	 * Sauvegarde le mot de passe mdp de l'utilisateur nom
	 * @param nom le nom de l'utilisateur dont le mot de passe est stocké
	 * @param mdp le mot de passe à stocker
	 */
	private static void ajouterPassword(String nom, String mdp) {
		AppChat.passwordTable.put(nom, mdp);
	}
	
	public boolean verifierMdp(String nom, String mdp) {
		return AppChat.passwordTable.get(nom).equals(mdp);
	}

	public void supprimerCompte(Utilisateur u) {
		AppChat.utilisateurList.retirerUtilisateur(u);
		AppChat.passwordTable.remove(u.getNom());
	}

	public void Connexion (){
		// doit afficher un menu avec les différentes options proposées
	}

	public void Deconnexion () {
		// doit fermer la fenetre des propositions
	}

	public static UtilisateurList getUtilisateurList(){
		return AppChat.utilisateurList;
	}
	
	/**
	 * 
	 * @param m
	 */
	public void publieMessage(Message m) {
		String nomAuteur = m.getAuteur();
		Utilisateur auteur;
		try {
			auteur = AppChat.utilisateurList.getUtilisateur(nomAuteur);
			auteur.ajouterMessageUtilisateur(m);
			auteur.getFollowerList().ajouterMessage(m);
		} catch (UtilisateurInexistantException e) {
			e.printStackTrace();
		}
		
	}

}
