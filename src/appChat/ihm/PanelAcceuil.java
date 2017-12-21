package appChat.ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * PanelAcceuil represente le premier affichage lors du lancement du programme.
 * Il propose deux boutons : un pour se connecter et l'autre pour creer un
 * compte.
 *
 */
public class PanelAcceuil extends JPanel implements ActionListener {

	private static final long serialVersionUID = 2886278625120923051L;

	protected JButton button1;
	protected JButton button2;
	protected FenetreLogin fenetreLogin;

	public PanelAcceuil(FenetreLogin fenetreLogin) {
		this.fenetreLogin = fenetreLogin;

		this.button1 = new JButton("Se Connecter");
		this.button2 = new JButton("Creer un compte");

		this.setLayout(new BorderLayout());

		ImageIcon image = new ImageIcon("..\\src\\appChat\\ihm\\ressources\\imageAccueil.png");
		JLabel imag = new JLabel(image);
		this.add(imag, BorderLayout.CENTER);

		JPanel panneauBoutton = new JPanel();
		panneauBoutton.setLayout(new GridLayout(2, 1, 0, 10));
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);

		panneauBoutton.add(button1);
		panneauBoutton.add(button2);

		this.add(panneauBoutton, BorderLayout.SOUTH);

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
