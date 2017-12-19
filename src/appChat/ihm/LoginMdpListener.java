package appChat.ihm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import appChat.rmi.UserConsoleDistante;

public class LoginMdpListener implements ActionListener {

	private JTextField zoneNom, zoneMdp;
	private UserConsoleDistante uc;

	public LoginMdpListener(JTextField zoneNom, JTextField zoneMdp, UserConsoleDistante uc) {
		this.zoneNom = zoneNom;
		this.zoneMdp = zoneMdp;
		this.uc = uc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

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
		this.zoneMdp.setText("");
	}

}
