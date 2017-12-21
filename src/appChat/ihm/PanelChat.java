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

/**
 * PanelChat est un JPanel qui contient une zone ou l'utilisateur peut ecrire un
 * message, une zone ou il peut visionner les messages, et un bouton permettant
 * de passer de l'affichage des seuls messages recents a l'historique complet ou
 * de revenir a ses message lorsque l'on affiche les messages d'un autre
 * utilisateur.
 * 
 *
 */
public class PanelChat extends JPanel implements ActionListener {

	private static final long serialVersionUID = -973432845546311563L;

	private JScrollPane panneauMessages;
	private TextFieldPanel textFieldPanel;
	private JList<Message> listeDansLePanneauMessages;
	private JList<Message> listeMessagesRecents;
	private JList<Message> listeMessagesComplet;
	private UtilisateurServeurImpl utilisateurServeur;
	private JButton boutonAffichageCompletMessages;
	/*
	 * refreshType = 1 pour afficher les messages recents ; 2 pour afficher
	 * l'historique ; autre pour les messages d'un autre utilisateur
	 */
	private char refreshType;

	/**
	 * Constructeur de PanelChat
	 * 
	 * @param utilisateurServeur
	 *            Permet de recuperer les informations concernant l'utilisateur
	 *            connecte
	 * @param uc
	 *            Permet d'envoyer les messages
	 */
	public PanelChat(UtilisateurServeurImpl utilisateurServeur, UserConsoleDistante uc) {
		super();

		this.utilisateurServeur = utilisateurServeur;

		this.refreshType = 1;

		this.setLayout(new BorderLayout());

		this.boutonAffichageCompletMessages = new JButton("Afficher l'historique des messages");
		this.boutonAffichageCompletMessages.addActionListener(this);

		this.textFieldPanel = new TextFieldPanel(uc);

		new JList<Message>();
		this.listeMessagesComplet = new JList<Message>();
		this.listeMessagesRecents = new JList<Message>();

		this.listeDansLePanneauMessages = this.listeMessagesRecents;
		this.panneauMessages = new JScrollPane(this.listeDansLePanneauMessages);

		this.add(this.boutonAffichageCompletMessages, BorderLayout.NORTH);
		this.add(this.panneauMessages, BorderLayout.CENTER);
		this.add(textFieldPanel, BorderLayout.SOUTH);

		this.textFieldPanel.requestFocus();
	}

	/**
	 * Permet de mettre a jour l'affichage des messages. La mise a jour sera
	 * differente selon si l'on affiche l'historique, les messages recents ou les
	 * messages d'un autre utilisateur.
	 */
	public void refreshMessages() {
		if (this.refreshType == 1) {
			this.refreshMessagesRecents();
		} else if (this.refreshType == 2) {
			this.refreshMessagesUtilisateur();
		}
	}

	/**
	 * Met a jour l'affichage des messages recents
	 */
	private void refreshMessagesRecents() {
		try {
			MessageListAbstract messageListe = this.utilisateurServeur.getUtilisateur().getListMessagesRecents();
			Message[] tab = new Message[messageListe.getNbMessage()];
			messageListe.getMessageList().toArray(tab);
			this.listeMessagesRecents.setListData(tab);
			this.listeDansLePanneauMessages.setListData(tab);
			;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Met a jour l'affichage de l'historique des messages
	 */
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
		if (arg0.getSource() == this.boutonAffichageCompletMessages) {
			if (this.refreshType == 2) {
				this.boutonAffichageCompletMessages.setText("Afficher l'historique des messages");
				this.refreshType = 1;
			} else if (this.refreshType == 1) {
				this.boutonAffichageCompletMessages.setText("Afficher les messages recents");
				this.refreshType = 2;
			} else {
				if (this.boutonAffichageCompletMessages.getText().equals("Afficher l'historique des messages")) {
					this.boutonAffichageCompletMessages.setText("Afficher les messages recents");
					this.refreshType = 2;
				} else {
					this.boutonAffichageCompletMessages.setText("Afficher l'historique des messages");
					this.refreshType = 1;
				}
			}
		}
		this.refreshMessages();
	}

	/**
	 * Permet de modifier la liste de message qui est affichee. Permet d'afficher
	 * les messages publies par un autre utilisateur.
	 * 
	 * @param liste
	 */
	public void setListeMessages(LinkedList<Message> liste) {
		Message[] tab = new Message[liste.size()];
		liste.toArray(tab);
		this.listeDansLePanneauMessages.setListData(tab);
		this.refreshType = 3;
	}

}
