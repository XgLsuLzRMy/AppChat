package appChat;

/**
 * SauvegardeReguliere permet de sauvegarder les donnees des utilisateurs et les
 * mots de passes a intervalles reguliers dans des fichiers
 * 
 *
 */
public class SauvegardeReguliere extends Thread {

	private AppChat a;

	public SauvegardeReguliere(AppChat a) {
		this.a = a;
	}

	@Override
	public void run() {
		while (true) {

			// System.out.println("Sauvegarde... ");

			this.a.ecrireDansFichier("utilisateurs", "passwords");

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
