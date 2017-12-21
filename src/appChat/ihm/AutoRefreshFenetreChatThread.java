package appChat.ihm;

public class AutoRefreshFenetreChatThread extends Thread {

	private FenetreChat fenetre;

	public AutoRefreshFenetreChatThread(FenetreChat fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void run() {
		while (true) {

			this.fenetre.refresh();

			synchronized (this) {
				try {
					this.wait(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
