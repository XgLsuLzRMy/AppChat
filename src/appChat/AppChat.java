package appChat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import appChat.Utilisateur;
import appChat.UtilisateurList;

public class AppChat {

	private static UtilisateurList utilisateurList = new UtilisateurList();
	private static Hashtable<String, Integer> passwordTable = new Hashtable<String, Integer>();

	private LinkedList<String> hashTagsRecents;
	private int nbMaxHashTagsRecents = 5;

	public AppChat() {
		this.hashTagsRecents = new LinkedList<String>();
	}

	@SuppressWarnings("unchecked")
	public AppChat(String fichierUtilisateurList, String fichierHashTable) {
		this.hashTagsRecents = new LinkedList<String>();
		// System.out.println("On lit les fichiers... ");
		// System.out.print(fichierUtilisateurList + "... ");
		FileInputStream fis;
		try {
			fis = new FileInputStream(fichierUtilisateurList);
			try {
				ObjectInputStream ois = new ObjectInputStream(fis);
				try {
					AppChat.utilisateurList = (UtilisateurList) ois.readObject();
					// System.out.println("OK");

					// System.out.print(fichierHashTable + "... ");
					fis = new FileInputStream(fichierHashTable);
					ois = new ObjectInputStream(fis);
					AppChat.passwordTable = (Hashtable<String, Integer>) ois.readObject();
					// System.out.println("OK");

					AppChat.utilisateurList.resetRegistry();

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void nouveauHashTagRecent(String hashTag) {
		this.hashTagsRecents.removeFirstOccurrence(hashTag);
		this.hashTagsRecents.addFirst(hashTag);
		if (this.nbMaxHashTagsRecents < this.hashTagsRecents.size()) {
			this.hashTagsRecents.removeLast();
		}
	}

	public LinkedList<String> getHashTagsRecents() {
		return this.hashTagsRecents;
	}

	public void ecrirePasswordDansFichier(String nomFichier) {
		try {
			FileOutputStream fos = new FileOutputStream(nomFichier);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(AppChat.passwordTable);
			fos.close();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ecrireUtilisateursDansFichier(String nomFichier) {
		try {
			FileOutputStream fos = new FileOutputStream(nomFichier);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(AppChat.utilisateurList);
			fos.close();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ecrireDansFichier(String fichierUtilisateurList, String fichierHashTable) {
		// System.out.print("Ecriture de la liste d'utilisateurs... ");
		this.ecrireUtilisateursDansFichier(fichierUtilisateurList);
		// System.out.println("OK");
		// System.out.print("Ecriture de la hashtable... ");
		this.ecrirePasswordDansFichier(fichierHashTable);
		// System.out.println("OK");
	}

	private static void ajouterUtilisateur(Utilisateur u) {
		AppChat.utilisateurList.ajouterUtilisateur(u);
	}

	public void creerCompte(String nom, String mdp) {
		System.out.println("Attention creation compte sans IP");
		AppChat.ajouterUtilisateur(new Utilisateur(nom));
		AppChat.ajouterPassword(nom, mdp);
	}

	public void creerCompte(String nom, String mdp, String IPAdress, int port_utilisateur) {
		AppChat.ajouterUtilisateur(new Utilisateur(nom, IPAdress, port_utilisateur));
		AppChat.ajouterPassword(nom, mdp);
	}

	/**
	 * Sauvegarde le mot de passe mdp de l'utilisateur nom
	 *
	 * @param nom
	 *            le nom de l'utilisateur dont le mot de passe est stocké
	 * @param mdp
	 *            le mot de passe à stocker
	 */
	private static void ajouterPassword(String nom, String mdp) {
		AppChat.passwordTable.put(nom, mdp.hashCode());
	}

	public boolean verifierMdp(String nom, String mdp) {
		return AppChat.passwordTable.get(nom).equals(mdp.hashCode());
	}

	public void supprimerCompte(Utilisateur u) {
		AppChat.utilisateurList.retirerUtilisateur(u);
		AppChat.passwordTable.remove(u.getNom());
	}

	public static UtilisateurList getUtilisateurList() {
		return AppChat.utilisateurList;
	}

	/**
	 *
	 * @param m
	 * @return La liste des utilisateur qui ont recu le message et qu'il faut donc
	 *         notifier
	 */
	@SuppressWarnings("unchecked")
	public UtilisateurList publieMessage(Message m) {
		String nomAuteur = m.getAuteur();
		UtilisateurList res = null;
		Utilisateur auteur;
		try {
			auteur = AppChat.utilisateurList.getUtilisateur(nomAuteur);
			auteur.ajouterMessageUtilisateur(m);
			auteur.getFollowerList().ajouterMessage(m);

			res = new UtilisateurList();

			res.setUtilisateurList((ArrayList<Utilisateur>) auteur.getFollowerList().getUtilisateurList().clone());

			/*
			 * On scan le contenu du message a la recherche de hashtag On stocke les hashtag
			 * dans un arraylist Si le hashtag est un nom d'utilisateur on leur envoie le
			 * message
			 */
			ArrayList<String> hashTagList = m.getHashtags();
			Utilisateur u;

			Iterator<String> it = hashTagList.iterator();
			String hashTag;
			while (it.hasNext()) {
				hashTag = it.next();
				try {
					u = AppChat.utilisateurList.getUtilisateur(hashTag);
					u.ajouterMessage(m);
					res.ajouterUtilisateur(u);
				} catch (UtilisateurInexistantException e) {
					// Le hashtag ne correspond pas a un utilisateur
					Iterator<Utilisateur> itu = AppChat.utilisateurList.getUtilisateurList().iterator();
					while (itu.hasNext()) {
						u = itu.next();
						if (u.getHashTagList().contains(hashTag)) {
							u.ajouterMessage(m);
							res.ajouterUtilisateur(u);
						}
					}
				}

				this.nouveauHashTagRecent(hashTag);

			}
		} catch (UtilisateurInexistantException e) {
			e.printStackTrace();
		}

		return res;

	}

	/**
	 * Permet de chercher un utilisateur a partir de son nom. La fonction cherche un
	 * utilisateur possedant exactement ce nom mais aussi independemment de la casse
	 * ou si le nom est contenu dans le nom d'un autre utilisateur ou inversement
	 *
	 * @param text
	 *            Le nom de l'utilisateur a chercher
	 * @return ArrayList des utilisateurs dont le nom correspond a la recherche
	 */
	public ArrayList<Utilisateur> chercherUtilisateur(String text) {
		ArrayList<Utilisateur> res = new ArrayList<Utilisateur>();
		if(text.equals("")) {
			res = AppChat.utilisateurList.getUtilisateurList();
		}else {
			String textToLowerCase = text.toLowerCase();
			// On essaie deja de voir si l'utilisateur existe
			try {
				res.add(AppChat.getUtilisateurList().getUtilisateur(text));
			} catch (UtilisateurInexistantException e) {
			}

			Iterator<Utilisateur> it = AppChat.utilisateurList.getUtilisateurList().iterator();
			Utilisateur u;
			String nom;
			while (it.hasNext()) {
				u = it.next();
				if (res.contains(u) == false) {
					nom = u.getNom().toLowerCase();
					if (nom.equals(textToLowerCase)) {
						res.add(u);
					} else if (nom.contains(textToLowerCase)) {
						res.add(u);
					} else if (textToLowerCase.contains(nom)) {
						res.add(u);
					}
				}
			}
		}
		return res;
	}

}
