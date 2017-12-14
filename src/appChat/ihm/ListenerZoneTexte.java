package appChat.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import appChat.rmi.UserConsoleDistante;

public class ListenerZoneTexte implements ActionListener{
	
	private JTextField zoneTexte;
	private UserConsoleDistante uc;
	
	public ListenerZoneTexte(JTextField zoneTexte, UserConsoleDistante uc) {
		this.zoneTexte = zoneTexte;
		this.uc = uc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println(this.zoneTexte.getText());
		this.uc.envoyerMessage(this.zoneTexte.getText());
		this.zoneTexte.setText("");
		
	}

}
