package appChat.ihm;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appChat.Message;
import appChat.rmi.UserConsoleDistante;
import appChat.rmi.UtilisateurServeurImpl;

public class FenetreChat extends JFrame{

	private static final long serialVersionUID = 8976560413665224423L;
	
	private UtilisateurServeurImpl utilisateurServeur;
	private UserConsoleDistante uc;
	private JPanel chatPanel;
	private JScrollPane panneauMessages;
	private TextFieldPanel textFieldPanel;
	
	public FenetreChat(UtilisateurServeurImpl utilisateurServeur, UserConsoleDistante uc) {
		super("AppChat");
		this.utilisateurServeur = utilisateurServeur;
		
		this.textFieldPanel = new TextFieldPanel(uc);
		
		this.chatPanel = (JPanel) this.getContentPane();
		this.chatPanel.setLayout(new BorderLayout());
		this.panneauMessages = new JScrollPane(new JList<Message>());
		
		try {
			Message[] tab = new Message[this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMessage()];
			this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getMessageList().toArray(tab);
			
			this.panneauMessages = new JScrollPane(new JList<Message>(tab));
			
			
			System.out.println("OK");
		}catch(Exception e) {
			e.printStackTrace();
		}
		//this.refresh();
		
		this.chatPanel.add(this.panneauMessages, BorderLayout.CENTER);
		this.chatPanel.add(textFieldPanel, BorderLayout.SOUTH);
		
	}
	
	public void refresh() {
		
		this.chatPanel.remove(this.panneauMessages);
		System.out.print("On refresh... ");
		
		try {
			Message[] tab = new Message[this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMessage()];
			this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getMessageList().toArray(tab);
			
			this.panneauMessages = new JScrollPane(new JList<Message>(tab));
			
			this.revalidate();
			this.chatPanel.repaint();
			
			
			this.chatPanel.add(this.panneauMessages, BorderLayout.CENTER);
			
			this.revalidate();
			this.repaint();
			
			System.out.println("OK");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
