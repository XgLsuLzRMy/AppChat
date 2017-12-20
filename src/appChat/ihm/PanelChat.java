package appChat.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appChat.Message;
import appChat.MessageListAbstract;
import appChat.rmi.UserConsoleDistante;
import appChat.rmi.UtilisateurServeurImpl;

public class PanelChat extends JPanel implements ActionListener{

	private static final long serialVersionUID = -973432845546311563L;

	private JScrollPane panneauMessages;
	private TextFieldPanel textFieldPanel;
	private JList<Message> listeDansLePanneauMessages;
	private JList<Message> listeMessagesRecents;
	private JList<Message> listeMessagesComplet;
	private JList<Message> listeMessagesAutreUtilisateur;
	private UtilisateurServeurImpl utilisateurServeur;
	private JButton boutonAffichageCompletMessages;
	private char refreshType;

	public PanelChat(UtilisateurServeurImpl utilisateurServeur, UserConsoleDistante uc) {
		super();
		
		this.utilisateurServeur = utilisateurServeur;
		
		this.refreshType = 1;
		
		this.setLayout(new BorderLayout());
		
		this.boutonAffichageCompletMessages = new JButton("Afficher l'historique des messages");
		this.boutonAffichageCompletMessages.addActionListener(this);
		
		this.textFieldPanel = new TextFieldPanel(uc);
		
		this.listeMessagesAutreUtilisateur = new JList<Message>();
		this.listeMessagesComplet = new JList<Message>();
		this.listeMessagesRecents = new JList<Message>();
		
		this.listeDansLePanneauMessages = this.listeMessagesRecents;
		this.panneauMessages = new JScrollPane(this.listeDansLePanneauMessages);
		
		this.add(this.boutonAffichageCompletMessages, BorderLayout.NORTH);
		this.add(this.panneauMessages, BorderLayout.CENTER);
		this.add(textFieldPanel, BorderLayout.SOUTH);

		this.textFieldPanel.requestFocus();
	}

	public void refreshMessages() {
		if(this.refreshType == 1) {
			this.refreshMessagesRecents();
		}else if(this.refreshType == 2) {
			this.refreshMessagesUtilisateur();
		}
	}
	
	private void refreshMessagesRecents() {
		try {
			MessageListAbstract messageListe = this.utilisateurServeur.getUtilisateur().getListMessagesRecents();
			Message[] tab = new Message[messageListe.getNbMessage()];
			messageListe.getMessageList().toArray(tab);
			this.listeMessagesRecents.setListData(tab);
			this.listeDansLePanneauMessages.setListData(tab);;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void refreshMessagesUtilisateur() {
		MessageListAbstract messageListe1;
		MessageListAbstract messageListe2;
		try {
			messageListe1 = this.utilisateurServeur.getUtilisateur().getListMessagesUtilisateur();
			messageListe2 = this.utilisateurServeur.getUtilisateur().getListMessagesRecents();
			
			@SuppressWarnings("unchecked")
			LinkedList<Message> list = (LinkedList<Message>) messageListe1.getMessageList().clone();
			list.addAll(messageListe2.getMessageList());
			
			Message[] tab = new Message[messageListe1.getNbMessage() + messageListe2.getNbMessage()];
			
			list.toArray(tab);
			
			this.listeMessagesComplet.setListData(tab);
			
			this.listeDansLePanneauMessages.setListData(tab);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == this.boutonAffichageCompletMessages) {
			if(this.refreshType == 2) {
				this.boutonAffichageCompletMessages.setText("Afficher l'historique des messages");
				this.refreshType = 1;
			}else if(this.refreshType == 1){
				this.boutonAffichageCompletMessages.setText("Afficher les messages recents");
				this.refreshType = 2;
			}else {
				if(this.boutonAffichageCompletMessages.getText().equals("Afficher l'historique des messages")) {
					this.boutonAffichageCompletMessages.setText("Afficher les messages recents");
					this.refreshType = 2;
				}else {
					this.boutonAffichageCompletMessages.setText("Afficher l'historique des messages");
					this.refreshType = 1;
				}
			}
		}
		this.refreshMessages();
	}
	
	public void setListeMessages(LinkedList<Message> liste) {
		Message[] tab = new Message[liste.size()];
		liste.toArray(tab);
		this.listeDansLePanneauMessages.setListData(tab);
		this.refreshType = 3;
	}
	
}
