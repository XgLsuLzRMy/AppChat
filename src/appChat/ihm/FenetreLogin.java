package appChat.ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import appChat.rmi.UserConsoleDistante;

/**
 * FenetreLogin est la 1ere fenetre qui apparait lorsqu'on execute
 * UserConsoleDistante. Elle possede 3 interfaces. L'affichage d'acceuil ou il
 * est propose de se connecter ou de creer un compte, l'affichage de creation de
 * compte et enfin l'affichage de login.
 * 
 *
 */
public class FenetreLogin extends JFrame {

	private static final long serialVersionUID = -5165753234926417673L;

	private JPanel panneau; // Le contenu de la fenetre
	// Les 3 panels correspondant aux 3 affichages
	private PanelLogin panelLogin;
	private PanelAcceuil panelAcceuil;
	private PanelCreationCompte panelCreationCompte;
	
	private UserConsoleDistante uc;
	/**
	 * Constructeur de FenetreLogin
	 * @param uc La console correspondant a l'utilisateur connecte. Permet d'utiliser les fonction login ou creerCompte.
	 */
	public FenetreLogin(UserConsoleDistante uc) {
		super("AppChat Login");
		this.uc = uc;

		this.panneau = (JPanel) this.getContentPane();

		this.panelAcceuil = new PanelAcceuil(this);
		this.panelCreationCompte = new PanelCreationCompte(this, this.uc);
		this.panelLogin = new PanelLogin(this.uc, this);

		this.panneau.add(panelAcceuil);

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	/**
	 * Permet de changer l'interface pour afficher le login.
	 */
	public void changerPanneauVersLogin() {
		this.panneau.removeAll();
		this.panneau.add(this.panelLogin);
		this.revalidate();
		this.repaint();
	}
	/**
	 * Permet de changer l'interface pour afficher la creation de compte
	 */
	public void changerPanneauVersCreerCompte() {
		this.panneau.removeAll();
		this.panneau.add(this.panelCreationCompte);
		this.revalidate();
		this.repaint();
	}
	/**
	 * Permet de changer l'interface pour afficher l'acceuil
	 */
	public void changerPanneauVersAcceuil() {
		this.panneau.removeAll();
		this.panneau.add(this.panelAcceuil);
		this.revalidate();
		this.repaint();
	}

}
