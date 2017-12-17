package appChat.ihm;

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
			this.uc.login(nom, mdp);
		}
		this.zoneMdp.setText("");
	}

}
