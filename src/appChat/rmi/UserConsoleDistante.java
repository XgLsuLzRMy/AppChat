package appChat.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class UserConsoleDistante {

	private String auteur;
	private AppRMIServeur appDistant;

	public UserConsoleDistante(String u, AppRMIServeur a) {
		this.auteur = u;
		this.appDistant = a;
	}

	public void run() throws RemoteException {
		int choix = 1;
		Scanner lecture = new Scanner(System.in);
		while (choix != 0) {
			System.out.println("Que voulez-vous faire ?");
			System.out.println("0 - Quitter");
			System.out.println("1 - Publier un tweet");
			choix = Integer.parseInt(lecture.nextLine());
			if (choix == 1) {
				System.out.print("Ecrire le contenu du tweet ﾃ� publier : ");
				String str = lecture.nextLine();
				appDistant.publieMessage(str);
			}
		}
		lecture.close();
	}

	public static void main(String[] args) {
		Registry registry;
		Scanner lecture = new Scanner(System.in);
		System.out.print("Entrer votre nom ");
		String nom = lecture.nextLine();
		System.out.print("Entrer le nom de l'App: ");
		String app = lecture.nextLine();

		AppRMIServeur a;
		try {
			if (args.length > 0)
				registry = LocateRegistry.getRegistry(args[0]);
			else
				registry = LocateRegistry.getRegistry();
			a = (AppRMIServeur) registry.lookup(app);
			UserConsoleDistante console = new UserConsoleDistante(nom, a);
			console.run();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (NotBoundException ex) {
			ex.printStackTrace();
		}
		lecture.close();
	}
	
	public String getAuteur() {
		return this.auteur;
	}

}
