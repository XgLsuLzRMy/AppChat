package appChat.rmi;
import java.util.LinkedList;

import appChat.Utilisateur;


public class Serveur {
	
	public static int COMPTEUR_USER;
	public static LinkedList<Utilisateur> ListUser;
	
	public Serveur (){
		Serveur.ListUser = new LinkedList<Utilisateur> ();
		
	}
	
	public void creerCompte (Utilisateur u) {
		
	}
	public void supprimerCompte(Utilisateur u) {
		
	}
	
	public void Connexion (){
		// doit afficher un menu avec les différentes options proposées
	}
	
	public void Deconnexion () {
		// doit fermer la fenetre des propositions
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
