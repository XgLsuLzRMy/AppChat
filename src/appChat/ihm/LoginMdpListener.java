package appChat.ihm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import appChat.rmi.UserConsoleDistante;

public class LoginMdpListener implements ActionListener {

	private JTextField zoneNom, zoneMdp;
	private UserConsoleDistante uc;
	private JButton boutonRetour;
	private FenetreLogin fenetrelogin;

	public LoginMdpListener(JTextField zoneNom, JTextField zoneMdp, UserConsoleDistante uc, JButton boutonRetour,
			FenetreLogin fenetreLogin) {
		this.zoneNom = zoneNom;
		this.zoneMdp = zoneMdp;
		this.uc = uc;
		this.boutonRetour = boutonRetour;
		this.fenetrelogin = fenetreLogin;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.boutonRetour) {
			this.fenetrelogin.changerPanneauVersAcceuil();
		} else {

			String nom = this.zoneNom.getText();
			String mdp = this.zoneMdp.getText();

			if (!(nom.equals("") || mdp.equals(""))) {
				int indexEspace = nom.indexOf(' ');
				if (indexEspace != -1) {
					nom = nom.substring(0, indexEspace);
					this.zoneNom.setText(nom);
					this.zoneNom.setCaretColor(Color.RED);
					this.zoneNom.requestFocus();
				} else {
					this.uc.login(nom, mdp);
				}

			}
		}
		this.zoneMdp.setText("");
	}

}
