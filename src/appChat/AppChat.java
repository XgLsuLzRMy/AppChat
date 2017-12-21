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

/**
 * AppChat regroupe l'ensemble des Utilisateurs possedant un compte. Elle
 * possede donc une liste des Utilisateurs et une table de hachage permettant de
 * mettre en relation un Utilisateur et son mot de passe. AppChat possede un
 * systeme de sauvegarde automatique de la liste des Utilisateurs et de leur
 * mots de passes afin de recuperer les donnees apres un arret du serveur.
 * AppChat permet de transmettre un message a une liste de destinataires, de
 * chercher un Utilisateur specifique ou encore de gerer les hashtags.
 * 
 * 
 *
 */
public class AppChat {
	// La liste des Utilisateurs qui possedent un compte
	private static UtilisateurList utilisateurList = new UtilisateurList();
	/*
	 * La table de hashage permettant de mettre en lien un Utilisateur et son mot de
	 * passe pour la connexion La clef est le nom de l'utilisateur et on recupere un
	 * entier qui est le String.haschCode() du mot de passe
	 */
	private static Hashtable<String, Integer> passwordTable = new Hashtable<String, Integer>();

	// La liste des derniers hashtags utilises par n'importe quel utilisateur de
	// AppChat
	private LinkedList<String> hashTagsRecents;
	// Le nombre maximum de hashtags recents qui sont stockes
	private int nbMaxHashTagsRecents = 5;

	/**
	 * Constructeur de AppChat simple : ne fait qu'instancier une liste de hashtags
	 * recents vide.
	 */
	public AppChat() {
		this.hashTagsRecents = new LinkedList<String>();
	}

	@SuppressWarnings("unchecked")
	/**
	 * Constructeur de AppChat dans lequel la liste des utilisateurs ayant un compte
	 * est recuperee a partir de fichiers dont les chemins sont donnes en argument.
	 * 
	 * @param fichierUtilisateurList
	 *            Le chemin vers le fichier contenant la liste des utilisateurs
	 *            ayant un compte.
	 * @param fichierHashTable
	 *            Le chemin vers le fichier contenant la table de hashage mettant en
	 *            relation un utilisateur et son mot de passe.
	 */
	public AppChat(String fichierUtilisateurList, String fichierHashTable) {
		this.hashTagsRecents = new LinkedList<String>();
		FileInputStream fis;
		try {
			fis = new FileInputStream(fichierUtilisateurList);
			try {
				ObjectInputStream ois = new ObjectInputStream(fis);
				try {
					AppChat.utilisateurList = (UtilisateurList) ois.readObject();
					fis = new FileInputStream(fichierHashTable);
					ois = new ObjectInputStream(fis);
					AppChat.passwordTable = (Hashtable<String, Integer>) ois.readObject();

					// AppChat.utilisateurList.resetRegistry(); // pas utile car lors du lancement
					// du serveur aucun utilisateur ne peut etre deja connecte

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

	/**
	 * Ajoute un hashtag a la liste des hashtags recents. Si le nombre maximal de
	 * hashtags est atteint le plus ancien est supprime.
	 * 
	 * @param hashTag
	 *            Le hashtag a rajouter dans la liste.
	 */
	public void nouveauHashTagRecent(String hashTag) {
		this.hashTagsRecents.removeFirstOccurrence(hashTag);
		this.hashTagsRecents.addFirst(hashTag);
		if (this.nbMaxHashTagsRecents < this.hashTagsRecents.size()) {
			this.hashTagsRecents.removeLast();
		}
	}

	/**
	 * Accesseur permettant de recuperer la liste des hashtags recents.
	 * 
	 * @return Une LinkedList regroupant les hashtags recents.
	 */
	public LinkedList<String> getHashTagsRecents() {
		return this.hashTagsRecents;
	}

	/**
	 * Ecrit la table de hashage regroupant les mots de passe des utilisateurs dans
	 * le fichier dont le chemin est donne en argument. Le contenu du fichier est
	 * ecrase et remplace par la nouvelle table de hashage. Si le fichier n'existe
	 * pas il sera cree.
	 * 
	 * @param nomFichier
	 *            Le chemin vers le fichier ou l'on souhaite ecrire la table de mot
	 *            de passe.
	 */
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

	/**
	 * Ecrit la liste des utilisateurs dans le fichier dont le chemin est donne en
	 * argument. Le contenu du fichier est ecrase et remplace par la nouvelle liste.
	 * Si le fichier n'existe pas il sera cree.
	 * 
	 * @param nomFichier
	 *            Le chemin vers le fichier ou l'on souhaite ecrire la liste
	 *            d'utilisateurs.
	 */
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

	/**
	 * Ecrit la liste des utilisateurs ayant un compte ainsi que la table de hashage
	 * des mots de passe dans les fichiers dont les chemins sont passes en argument.
	 * Cette fonction fait simplement appel aux fonctions
	 * ecrireUtilisateursDansFichier et ecrirePasswordDansFichier.
	 * 
	 * @param fichierUtilisateurList
	 *            Le chemin vers le fichier ou l'on souhaite ecrire la liste
	 *            d'utilisateurs.
	 * @param fichierHashTable
	 *            Le chemin vers le fichier ou l'on souhaite ecrire la table de mot
	 *            de passe.
	 */
	public void ecrireDansFichier(String fichierUtilisateurList, String fichierHashTable) {
		this.ecrireUtilisateursDansFichier(fichierUtilisateurList);
		this.ecrirePasswordDansFichier(fichierHashTable);
	}

	/**
	 * Ajoute un utilisateur u donne en argument a la liste des utilisateurs ayant
	 * un compte. Cette methode n'est pas accessible depuis une classe exterieure
	 * car avant d'ajouter un utilisateur il faut d'abord verifier qu'il n'existe
	 * pas deja et lui attribuer un mot de passe.
	 * 
	 * @param u
	 *            L'utilisateur que l'on souhaite rajouter dans la liste.
	 */
	private static void ajouterUtilisateur(Utilisateur u) {
		AppChat.utilisateurList.ajouterUtilisateur(u);
	}

	/**
	 * Ajoute un nouvel utilisateur dans la liste des utilisateurs. Une IP et un
	 * port par defaut seront utilises.
	 * 
	 * @param nom
	 *            Le nom du nouvel utilisateur
	 * @param mdp
	 *            Le mot de passe hashe du nouvel utilisateur
	 */
	public void creerCompte(String nom, String mdp) {
		System.out.println("Attention creation compte sans IP");
		AppChat.ajouterUtilisateur(new Utilisateur(nom));
		AppChat.ajouterPassword(nom, mdp);
	}

	/**
	 * Ajoute un nouvel utilisateur dans la liste des utilisateurs.
	 * 
	 * @param nom
	 *            Le nom du nouvel utilisateur
	 * @param mdp
	 *            Le mot de passe hashe du nouvel utilisateur
	 * @param IPAdress
	 *            L'addresse IP avec laquelle le nouvel utilisateur s'est connecte
	 * @param port_utilisateur
	 *            Le port avec lequel le nouvel utilisateur s'est connecte
	 */
	public void creerCompte(String nom, String mdp, String IPAdress, int port_utilisateur) {
		AppChat.ajouterUtilisateur(new Utilisateur(nom, IPAdress, port_utilisateur));
		AppChat.ajouterPassword(nom, mdp);
	}

	/**
	 * Sauvegarde le mot de passe de l'utilisateur.
	 * 
	 * @param nom
	 *            le nom de l'utilisateur dont le mot de passe est stocke
	 * @param mdp
	 *            le mot de passe a stocker
	 */
	private static void ajouterPassword(String nom, String mdp) {
		AppChat.passwordTable.put(nom, mdp.hashCode());
	}

	/**
	 * Verifie que le mot de passe mdp en argument correspond bien au mot de passe
	 * de l'utilisateur possedant le nom donne en argument.
	 * 
	 * @param nom
	 *            Le nom de l'utilisateur dont le mot de passe est verifie.
	 * @param mdp
	 *            Le mot de passe qui sera verifie.
	 * @return true si le mot de passe correspond, false sinon
	 */
	public boolean verifierMdp(String nom, String mdp) {
		return AppChat.passwordTable.get(nom).equals(mdp.hashCode());
	}

	/**
	 * Retire un utilisateur de la liste des utilisateurs ayant un compte. Cela
	 * supprime aussi son mot de passe.
	 * 
	 * @param u
	 *            L'utilisateur qui sera retire.
	 */
	public void supprimerCompte(Utilisateur u) {
		AppChat.utilisateurList.retirerUtilisateur(u);
		AppChat.passwordTable.remove(u.getNom());
	}

	/**
	 * Accesseur pour obtenir la liste des utilisateur ayant un compte.
	 * 
	 * @return une UtilisateurList contenant les utilisateurs ayant un compte.
	 */
	public static UtilisateurList getUtilisateurList() {
		return AppChat.utilisateurList;
	}

	/**
	 * Ajoute le message aux destinataires. Les destinataires sont les followers de
	 * l'auteur, les utilisateurs abonnes au nom de l'auteur ou les utilisateur
	 * abonnes a un des hashtags contenus dans le message.
	 * 
	 * @param m
	 *            Le message qui sera publie.
	 * @return La liste des utilisateur qui ont recu le message. (utile pour les
	 *         notifier par exemple)
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
			 * On scan le contenu du message a la recherche de hashtag On stocke les
			 * hashtags dans un arraylist Si le hashtag est un nom d'utilisateur on leur
			 * envoie le message
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
	 * Cherche un utilisateur a partir de son nom. La fonction cherche un
	 * utilisateur possedant exactement le nom donne en argument mais aussi
	 * independemment de la casse ou si le nom est contenu dans le nom d'un autre
	 * utilisateur et inversement
	 *
	 * @param text
	 *            Le nom de l'utilisateur a chercher.
	 * @return ArrayList des utilisateurs dont le nom correspond a la recherche.
	 */
	public ArrayList<Utilisateur> chercherUtilisateur(String text) {
		ArrayList<Utilisateur> res = new ArrayList<Utilisateur>();
		if (text.equals("")) {
			res = AppChat.utilisateurList.getUtilisateurList();
		} else {
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
