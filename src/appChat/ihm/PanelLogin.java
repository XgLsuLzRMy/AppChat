package appChat.ihm;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import appChat.rmi.UserConsoleDistante;

public class PanelLogin extends JPanel {

	private static final long serialVersionUID = -2245064952484115584L;

	private JTextField texteNom, texteMdp;

	public PanelLogin(UserConsoleDistante uc) {

		this.texteNom = new JTextField(15);
		this.texteMdp = new JTextField(15);

		LoginMdpListener loginMdpListener = new LoginMdpListener(this.texteNom, this.texteMdp, uc);

		this.texteNom.addActionListener(loginMdpListener);
		this.texteMdp.addActionListener(loginMdpListener);

		this.setLayout(new GridLayout(2, 2));

		this.add(new JLabel("Nom : "));
		this.add(this.texteNom);
		this.add(new JLabel("Mot de passe : "));
		this.add(this.texteMdp);

	}

}
