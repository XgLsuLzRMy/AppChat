package appChat.ihm;

import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import appChat.rmi.UserConsoleDistante;
import appChat.rmi.UtilisateurServeurImpl;

public class FenetreChat extends JFrame {

	private static final long serialVersionUID = 8976560413665224423L;

	private UserConsoleDistante uc;
	private JPanel panneau;
	private PanelChat panelChat;
	private HashTagPanel hashTagPanel;
	private PanneauUtilisateurs panelUtilisateurConnectes;
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
		this.panneau.setLayout(new GridLayout(1, 3));

		this.panelUtilisateurConnectes = new PanneauUtilisateurs(this.uc, this.panelChat);

		this.listeHashTags = new JList<String>();
		this.listeHashTagsRecents = new JList<String>();
		this.hashTagPanel = new HashTagPanel(this.listeHashTags, listeHashTagsRecents, this.uc);

		this.refresh();

		/* |------------------------------
		 * |		|		|			 |
		 * |hashtags| chat	|Utilisateurs|
		 * |		|		|			 |
		 * |------------------------------
		 */
		this.panneau.add(this.hashTagPanel);
		this.panneau.add(this.panelChat);
		this.panneau.add(this.panelUtilisateurConnectes);

		this.panelChat.requestFocus();

		// this.setLocationRelativeTo(null);
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
		this.panelUtilisateurConnectes.refresh();
	}

	public void refreshMessages() {
		this.panelChat.refreshMessages();
	}

}
