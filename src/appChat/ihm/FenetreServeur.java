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
	
	public FenetreServeur(AppChat appChat) {
		super("AppChat Serveur");
		this.appChat = appChat;
		
		JPanel mainPanel = (JPanel)this.getContentPane();
		mainPanel.setLayout(new BorderLayout());
		
		System.out.println("1");
		
		Utilisateur[] ul = new Utilisateur[AppChat.getUtilisateurList().getUtilisateurList().size()];
		AppChat.getUtilisateurList().getUtilisateurList().toArray(ul);
		
		System.out.println("2");
		
		jListUtilisateurs = new JList<Utilisateur>(ul);
		System.out.println("3");
		mainPanel.add(new JScrollPane(jListUtilisateurs), BorderLayout.CENTER);
		System.out.println("4");
	}
	
	public void refresh() {
		this.repaint();
	}
	
	public AppChat getAppChat() {
		return this.appChat;
	}
	
	public void setAppChat(AppChat appChat) {
		this.appChat = appChat;
	}
	
}
