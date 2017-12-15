package appChat.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import appChat.Utilisateur;

public class CheckUtilisateursConnectes extends Thread{
	
	
	public CheckUtilisateursConnectes() {
		
	}

	@Override
	public void run() {
		while(true) {
			//System.out.println("Liste des utilisateurs connectes : \n" + AppRMIServeurImpl.utilisateursConnectes);
			boolean changement = false;
			Utilisateur u;
			int i = 0;
			while(i < AppRMIServeurImpl.utilisateursConnectes.length()) {
				u = AppRMIServeurImpl.utilisateursConnectes.get(i);
				i++;
				try {
					UtilisateurServeur us = (UtilisateurServeur) AppRMIServeurImpl.registry.lookup(u.getNom());
					try {
						us.ping();
						us.refreshAffichageListeutilisateursConnectes();
						//System.out.println(u.getNom() + " est connecte");
					}catch(Exception e) {
						System.out.println("changement");
						changement = true;
						System.out.println(u.getNom() + " est deconnecte");
						AppRMIServeurImpl.utilisateursConnectes.retirerUtilisateur(u);
						i--;
					}
					
				} catch (AccessException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					System.out.println("changement");
					changement = true;
					System.out.println(u.getNom() + " est deconnecte");
					AppRMIServeurImpl.utilisateursConnectes.retirerUtilisateur(u);
					i--;
				}
			}
			
			/*if (changement) {
				System.out.println("On refresh l'affichage de la liste des utilisateurs connectes");
				i = 0;
				while(i < AppRMIServeurImpl.utilisateursConnectes.length()) {
					u = AppRMIServeurImpl.utilisateursConnectes.get(i);
					
					try {
						UtilisateurServeur us = (UtilisateurServeur) AppRMIServeurImpl.registry.lookup(u.getNom());
						us.refreshAffichageListeutilisateursConnectes();
					} catch (RemoteException | NotBoundException e) { 
						e.printStackTrace();
					}
					
					
					i++;
				}
			}*/
			
			synchronized(this) {
				try {
					this.wait(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
