package appChat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import appChat.Utilisateur;
import appChat.UtilisateurList;


public class AppChat {

	public static int COMPTEUR_USER = 0;
	private static UtilisateurList utilisateurList = new UtilisateurList();
	private static ArrayList<String> passwordList = new ArrayList<String>();

	public AppChat (){

	}

	private static void ajouterUtilisateur(Utilisateur u) {
		AppChat.utilisateurList.ajouterUtilisateur(u);
	}

	public void creerCompte (String nom,String mdp) {
		AppChat.ajouterUtilisateur(new Utilisateur(nom));
		AppChat.ajouterPassword(mdp);
	}

	private static void ajouterPassword(String mdp) {
		AppChat.passwordList.add(mdp);
	}

	public void supprimerCompte(Utilisateur u) {

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
		Utilisateur auteur = this.utilisateurList.getUtilisateur(nomAuteur);
		auteur.ajouterMessageUtilisateur(m);
		auteur.getFollowerList().ajouterMessage(m);
	}

}
