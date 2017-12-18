package appChat.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import appChat.Utilisateur;
import appChat.rmi.UserConsoleDistante;

public class PanneauUtilisateurs extends JPanel {

	private static final long serialVersionUID = 2242977600425695449L;

	private JPanel recherchePanel;
	private JButton rechercheButton;
	private JTextField zoneTexte;
	private JScrollPane panneauUtilisateurRecherche;
	private JList<Utilisateur> listUtilisateursRecherche;

	public PanneauUtilisateurs(JScrollPane panneauUtilisateurConnectes, UserConsoleDistante uc) {
		super();

		panneauUtilisateurConnectes.setColumnHeaderView(new JLabel("Utilisateurs connectes"));

		this.listUtilisateursRecherche = new JList<Utilisateur>();
		this.listUtilisateursRecherche.addMouseListener(new ClicDroitListener(uc));

		this.panneauUtilisateurRecherche = new JScrollPane(this.listUtilisateursRecherche);
		this.panneauUtilisateurRecherche.setColumnHeaderView(new JLabel("Resutlats de recherche"));

		this.recherchePanel = new JPanel();

		this.rechercheButton = new JButton("Chercher");
		this.zoneTexte = new JTextField(15);

		this.rechercheButton
				.addActionListener(new ListenerZoneTexteRecherche(this.zoneTexte, this.listUtilisateursRecherche, uc));
		this.zoneTexte
				.addActionListener(new ListenerZoneTexteRecherche(this.zoneTexte, this.listUtilisateursRecherche, uc));

		this.recherchePanel.setLayout(new BorderLayout());

		this.recherchePanel.add(this.zoneTexte, BorderLayout.CENTER);
		this.recherchePanel.add(this.rechercheButton, BorderLayout.EAST);
		this.recherchePanel.add(this.panneauUtilisateurRecherche, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());

		this.add(this.recherchePanel, BorderLayout.NORTH);
		this.add(panneauUtilisateurConnectes, BorderLayout.SOUTH);
	}

	public class ListenerZoneTexteRecherche implements ActionListener {

		private JTextField zoneTexte;
		private UserConsoleDistante uc;
		private JList<Utilisateur> listUtilisateursRecherche;

		public ListenerZoneTexteRecherche(JTextField zoneTexte, JList<Utilisateur> listUtilisateursRecherche,
				UserConsoleDistante uc) {
			this.zoneTexte = zoneTexte;
			this.uc = uc;
			this.listUtilisateursRecherche = listUtilisateursRecherche;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = this.zoneTexte.getText();
			try {
				ArrayList<Utilisateur> list = this.uc.chercherUtilisateur(text);
				System.out.println(list);

				Utilisateur[] tab = new Utilisateur[list.size()];
				list.toArray(tab);
				this.listUtilisateursRecherche.setListData(tab);

			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

	}

}
