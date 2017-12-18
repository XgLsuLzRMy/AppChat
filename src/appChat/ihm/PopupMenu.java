package appChat.ihm;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class PopupMenu extends JPanel {

	private static final long serialVersionUID = 3311171851094603404L;
	private JPopupMenu popup;

	public PopupMenu() {
		popup = new JPopupMenu();

		JMenuItem item = new JMenuItem("item 1");
		popup.add(item);
		item = new JMenuItem("item 2");
		popup.add(item);
		this.addMouseListener(new MousePopupListener());
	}

	class MousePopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			checkPopup(e);
		}

		public void mouseClicked(MouseEvent e) {
			checkPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			checkPopup(e);
		}

		private void checkPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popup.show(popup, e.getX(), e.getY());
			}
		}
	}

}
