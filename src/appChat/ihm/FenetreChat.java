package appChat.ihm;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import appChat.Message;
import appChat.MessageListAbstract;
import appChat.Utilisateur;
import appChat.UtilisateurList;
import appChat.rmi.UserConsoleDistante;
import appChat.rmi.UtilisateurServeurImpl;

public class FenetreChat extends JFrame {

	private static final long serialVersionUID = 8976560413665224423L;

	private UtilisateurServeurImpl utilisateurServeur;
	private UserConsoleDistante uc;
	private JPanel chatPanel;
	private JScrollPane panneauMessages;
	private JScrollPane panneauUtilisateurConnectes;
	private TextFieldPanel textFieldPanel;
	private HashTagPanel hashTagPanel;

	private JList<Utilisateur> listUtilisateursConnectes;
	private JList<Message> listeMessagesRecents;
	private JList<String> listeHashTags;
	private JList<String> listeHashTagsRecents;

	public FenetreChat(UtilisateurServeurImpl utilisateurServeur, UserConsoleDistante uc) {
		super("AppChat");
		this.utilisateurServeur = utilisateurServeur;
		this.uc = uc;
		try {
			super.setTitle("AppChat " + utilisateurServeur.getUtilisateur().getNom());
		} catch (RemoteException e1) {
		}

		this.textFieldPanel = new TextFieldPanel(uc);

		this.chatPanel = (JPanel) this.getContentPane();
		this.chatPanel.setLayout(new BorderLayout());

		this.listeMessagesRecents = new JList<Message>();
		this.panneauMessages = new JScrollPane(this.listeMessagesRecents);

		this.listUtilisateursConnectes = new JList<Utilisateur>();
		this.panneauUtilisateurConnectes = new JScrollPane(this.listUtilisateursConnectes);
		this.panneauUtilisateurConnectes.setColumnHeaderView(new JLabel("Utilisateurs connectes"));
		this.listUtilisateursConnectes.addMouseListener(new ClicDroitListener(this.uc));

		this.listeHashTags = new JList<String>();
		this.listeHashTagsRecents = new JList<String>();
		this.hashTagPanel = new HashTagPanel(this.listeHashTags, listeHashTagsRecents, this.uc);

		this.refresh();

		this.chatPanel.add(this.hashTagPanel, BorderLayout.WEST);
		this.chatPanel.add(this.panneauMessages, BorderLayout.CENTER);
		this.chatPanel.add(this.panneauUtilisateurConnectes, BorderLayout.EAST);
		this.chatPanel.add(textFieldPanel, BorderLayout.SOUTH);

		this.setLocationRelativeTo(null);
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
