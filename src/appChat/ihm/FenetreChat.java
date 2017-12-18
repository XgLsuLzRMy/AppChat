package appChat.ihm;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import appChat.Message;
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
	
	private JList<Utilisateur> listUtilisateursConnectes;

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

		this.panneauMessages = new JScrollPane(new JList<Message>());
		this.listUtilisateursConnectes = new JList<Utilisateur>();
		this.panneauUtilisateurConnectes = new JScrollPane(this.listUtilisateursConnectes);
		this.listUtilisateursConnectes.addMouseListener(new ClicDroitListener(this.uc));

		this.refreshMessages();
		this.refreshListeUtilisateursConnectes();

		this.chatPanel.add(this.panneauMessages, BorderLayout.CENTER);
		this.chatPanel.add(this.panneauUtilisateurConnectes, BorderLayout.EAST);
		this.chatPanel.add(textFieldPanel, BorderLayout.SOUTH);

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void refreshListeUtilisateursConnectes() {
		try {

			UtilisateurList utilisateursConnectes = this.uc.getListeUtilisateursConnectes();
			Utilisateur[] tab1 = new Utilisateur[utilisateursConnectes.length()];
			utilisateursConnectes.getUtilisateurList().toArray(tab1);
			this.listUtilisateursConnectes.setListData(tab1);

			this.revalidate();
			this.chatPanel.repaint();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void refreshMessages() {

		this.chatPanel.remove(this.panneauMessages);
		System.out.print("On refresh... ");

		try {
			Message[] tab = new Message[this.utilisateurServeur.getUtilisateur().getListMessagesRecents()
					.getNbMessage()];
			this.utilisateurServeur.getUtilisateur().getListMessagesRecents().getMessageList().toArray(tab);

			this.panneauMessages = new JScrollPane(new JList<Message>(tab));

			this.revalidate();
			this.chatPanel.repaint();

			this.chatPanel.add(this.panneauMessages, BorderLayout.CENTER);

			this.revalidate();
			this.repaint();

			System.out.println("OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
