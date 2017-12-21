package appChat;

/**
 * UtilisateurInexcistantException est utilisee dans les cas ou on cherche a
 * acceder a un utilisateur specifique mais qui n'est pas present dans une
 * liste.
 * 
 *
 */
public class UtilisateurInexistantException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public void printStackTrace() {
		System.out.println("Utilisateur non existant :'(");
	}
}
