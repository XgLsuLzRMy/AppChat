package appChat.ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import appChat.rmi.UserConsoleDistante;

public class FenetreLogin extends JFrame {

	private static final long serialVersionUID = -5165753234926417673L;

	private JPanel panneau;
	private PanelLogin panelLogin;
	private PanelAcceuil panelAcceuil;
	private PanelCreationCompte panelCreationCompte;
	private UserConsoleDistante uc;

	public FenetreLogin(UserConsoleDistante uc) {
		super("AppChat Login");
		this.uc = uc;

		this.panneau = (JPanel) this.getContentPane();

		this.panelAcceuil = new PanelAcceuil(this);
		this.panelCreationCompte = new PanelCreationCompte(this, this.uc);
		this.panelLogin = new PanelLogin(this.uc);

		this.panneau.add(panelAcceuil);

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
		this.panneau.add(this.panelCreationCompte);
		this.revalidate();
		this.repaint();
	}

}
