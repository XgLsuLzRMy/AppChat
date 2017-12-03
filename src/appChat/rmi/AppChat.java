package appChat.rmi;
import appChat.Utilisateur;
import appChat.UtilisateurList;


public class Serveur {
	
	public static int COMPTEUR_USER;
	public static UtilisateurList ListUser;
	
	public Serveur (){
		Serveur.ListUser = new UtilisateurList();
		
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
