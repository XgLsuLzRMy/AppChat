package appChat.ihm;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appChat.AppChat;
import appChat.Utilisateur;

public class FenetreServeur extends JFrame{

	private static final long serialVersionUID = -7993813074760302584L;
	
	private AppChat appChat;
	private JList<Utilisateur> jListUtilisateurs;
	private JPanel mainPanel;
	
	public FenetreServeur(AppChat appChat) {
		super("AppChat Serveur");
		this.appChat = appChat;
		
		this.mainPanel = (JPanel)this.getContentPane();
		this.mainPanel.setLayout(new BorderLayout());
		
		Utilisateur[] ul = new Utilisateur[AppChat.getUtilisateurList().getUtilisateurList().size()];
		AppChat.getUtilisateurList().getUtilisateurList().toArray(ul);
		
		jListUtilisateurs = new JList<Utilisateur>(ul);
		this.mainPanel.add(new JScrollPane(jListUtilisateurs), BorderLayout.CENTER);
		
	}
	
	public void refresh() {
		
		System.out.println("Refresh...");
		
		this.mainPanel.remove(this.jListUtilisateurs);
		
		Utilisateur[] ul = new Utilisateur[AppChat.getUtilisateurList().getUtilisateurList().size()];
		AppChat.getUtilisateurList().getUtilisateurList().toArray(ul);
		
		this.jListUtilisateurs = new JList<Utilisateur>(ul);
		this.mainPanel.add(new JScrollPane(this.jListUtilisateurs), BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();
	}
	
	public AppChat getAppChat() {
		return this.appChat;
	}
	
	public void setAppChat(AppChat appChat) {
		this.appChat = appChat;
	}
	
}
