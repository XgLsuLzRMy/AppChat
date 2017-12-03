package appChat;

public class Utilisateur {
	
	String NomUser;
	UtilisateurList ListFollower;
	MessageList ListMessageRecentAConsulter;
	MessageList ListMessagesUtilisateur;
	
	public Utilisateur(String nomUser) {
		this.NomUser = nomUser;
		this.ListFollower = new UtilisateurList();
		this.ListMessageRecentAConsulter =  new MessageList();
		this.ListMessagesUtilisateur = new MessageList();
	}
	
	public void follow(Utilisateur u){
		
	}
	
	public void publierMessage (Message m){
		
	}
	
	public void retweetMessage (Message m){
		
	}

	public static void main(String[] args) {
		
		
	}

}
