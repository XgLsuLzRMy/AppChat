package appChat.ihm;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appChat.Message;
import appChat.Utilisateur;

public class FenetreChat extends JFrame{

	private static final long serialVersionUID = 8976560413665224423L;
	
	private Utilisateur utilisateur;
	
	public FenetreChat(Utilisateur utilisateur) {
		super("AppChat");
		this.utilisateur = utilisateur;
		
		JPanel chatPanel = (JPanel) this.getContentPane();
		chatPanel.setLayout(new BorderLayout());
		
		Message[] tab = new Message[this.utilisateur.getListMessagesRecents().getNbMessage()];
		tab = (Message[]) this.utilisateur.getListMessagesRecents().getMessageList().toArray();
		new JList<Message>(tab);
		
		chatPanel.add(new JScrollPane(), BorderLayout.CENTER);
		
	}
	
	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}
	
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
}
