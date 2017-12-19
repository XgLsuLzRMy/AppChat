package appChat.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import appChat.rmi.UserConsoleDistante;

public class ClicDroitListenerHashTags implements MouseListener {

	private JPopupMenu popup;
	private JList<String> list;
	private String selectedHashTag;
	private PopupMenuListenerHashTags menuListener;

	public ClicDroitListenerHashTags(UserConsoleDistante uc) {
		this.popup = new JPopupMenu();
		this.list = new JList<String>();
		this.selectedHashTag = null;

		this.menuListener = new PopupMenuListenerHashTags(uc, this.selectedHashTag);

		JMenuItem item = new JMenuItem("s'abonner");
		item.addActionListener(menuListener);
		this.popup.add(item);
		item = new JMenuItem("se desabonner");
		item.addActionListener(menuListener);
		this.popup.add(item);

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

	@SuppressWarnings("unchecked")
	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			this.list = (JList<String>) e.getSource();

			int row = this.list.locationToIndex(e.getPoint());
			this.list.setSelectedIndex(row);

			this.selectedHashTag = this.list.getSelectedValue();
			this.menuListener.setSelectedUser(selectedHashTag);
			System.out.println("Selected hashtag : " + this.selectedHashTag);

			this.popup.show(this.list, e.getX(), e.getY());

		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public class PopupMenuListenerHashTags implements ActionListener {

		private UserConsoleDistante uc;
		private String selectedHashTag;

		public PopupMenuListenerHashTags(UserConsoleDistante uc, String selectedHashTag) {
			this.uc = uc;
			this.selectedHashTag = selectedHashTag;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			switch (event.getActionCommand()) {
			case "s'abonner":
				System.out.println("s'abonner");
				if (this.selectedHashTag != null) {
					this.uc.ajouterHashTag(selectedHashTag);
				} else {
					System.out.println("Erreur : selectedHashTag = null");
				}
				break;
			case "se desabonner":
				System.out.println("se desabonner");
				if (this.selectedHashTag != null) {
					this.uc.retirerHashTag(selectedHashTag);
				} else {
					System.out.println("Erreur : selectedHashTag = null");
				}
				break;

			default:
				System.out.println("Action pas definie : " + event.getActionCommand());
			}
		}

		public void setSelectedUser(String selectedHashTag) {
			this.selectedHashTag = selectedHashTag;
		}

	}

}
