package appChat.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class PopupMenu extends JPanel {

	private static final long serialVersionUID = 3311171851094603404L;
	private JPopupMenu popup;

	public PopupMenu() {
		popup = new JPopupMenu();
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Popup menu item [" + event.getActionCommand() + "] was pressed.");
			}
		};

		JMenuItem item = new JMenuItem("item 1");
		item.addActionListener(menuListener);
		popup.add(item);
		item = new JMenuItem("item 2");
		item.addActionListener(menuListener);
		popup.add(item);
		popup.addPopupMenuListener(new PopupPrintListener());
		this.addMouseListener(new MousePopupListener());
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("Popup Menu Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new PopupMenu());
		frame.setSize(300, 300);
		frame.setVisible(true);
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
				popup.show(PopupMenu.this, e.getX(), e.getY());
			}
		}
	}

	class PopupPrintListener implements PopupMenuListener {
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			System.out.println("Popup menu will be visible!");
		}

		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			System.out.println("Popup menu will be invisible!");
		}

		public void popupMenuCanceled(PopupMenuEvent e) {
			System.out.println("Popup menu is hidden!");
		}
	}

}
