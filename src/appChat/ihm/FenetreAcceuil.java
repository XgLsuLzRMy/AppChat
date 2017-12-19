package appChat.ihm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FenetreAcceuil extends JPanel implements ActionListener {

	private static final long serialVersionUID = 2886278625120923051L;

	protected JButton button1;
	protected JButton button2;
	protected FenetreLogin fenetreLogin;

	public FenetreAcceuil(FenetreLogin fenetreLogin) {
		this.fenetreLogin = fenetreLogin;

		this.button1 = new JButton("Se Connecter");
		this.button2 = new JButton("Creer un compte");

		this.setLayout(new GridLayout(2, 1, 0, 10));

		this.button1.addActionListener(this);
		this.button2.addActionListener(this);

		this.add(button1);
		this.add(button2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == this.button1) {
			this.fenetreLogin.changerPanneauVersLogin();
		} else if (source == this.button2) {
			this.fenetreLogin.changerPanneauVersCreerCompte();
		} else {
			System.out.println("Erreur");
		}

	}

}
