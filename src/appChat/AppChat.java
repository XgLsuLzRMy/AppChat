package appChat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import appChat.Utilisateur;
import appChat.UtilisateurList;
import appChat.ihm.FenetreServeur;


public class AppChat {

	public static int COMPTEUR_USER = 0;
	private static UtilisateurList utilisateurList = new UtilisateurList();
	private static Hashtable<String, String> passwordTable = new Hashtable<String, String>();
	private FenetreServeur fenetre;

	public AppChat (){
		this.fenetre = new FenetreServeur(this);
		this.fenetre.pack();
		this.fenetre.setVisible(true);
	}
	
	public AppChat(String fichierUtilisateurList, String fichierHashTable) {
		System.out.println("On lit les fichiers... ");
		System.out.print(fichierUtilisateurList + "... ");
		FileInputStream fis;
		try {
			fis = new FileInputStream(fichierUtilisateurList);
			try {
				ObjectInputStream ois = new ObjectInputStream(fis);
				try {
					AppChat.utilisateurList = (UtilisateurList) ois.readObject();
					System.out.println("OK");
					
					System.out.print(fichierHashTable + "... ");
					fis = new FileInputStream(fichierHashTable);
					ois = new ObjectInputStream(fis);
					AppChat.passwordTable = (Hashtable<String, String>) ois.readObject();
					System.out.print("OK");
					
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
		}catch(Exception e) {
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
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ecrireDansFichier(String fichierUtilisateurList, String fichierHashTable) {
		System.out.print("Ecriture de la liste d'utilisateurs... ");
		this.ecrireUtilisateursDansFichier(fichierUtilisateurList);
		System.out.println("OK");
		System.out.print("Ecriture de la hashtable... ");
		this.ecrirePasswordDansFichier(fichierHashTable);
		System.out.println("OK");
	}
	
	private static void ajouterUtilisateur(Utilisateur u) {
		AppChat.utilisateurList.ajouterUtilisateur(u);
	}

	public void creerCompte (String nom,String mdp) {
		AppChat.ajouterUtilisateur(new Utilisateur(nom));
		AppChat.ajouterPassword(nom, mdp);
		this.fenetre.refresh();
	}
	
	/**
	 * Sauvegarde le mot de passe mdp de l'utilisateur nom
	 * @param nom le nom de l'utilisateur dont le mot de passe est stocké
	 * @param mdp le mot de passe à stocker
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
		this.fenetre.refresh();
	}


	public static UtilisateurList getUtilisateurList(){
		return AppChat.utilisateurList;
	}
	
	/**
	 * 
	 * @param m
	 */
	public void publieMessage(Message m) {
		String nomAuteur = m.getAuteur();
		Utilisateur auteur;
		try {
			auteur = AppChat.utilisateurList.getUtilisateur(nomAuteur);
			auteur.ajouterMessageUtilisateur(m);
			auteur.getFollowerList().ajouterMessage(m);
		} catch (UtilisateurInexistantException e) {
			e.printStackTrace();
		}
		
	}

}
