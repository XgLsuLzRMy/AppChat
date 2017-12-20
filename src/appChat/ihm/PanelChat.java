package appChat.ihm;

import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appChat.Message;
import appChat.MessageListAbstract;
import appChat.rmi.UserConsoleDistante;
import appChat.rmi.UtilisateurServeurImpl;

public class PanelChat extends JPanel{

	private static final long serialVersionUID = -973432845546311563L;
	
	private JScrollPane panneauMessages;
	private TextFieldPanel textFieldPanel;
	private JList<Message> listeMessagesRecents;
	private UtilisateurServeurImpl utilisateurServeur;
	
	public PanelChat(UtilisateurServeurImpl utilisateurServeur, UserConsoleDistante uc) {
		this.utilisateurServeur = utilisateurServeur;
		
		this.setLayout(new BorderLayout());
		
		this.textFieldPanel = new TextFieldPanel(uc);
		
		this.listeMessagesRecents = new JList<Message>();
		this.panneauMessages = new JScrollPane(this.listeMessagesRecents);
		
		this.add(this.panneauMessages, BorderLayout.CENTER);
		this.add(textFieldPanel, BorderLayout.SOUTH);
		
		this.textFieldPanel.requestFocus();
	}
	
	public void refreshMessages() {
		try {
			MessageListAbstract messageListe = this.utilisateurServeur.getUtilisateur().getListMessagesRecents();
			Message[] tab = new Message[messageListe.getNbMessage()];
			messageListe.getMessageList().toArray(tab);
			this.listeMessagesRecents.setListData(tab);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
