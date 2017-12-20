package appChat.ihm;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import appChat.rmi.UserConsoleDistante;

public class PanelCreationCompte extends JPanel implements ActionListener {

	private static final long serialVersionUID = -4762372221425260220L;

	private JTextField texteNom, texteMdp, texteMdpRepetition;
	private UserConsoleDistante uc;
	private FenetreLogin fenetreLogin;
	private JButton boutonRetour;

	public PanelCreationCompte(FenetreLogin fenetreLogin, UserConsoleDistante uc) {

		this.fenetreLogin = fenetreLogin;
		this.uc = uc;

		this.texteNom = new JTextField(20);
		this.texteMdp = new JTextField(20);
		this.texteMdpRepetition = new JTextField(20);
		this.boutonRetour = new JButton("Retour");

		this.texteNom.addActionListener(this);
		this.texteMdp.addActionListener(this);
		this.texteMdpRepetition.addActionListener(this);
		this.boutonRetour.addActionListener(this);

		this.setLayout(new GridLayout(4, 2));

		this.add(new JLabel("Nom : "));
		this.add(this.texteNom);
		this.add(new JLabel("Mot de passe : "));
		this.add(this.texteMdp);
		this.add(new JLabel("Repeter : "));
		this.add(this.texteMdpRepetition);
		this.add(boutonRetour);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.boutonRetour) {
			this.fenetreLogin.changerPanneauVersAcceuil();
		} else {

			String nom = this.texteNom.getText();
			String mdp = this.texteMdp.getText();
			String mdpRepetition = this.texteMdpRepetition.getText();

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
