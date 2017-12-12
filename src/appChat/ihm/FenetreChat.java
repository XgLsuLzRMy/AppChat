package appChat.ihm;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appChat.Message;
import appChat.rmi.UtilisateurServeurImpl;

public class FenetreChat extends JFrame{

	private static final long serialVersionUID = 8976560413665224423L;
	
	private UtilisateurServeurImpl utilisateurServeur;
	private JPanel chatPanel;
	private JScrollPane panneauMessages;
	public FenetreChat(UtilisateurServeurImpl utilisateurServeur) {
		super("AppChat");
		this.utilisateurServeur = utilisateurServeur;
		
		this.chatPanel = (JPanel) this.getContentPane();
		this.chatPanel.setLayout(new BorderLayout());
		this.panneauMessages = new JScrollPane(new JList<Message>());
		this.chatPanel.add(this.panneauMessages, BorderLayout.CENTER);
		this.refresh();
		
		
	}
	
	public void refresh() {
		System.out.print("On refresh... ");
		
		try {
			Message[] tab = new Message[this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMessage()];
			this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getMessageList().toArray(tab);
			
			this.panneauMessages = new JScrollPane(new JList<Message>(tab));
			
			this.panneauMessages.repaint();
			this.chatPanel.repaint();
			System.out.println("OK");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
