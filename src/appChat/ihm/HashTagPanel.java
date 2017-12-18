package appChat.ihm;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appChat.rmi.UserConsoleDistante;

public class HashTagPanel extends JPanel {

	private static final long serialVersionUID = -5453864867617888167L;

	private JScrollPane panneauHashTags;
	private JScrollPane panneauHashTagsRecents;
	private TextFieldHashTagPanel textFieldHashTagPanel;

	public HashTagPanel(JList<String> listeHashTags, JList<String> listeHashTagsRecents, UserConsoleDistante uc) {
		super();
		listeHashTags.addMouseListener(new ClicDroitListenerHashTags(uc));
		this.panneauHashTags = new JScrollPane(listeHashTags);
		this.panneauHashTags.setColumnHeaderView(new JLabel("Vos abonnements"));

		listeHashTagsRecents.addMouseListener(new ClicDroitListenerHashTags(uc));
		this.panneauHashTagsRecents = new JScrollPane(listeHashTagsRecents);
		this.panneauHashTagsRecents.setColumnHeaderView(new JLabel("Tags recemment utilises"));

		this.textFieldHashTagPanel = new TextFieldHashTagPanel(uc);

		this.setLayout(new BorderLayout());

		this.add(this.textFieldHashTagPanel, BorderLayout.NORTH);
		this.add(this.panneauHashTags, BorderLayout.CENTER);
		this.add(this.panneauHashTagsRecents, BorderLayout.SOUTH);

	}

}
