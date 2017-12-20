package appChat.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import appChat.Utilisateur;
import appChat.rmi.UserConsoleDistante;

public class PopupMenuListener implements ActionListener {

	private UserConsoleDistante uc;
	private Utilisateur selectedUser;
	private PanelChat panelChat;

	public PopupMenuListener(UserConsoleDistante uc, Utilisateur selectedUser, PanelChat panelChat) {
		this.uc = uc;
		this.selectedUser = selectedUser;
		this.panelChat = panelChat;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "follow":
			if (this.selectedUser != null) {
				this.uc.follow(this.selectedUser.getNom());
			} else {
				System.out.println("Erreur : selectedUser = null");
			}
		break;
			
		case "unfollow":
			if (this.selectedUser != null) {
				this.uc.unfollow(this.selectedUser.getNom());
			} else {
				System.out.println("Erreur : selectedUser = null");
			}
		break;
			
		case "afficher les messages":
			if (this.selectedUser != null) {
				this.panelChat.setListeMessages(this.uc.getUtilisateur(this.selectedUser.getNom()).getListMessagesUtilisateur().getMessageList());
			} else {
				System.out.println("Erreur : selectedUser = null");
			}
		break;
		
		default:
			System.out.println("Action pas definie : " + event.getActionCommand());
		}
	}

	public void setSelectedUser(Utilisateur selectedUser) {
		this.selectedUser = selectedUser;
	}

}
