package appChat.ihm;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appChat.Message;
import appChat.Utilisateur;
import appChat.rmi.UtilisateurServeurImpl;

public class FenetreChat extends JFrame{

	private static final long serialVersionUID = 8976560413665224423L;
	
	private UtilisateurServeurImpl utilisateurServeur;
	
	public FenetreChat(UtilisateurServeurImpl utilisateurServeur) {
		super("AppChat");
		this.utilisateurServeur = utilisateurServeur;
		
		JPanel chatPanel = (JPanel) this.getContentPane();
		chatPanel.setLayout(new BorderLayout());
		
		try {
			Message[] tab = new Message[this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMessage()];
			this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getMessageList().toArray(tab);
			
			
			chatPanel.add(new JScrollPane(new JList<Message>(tab)), BorderLayout.CENTER);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
