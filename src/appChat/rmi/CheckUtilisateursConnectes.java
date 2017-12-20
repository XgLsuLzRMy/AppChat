package appChat.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import appChat.Utilisateur;

public class CheckUtilisateursConnectes extends Thread {

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
			System.out.println("1");
			while (i < AppRMIServeurImpl.utilisateursConnectes.length()) {
				System.out.println("2");
				u = AppRMIServeurImpl.utilisateursConnectes.get(i);
				System.out.println("3");
				if (u == null) {
					System.out.println("4");
					System.out.println(AppRMIServeurImpl.utilisateursConnectes
							.length()
							+ "\n"
							+ AppRMIServeurImpl.utilisateursConnectes + "\n");
					System.out.println("5");
				}
				i++;
				System.out.println("6");
				try {
					if (u.getRegistry() == null) {
						System.out.println("Registre null");
					} else {
						System.out.println("7");
						//UtilisateurServeur us = (UtilisateurServeur) u.getRegistry().lookup(u.getNom());
						//UtilisateurServeur us = (UtilisateurServeur) LocateRegistry.getRegistry(u.getIPAddress(), 1099).lookup(u.getNom());
						Registry reg = LocateRegistry.getRegistry(u.getIPAddress(), 1099);
						System.out.println("8");
						System.out.println(u.getNom() + "/" + u.getIPAddress());
						reg.lookup(u.getNom());
						System.out.println("8.5");
						UtilisateurServeur us = (UtilisateurServeur) reg.lookup(u.getNom());
						// UtilisateurServeur us = (UtilisateurServeur)
						// AppRMIServeurImpl.registry.lookup(u.getNom());
						try {
							System.out.println("9");
							us.ping();
							System.out.println("10");
							// us.refreshAffichageListeutilisateursConnectes();
							// System.out.println(u.getNom() + " est connecte");
						} catch (Exception e) {
							// System.out.println("changement");
							// changement = true;
							System.out.println(u.getNom() + " est deconnecte");
							AppRMIServeurImpl.utilisateursConnectes
									.retirerUtilisateur(u);
							i--;
						}
					}
					System.out.println("11");
				} catch (AccessException e) {
					System.out.println("erreur 12");
					e.printStackTrace();
				} catch (RemoteException e) {
					System.out.println("erreur 13");
					e.printStackTrace();
				} catch (NotBoundException e) {
					// System.out.println("changement");
					// changement = true;
					System.out.println(u.getNom() + " est deconnecte");
					AppRMIServeurImpl.utilisateursConnectes
							.retirerUtilisateur(u);
					i--;
				}
			}
			System.out.println("fin");
			/*
			 * if (changement) { System.out. println(
			 * "On refresh l'affichage de la liste des utilisateurs connectes");
			 * i = 0; while(i <
			 * AppRMIServeurImpl.utilisateursConnectes.length()) { u =
			 * AppRMIServeurImpl.utilisateursConnectes.get(i);
			 *
			 * try { UtilisateurServeur us = (UtilisateurServeur)
			 * AppRMIServeurImpl.registry.lookup(u.getNom());
			 * us.refreshAffichageListeutilisateursConnectes(); } catch
			 * (RemoteException | NotBoundException e) { e.printStackTrace(); }
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
