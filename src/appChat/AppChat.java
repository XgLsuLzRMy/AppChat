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

import appChat.Utilisateur;
import appChat.UtilisateurList;

public class AppChat {

	private static UtilisateurList utilisateurList = new UtilisateurList();
	private static Hashtable<String, String> passwordTable = new Hashtable<String, String>();

	public AppChat() {

	}

	@SuppressWarnings("unchecked")
	public AppChat(String fichierUtilisateurList, String fichierHashTable) {
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
					AppChat.passwordTable = (Hashtable<String, String>) ois.readObject();
					// System.out.println("OK");

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
		AppChat.ajouterUtilisateur(new Utilisateur(nom));
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
		AppChat.passwordTable.put(nom, mdp);
	}

	public boolean verifierMdp(String nom, String mdp) {
		return AppChat.passwordTable.get(nom).equals(mdp);
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
			while (it.hasNext()) {
				try {
					u = AppChat.utilisateurList.getUtilisateur(it.next());
					u.ajouterMessage(m);
					res.ajouterUtilisateur(u);
				} catch (UtilisateurInexistantException e) {
					// Le hashtag ne correspond pas a un utilisateur
				}
			}
		} catch (UtilisateurInexistantException e) {
			e.printStackTrace();
		}

		return res;

	}

}
