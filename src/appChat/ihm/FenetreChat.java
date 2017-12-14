package appChat.ihm;

import java.awt.BorderLayout;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appChat.AppChat;
import appChat.Message;
import appChat.Utilisateur;
import appChat.UtilisateurList;
import appChat.rmi.UserConsoleDistante;
import appChat.rmi.UtilisateurServeurImpl;

public class FenetreChat extends JFrame{

	private static final long serialVersionUID = 8976560413665224423L;
	
	private UtilisateurServeurImpl utilisateurServeur;
	private UserConsoleDistante uc;
	private JPanel chatPanel;
	private JScrollPane panneauMessages;
	private JScrollPane panneauUtilisateurConnectes;
	private TextFieldPanel textFieldPanel;
	
	public FenetreChat(UtilisateurServeurImpl utilisateurServeur, UserConsoleDistante uc) {
		super("AppChat");
		this.utilisateurServeur = utilisateurServeur;
		this.uc = uc;
		try {
			super.setTitle("AppChat " + utilisateurServeur.getUtilisateur().getNom());
		} catch (RemoteException e1) { }
		
		this.textFieldPanel = new TextFieldPanel(uc);
		
		this.chatPanel = (JPanel) this.getContentPane();
		this.chatPanel.setLayout(new BorderLayout());
		
		this.panneauMessages = new JScrollPane(new JList<Message>());
		
		this.panneauUtilisateurConnectes = new JScrollPane(new JList<Utilisateur>());
		
		try {
			Message[] tab = new Message[this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getNbMessage()];
			this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getMessageList().toArray(tab);
			this.panneauMessages = new JScrollPane(new JList<Message>(tab));
			System.out.println("1");
			UtilisateurList utilisateursConnectes = this.uc.getListeUtilisateursConnectes();
			System.out.println("2");
			Utilisateur[] tab1 = new Utilisateur[utilisateursConnectes.length()];
			System.out.println("3");
			utilisateursConnectes.getUtilisateurList().toArray(tab1);
			System.out.println("4");
			this.panneauUtilisateurConnectes = new JScrollPane(new JList<Utilisateur>(tab1));
			
			
			System.out.println("OK");
		}catch(Exception e) {
			e.printStackTrace();
		}
		//this.refresh();
		
		this.chatPanel.add(this.panneauMessages, BorderLayout.CENTER);
		this.chatPanel.add(this.panneauUtilisateurConnectes, BorderLayout.EAST);
		this.chatPanel.add(textFieldPanel, BorderLayout.SOUTH);
		
	}
	
	
	public void refreshListeUtilisateursConnectes() {
		this.chatPanel.remove(this.panneauUtilisateurConnectes);
		//System.out.println("On refresh le tableau");
		
		try {
			
			UtilisateurList utilisateursConnectes = this.uc.getListeUtilisateursConnectes();
			Utilisateur[] tab1 = new Utilisateur[utilisateursConnectes.length()];
			utilisateursConnectes.getUtilisateurList().toArray(tab1);
			this.panneauUtilisateurConnectes = new JScrollPane(new JList<Utilisateur>(tab1));
			
			this.revalidate();
			this.chatPanel.repaint();
			this.chatPanel.add(this.panneauUtilisateurConnectes, BorderLayout.EAST);
			this.revalidate();
			this.chatPanel.repaint();
			
		}catch (Exception e) {
			e.printStackTrace();

		}
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
