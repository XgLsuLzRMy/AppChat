package appChat.ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
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
		
		this.setLayout(new GridLayout(2,1));
		
		ImageIcon imageLogin = new ImageIcon("..\\src\\appChat\\ihm\\ressources\\connexion.jpg");
		JLabel imageConnex = new JLabel(imageLogin);
		JPanel panneauLogin = new JPanel(new GridLayout(3, 2));
		

		panneauLogin.add(new JLabel("Nom : "));
		panneauLogin.add(this.texteNom);
		panneauLogin.add(new JLabel("Mot de passe : "));
		panneauLogin.add(this.texteMdp);
		panneauLogin.add(boutonRetour);
		
		this.add(imageConnex, BorderLayout.CENTER);
		this.add(panneauLogin, BorderLayout.SOUTH);

		this.texteNom.requestFocus();

	}

}
