package appChat.ihm;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import appChat.rmi.UserConsoleDistante;

/**
 * PanelCreationCompte correspond a l'interface permettant a l'utilisateur de
 * saisir son nom et de choisir un mot de passe et de le valider en l'ecrivant
 * une seconde fois. Si la creation du compte se passe bien, l'utilisateur est
 * alors redirige vers le PanelLogin.
 *
 */
public class PanelCreationCompte extends JPanel implements ActionListener {

	private static final long serialVersionUID = -4762372221425260220L;

	private JTextField texteNom;
	private JPasswordField texteMdp, texteMdpRepetition;
	private UserConsoleDistante uc;
	private FenetreLogin fenetreLogin;
	private JButton boutonRetour;

	public PanelCreationCompte(FenetreLogin fenetreLogin, UserConsoleDistante uc) {

		this.fenetreLogin = fenetreLogin;
		this.uc = uc;

		this.texteNom = new JTextField(20);
		this.texteMdp = new JPasswordField(20);
		this.texteMdpRepetition = new JPasswordField(20);
		this.boutonRetour = new JButton("Retour");

		this.texteNom.addActionListener(this);
		this.texteMdp.addActionListener(this);
		this.texteMdpRepetition.addActionListener(this);
		this.boutonRetour.addActionListener(this);

		JPanel panneauConnexion = new JPanel();

		panneauConnexion.setLayout(new GridLayout(4, 2));

		panneauConnexion.add(new JLabel("Nom : "));
		panneauConnexion.add(this.texteNom);
		panneauConnexion.add(new JLabel("Mot de passe : "));
		panneauConnexion.add(this.texteMdp);
		panneauConnexion.add(new JLabel("Repeter : "));
		panneauConnexion.add(this.texteMdpRepetition);
		panneauConnexion.add(boutonRetour);

		ImageIcon imageConnexion = new ImageIcon("..\\src\\appChat\\ihm\\ressources\\connexion.jpg");
		JLabel imageConnex = new JLabel(imageConnexion);

		this.setLayout(new GridLayout(2, 1));

		this.add(imageConnex);
		this.add(panneauConnexion);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.boutonRetour) {
			this.fenetreLogin.changerPanneauVersAcceuil();
		} else {

			String nom = this.texteNom.getText();
			// String mdp = this.texteMdp.getText();
			String mdp = new String(this.texteMdp.getPassword());
			// String mdpRepetition = this.texteMdpRepetition.getText();
			String mdpRepetition = new String(this.texteMdpRepetition.getPassword());

			if (!(nom.equals("") || mdp.equals("") || mdpRepetition.equals(""))) {
				if (mdp.equals(mdpRepetition)) {
					int indexEspace = nom.indexOf(' ');
					if (indexEspace != -1) {
						nom = nom.substring(0, indexEspace);
						this.texteNom.setText(nom);
						this.texteNom.setCaretColor(Color.RED);
						this.texteNom.requestFocus();
					} else {
						System.out.println("Creation du compte " + nom + " " + mdp);
						if (this.uc.creerCompte(nom, mdp)) {
							this.fenetreLogin.changerPanneauVersLogin();
						} else {
							this.texteNom.setText("Nom deja pris");
						}
					}
				} else {
					this.texteMdpRepetition.setText("");
					this.texteMdp.requestFocus();
				}
			}
		}
		this.texteMdp.setText("");
		this.texteMdpRepetition.setText("");
	}

}
