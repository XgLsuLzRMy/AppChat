package appChat;

public class UtilisateurInexistantException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void printStackTrace() {
		System.out.println("Utilisateur non existant :'(");
	}
}
