package appChat.ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import appChat.rmi.UserConsoleDistante;

public class FenetreLogin extends JFrame {

	private static final long serialVersionUID = -5165753234926417673L;

	private JPanel panneau;
	private PanelLogin panelLogin;
	private FenetreAcceuil fenetreAcceuil;
	private UserConsoleDistante uc;

	public FenetreLogin(UserConsoleDistante uc) {
		super("AppChat Login");
		this.uc = uc;

		this.panneau = (JPanel) this.getContentPane();

		this.fenetreAcceuil = new FenetreAcceuil(this);
		this.panelLogin = new PanelLogin(this.uc);

		this.panneau.add(fenetreAcceuil);

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void changerPanneauVersLogin() {
		this.panneau.removeAll();
		this.panneau.add(this.panelLogin);
		this.revalidate();
		this.repaint();
	}

	public void changerPanneauVersCreerCompte() {
		this.panneau.removeAll();
		// TODO Ajouter un panneau creer compte
		this.revalidate();
		this.repaint();
	}

}
