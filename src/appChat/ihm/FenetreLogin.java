package appChat.ihm;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import appChat.rmi.UserConsoleDistante;

public class FenetreLogin extends JFrame {

	private static final long serialVersionUID = -5165753234926417673L;

	private JPanel panneau;
	private JPanel texteEtLabelNom, texteEtLabelMdp;
	private UserConsoleDistante uc;
	private JTextField texteNom, texteMdp;

	public FenetreLogin(UserConsoleDistante uc) {
		super("AppChat Login");
		this.uc = uc;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.panneau = (JPanel) this.getContentPane();
		this.panneau.setLayout(new BorderLayout());

		this.texteNom = new JTextField();
		this.texteMdp = new JTextField();

		LoginMdpListener loginMdpListener = new LoginMdpListener(this.texteNom, this.texteMdp, this.uc);

		this.texteNom.addActionListener(loginMdpListener);
		this.texteMdp.addActionListener(loginMdpListener);

		this.texteEtLabelNom = new JPanel();
		this.texteEtLabelNom.setLayout(new BorderLayout());
		this.texteEtLabelNom.add(new JLabel("Nom : "), BorderLayout.WEST);
		this.texteEtLabelNom.add(this.texteNom, BorderLayout.CENTER);

		this.texteEtLabelMdp = new JPanel();
		this.texteEtLabelMdp.setLayout(new BorderLayout());
		this.texteEtLabelMdp.add(new JLabel("Mot de passe : "), BorderLayout.WEST);
		this.texteEtLabelMdp.add(this.texteMdp, BorderLayout.CENTER);

		this.panneau.add(this.texteEtLabelNom, BorderLayout.CENTER);
		this.panneau.add(this.texteEtLabelMdp, BorderLayout.SOUTH);

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

}
