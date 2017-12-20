package appChat.ihm;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import appChat.Utilisateur;
import appChat.rmi.UserConsoleDistante;

public class ClicDroitListener implements MouseListener {

	private JPopupMenu popup;
	private JList<Utilisateur> list;
	private Utilisateur selectedUser;
	private PopupMenuListener menuListener;

	public ClicDroitListener(UserConsoleDistante uc, PanelChat panelChat) {
		this.popup = new JPopupMenu();
		this.list = new JList<Utilisateur>();
		this.selectedUser = null;

		this.menuListener = new PopupMenuListener(uc, this.selectedUser, panelChat);

		JMenuItem item = new JMenuItem("follow");
		item.addActionListener(menuListener);
		this.popup.add(item);
		
		item = new JMenuItem("unfollow");
		item.addActionListener(menuListener);
		this.popup.add(item);
		
		item = new JMenuItem("afficher les messages");
		item.addActionListener(menuListener);
		this.popup.add(item);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			this.list = (JList<Utilisateur>) e.getSource();

			int row = this.list.locationToIndex(e.getPoint());
			this.list.setSelectedIndex(row);

			this.selectedUser = this.list.getSelectedValue();
			this.menuListener.setSelectedUser(selectedUser);
			System.out.println("Selected user : " + this.selectedUser);

			this.popup.show(this.list, e.getX(), e.getY());

		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
