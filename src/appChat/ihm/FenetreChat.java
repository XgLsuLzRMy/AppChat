package appChat.ihm;

import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import appChat.Utilisateur;
import appChat.UtilisateurList;
import appChat.rmi.UserConsoleDistante;
import appChat.rmi.UtilisateurServeurImpl;

public class FenetreChat extends JFrame {

	private static final long serialVersionUID = 8976560413665224423L;

	private UserConsoleDistante uc;
	private JPanel panneau;
	private PanelChat panelChat;
	private JScrollPane panneauUtilisateurConnectes;
	private PanneauUtilisateurs panelUtilisateurConnectes;
	
	private HashTagPanel hashTagPanel;

	private JList<Utilisateur> listUtilisateursConnectes;
	
	private JList<String> listeHashTags;
	private JList<String> listeHashTagsRecents;

	public FenetreChat(UtilisateurServeurImpl utilisateurServeur, UserConsoleDistante uc) {
		super("AppChat");
		this.uc = uc;
		try {
			super.setTitle("AppChat " + utilisateurServeur.getUtilisateur().getNom());
		} catch (RemoteException e1) {
		}
		
		this.panelChat = new PanelChat(utilisateurServeur, uc);
		

		this.panneau = (JPanel) this.getContentPane();
		//this.chatPanel.setLayout(new BorderLayout());
		this.panneau.setLayout(new GridLayout(1,3));

		

		this.listUtilisateursConnectes = new JList<Utilisateur>();
		this.panneauUtilisateurConnectes = new JScrollPane(this.listUtilisateursConnectes);
		this.panneauUtilisateurConnectes.setColumnHeaderView(new JLabel("Utilisateurs connectes"));
		this.listUtilisateursConnectes.addMouseListener(new ClicDroitListener(this.uc));

		this.panelUtilisateurConnectes = new PanneauUtilisateurs(panneauUtilisateurConnectes, this.uc);

		this.listeHashTags = new JList<String>();
		this.listeHashTagsRecents = new JList<String>();
		this.hashTagPanel = new HashTagPanel(this.listeHashTags, listeHashTagsRecents, this.uc);

		this.refresh();

		/*this.chatPanel.add(this.hashTagPanel, BorderLayout.WEST);
		this.chatPanel.add(this.panneauMessages, BorderLayout.CENTER);
		this.chatPanel.add(this.panelUtilisateurConnectes, BorderLayout.EAST);
		this.chatPanel.add(textFieldPanel, BorderLayout.SOUTH);*/
		/*
		 * |------------------------------
		 * |		|		|			 |
		 * |hashtags| chat	|Utilisateurs|
		 * |		|		|			 |
		 * |------------------------------
		*/
		this.panneau.add(this.hashTagPanel);
		this.panneau.add(this.panelChat);
		this.panneau.add(this.panelUtilisateurConnectes);
		
		this.panelChat.requestFocus();

		//this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		

	}

	public void refresh() {
		System.out.print("\nRefresh liste hashtag... ");
		this.refreshListeHashTags();
		System.out.println("OK");
		System.out.print("Refresh utilisateurs connectes... ");
		this.refreshListeUtilisateursConnectes();
		System.out.println("OK");
		System.out.print("Refresh messages... ");
		this.refreshMessages();
		System.out.println("OK");
	}

	public void refreshListeHashTags() {
		try {
			ArrayList<String> listeHashTags = this.uc.getHashTagList();
			String[] hashtagTab = new String[listeHashTags.size()];
			listeHashTags.toArray(hashtagTab);
			this.listeHashTags.setListData(hashtagTab);
			LinkedList<String> listeHashTagsRecents = this.uc.getHashTagsRecents();
			hashtagTab = new String[listeHashTagsRecents.size()];
			listeHashTagsRecents.toArray(hashtagTab);
			this.listeHashTagsRecents.setListData(hashtagTab);

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void refreshListeUtilisateursConnectes() {
		try {
			UtilisateurList utilisateursConnectes = this.uc.getListeUtilisateursConnectes();
			Utilisateur[] tab = new Utilisateur[utilisateursConnectes.length()];
			utilisateursConnectes.getUtilisateurList().toArray(tab);
			this.listUtilisateursConnectes.setListData(tab);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refreshMessages() {
		this.panelChat.refreshMessages();
	}

}
