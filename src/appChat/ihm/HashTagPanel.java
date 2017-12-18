package appChat.ihm;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HashTagPanel extends JPanel {

	private static final long serialVersionUID = -5453864867617888167L;

	private JScrollPane panneauHashTags;
	private JScrollPane panneauHashTagsRecents;

	public HashTagPanel(JList<String> listeHashTags,JList<String> listeHashTagsRecents) {
		super();
		this.panneauHashTags = new JScrollPane(listeHashTags);
		this.panneauHashTags.setColumnHeaderView(new JLabel("Vos abonnements"));
		
		this.panneauHashTagsRecents = new JScrollPane(listeHashTagsRecents);
		this.panneauHashTagsRecents.setColumnHeaderView(new JLabel("Tags recemment utilises"));
		
		this.setLayout(new BorderLayout());
		
		
		
		this.add(this.panneauHashTags, BorderLayout.CENTER);
		this.add(this.panneauHashTagsRecents, BorderLayout.SOUTH);

	}

}
