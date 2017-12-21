package appChat.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import appChat.Utilisateur;

/**
 * CheckUtilisateursConnectes permet de mettre a jour la liste des utilisateurs
 * connectes a intervalles reguliers.
 *
 */
public class CheckUtilisateursConnectes extends Thread {

	/**
	 * Constructeur de CheckUtilisateursConnectes On parcourt la liste des
	 * utilisateurs connectes et on verifie s'ils le sont toujours en executant la
	 * fonction ping. Si la fonction est exectutee alors l'utilisateur est en ligne
	 * sinon il est deconnecte.
	 */
	public CheckUtilisateursConnectes() {

	}

	@Override
	public void run() {
		while (true) {
			// System.out.println("Liste des utilisateurs connectes : \n" +
			// AppRMIServeurImpl.utilisateursConnectes);
			// boolean changement = false;
			Utilisateur u;
			int i = 0;
			while (i < AppRMIServeurImpl.utilisateursConnectes.length()) {
				u = AppRMIServeurImpl.utilisateursConnectes.get(i);
				if (u == null) {
					System.out.println(AppRMIServeurImpl.utilisateursConnectes.length() + "\n"
							+ AppRMIServeurImpl.utilisateursConnectes + "\n");
				}
				i++;
				System.out.println("6");
				try {
					if (u.getRegistry() == null) {
						System.out.println("Registre null");
					} else {
						UtilisateurServeur us = (UtilisateurServeur) u.getRegistry().lookup(u.getNom());
						try {
							us.ping();
							// us.refreshAffichageListeutilisateursConnectes();
							// System.out.println(u.getNom() + " est connecte");
						} catch (Exception e) {
							// System.out.println("changement");
							// changement = true;
							System.out.println(u.getNom() + " est deconnecte");
							AppRMIServeurImpl.utilisateursConnectes.retirerUtilisateur(u);
							i--;
						}
					}
				} catch (AccessException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					// System.out.println("changement");
					// changement = true;
					System.out.println(u.getNom() + " est deconnecte");
					AppRMIServeurImpl.utilisateursConnectes.retirerUtilisateur(u);
					i--;
				}
			}
			System.out.println("fin");
			/*
			 * if (changement) { System.out. println(
			 * "On refresh l'affichage de la liste des utilisateurs connectes"); i = 0;
			 * while(i < AppRMIServeurImpl.utilisateursConnectes.length()) { u =
			 * AppRMIServeurImpl.utilisateursConnectes.get(i);
			 *
			 * try { UtilisateurServeur us = (UtilisateurServeur)
			 * AppRMIServeurImpl.registry.lookup(u.getNom());
			 * us.refreshAffichageListeutilisateursConnectes(); } catch (RemoteException |
			 * NotBoundException e) { e.printStackTrace(); }
			 *
			 *
			 * i++; } }
			 */

			synchronized (this) {
				try {
					this.wait(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
