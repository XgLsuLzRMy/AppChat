package appChat;
import appChat.Utilisateur;
import appChat.UtilisateurList;


public class AppChat {
	
	public static int COMPTEUR_USER = 0;
	public static UtilisateurList utilisateurList = new UtilisateurList();
	
	public AppChat (){
		
	}
	
	public static void ajouterUtilisateur(Utilisateur u) {
		AppChat.utilisateurList.ajouterUtilisateur(u);
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
		AppChat appChat = new AppChat();
		Utilisateur u1 = new Utilisateur("U1");
		AppChat.ajouterUtilisateur(u1);
		Utilisateur u2 = new Utilisateur("U2");
		AppChat.ajouterUtilisateur(u2);
	}

}
