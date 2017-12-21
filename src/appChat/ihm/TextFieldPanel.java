package appChat.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import appChat.rmi.UserConsoleDistante;

/**
 * TextFieldPanel permet d'afficher une zone de texte et un bouton pour pouvoir
 * saisir et envoyer un message.
 *
 */
public class TextFieldPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5018214483472601635L;

	private JTextField zoneTexte;
	private JButton boutton;

	// private UserConsoleDistante uc;

	public TextFieldPanel(UserConsoleDistante uc) {
		this.zoneTexte = new JTextField(50);
		this.boutton = new JButton("OK");
		// this.uc = uc;

		this.setLayout(new BorderLayout());

		this.add(this.zoneTexte, BorderLayout.CENTER);
		this.add(this.boutton, BorderLayout.EAST);

		this.zoneTexte.requestFocusInWindow();

		this.zoneTexte.addActionListener(new ListenerZoneTexte(this.zoneTexte, uc));
		this.boutton.addActionListener(new ListenerZoneTexte(this.zoneTexte, uc));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
