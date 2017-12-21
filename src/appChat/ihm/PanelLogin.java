package appChat.ihm;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import appChat.rmi.UserConsoleDistante;

public class PanelLogin extends JPanel {

	private static final long serialVersionUID = -2245064952484115584L;

	private JTextField texteNom;
	private JPasswordField texteMdp;
	private JButton boutonRetour;
	FenetreLogin fenetreLogin;

	public PanelLogin(UserConsoleDistante uc, FenetreLogin fenetreLogin) {
		this.fenetreLogin = fenetreLogin;
		this.texteNom = new JTextField(20);
		this.texteMdp = new JPasswordField(20);
		this.boutonRetour = new JButton("Retour");

		LoginMdpListener loginMdpListener = new LoginMdpListener(this.texteNom, this.texteMdp, uc, this.boutonRetour,
				this.fenetreLogin);

		this.texteNom.addActionListener(loginMdpListener);
		this.texteMdp.addActionListener(loginMdpListener);
		boutonRetour.addActionListener(loginMdpListener);

		this.setLayout(new GridLayout(3, 2));

		this.add(new JLabel("Nom : "));
		this.add(this.texteNom);
		this.add(new JLabel("Mot de passe : "));
		this.add(this.texteMdp);
		this.add(boutonRetour);

		this.texteNom.requestFocus();

	}

}
