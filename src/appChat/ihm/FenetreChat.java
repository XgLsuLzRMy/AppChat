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

/**
 * FenetreChat est la fenetre principale du programme. Elle est instancie par le
 * constructeur de UtilisateurServeurImpl. Elle contient 3 parties : a gauche
 * les hashtags, au centre le chat, a droite les autres utilisateurs. La methode
 * refresh permet de mettre a jour le contenu de la fenetre.
 * 
 *
 */
public class FenetreChat extends JFrame {

	private static final long serialVersionUID = 8976560413665224423L;

	private UserConsoleDistante uc; // permet d'executer les methodes suite aux inputs de l'utilisateur
	private JPanel panneau; // Le contenu de FenetreChat
	private PanelChat panelChat; // Le centre de la fenetre : contient une zone de texte pour ecrire un message
									// et une zone ou les messages s'affichent
	private HashTagPanel hashTagPanel;
	private PanneauUtilisateurs panelUtilisateurConnectes;
	private JList<String> listeHashTags;
	private JList<String> listeHashTagsRecents;

	/**
	 * Constructeur de FenetreChat
	 * 
	 * @param utilisateurServeur
	 *            L'utilisateur qui est connecte. Permet de recupere ses messages et
	 *            d'en envoyer.
	 * @param uc
	 *            La UserConsoleDistante de l'utilisateur qui est connecte. Permet
	 *            d'utiliser les methodes comme envoyerMessage(text) ou follow(nom)
	 */
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

		this.panneau.add(this.hashTagPanel);
		this.panneau.add(this.panelChat);
		this.panneau.add(this.panelUtilisateurConnectes);

		this.panelChat.requestFocus();

		// this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/**
	 * Permet de mettre a jour l'affichage de la fenetre. Cette methode fait
	 * simplement appel a refreshListeHashtTag, refreshListeUtilisateursConnectes et
	 * refreshMessages
	 */
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

	/**
	 * Permet de mettre a jour l'affichage de la liste des hashtags
	 */
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

	/**
	 * Permet de mettre a jour l'affichage des utilisateurs connectes. Cette methode
	 * utilise la methode refresh de PanelUtilisateurConnectes
	 */
	public void refreshListeUtilisateursConnectes() {
		this.panelUtilisateurConnectes.refresh();
	}

	/**
	 * Permet de mettre a jour l'affichage des messages. Cette methode utilise la
	 * methode refreshMessage de PanelChat
	 */
	public void refreshMessages() {
		this.panelChat.refreshMessages();
	}

}
