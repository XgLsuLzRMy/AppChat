package appChat.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import appChat.Utilisateur;
import appChat.rmi.UserConsoleDistante;

public class PopupMenuListener implements ActionListener {

	private UserConsoleDistante uc;
	private Utilisateur selectedUser;

	public PopupMenuListener(UserConsoleDistante uc, Utilisateur selectedUser) {
		this.uc = uc;
		this.selectedUser = selectedUser;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "follow":
			System.out.println("follow");
			if (this.selectedUser != null) {
				this.uc.follow(this.selectedUser.getNom());
			} else {
				System.out.println("Erreur : selectedUser = null");
			}
			break;
		case "unfollow":
			System.out.println("unfollow");
			if (this.selectedUser != null) {
				this.uc.unfollow(this.selectedUser.getNom());
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
