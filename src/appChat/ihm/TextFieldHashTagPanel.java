package appChat.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import appChat.rmi.UserConsoleDistante;

public class TextFieldHashTagPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 3792707519605459619L;

	private JTextField zoneTexte;
	private JButton boutton;

	public TextFieldHashTagPanel(UserConsoleDistante uc) {
		this.zoneTexte = new JTextField(15);
		this.boutton = new JButton("s'abonner");

		this.zoneTexte.addActionListener(new ListenerZoneTexteHashTag(this.zoneTexte, uc));
		this.boutton.addActionListener(new ListenerZoneTexteHashTag(this.zoneTexte, uc));

		this.add(this.zoneTexte, BorderLayout.CENTER);
		this.add(this.boutton, BorderLayout.EAST);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public class ListenerZoneTexteHashTag implements ActionListener {

		private JTextField zoneTexte;
		private UserConsoleDistante uc;

		public ListenerZoneTexteHashTag(JTextField zoneTexte, UserConsoleDistante uc) {
			this.zoneTexte = zoneTexte;
			this.uc = uc;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = this.zoneTexte.getText();
			if (text.equals("") == false) {
				if (text.charAt(0) == '#') {
					text = text.substring(1, text.length());
				}
				this.uc.ajouterHashTag(text);
				this.zoneTexte.setText("");
			}
		}

	}

}
