package appChat;

import java.io.Serializable;

public class MessageListRecent extends MessageListAbstract implements Serializable {

	private static final long serialVersionUID = 7502480414918650780L;
	private int nbMaxMessage;

	public MessageListRecent() {
		super();
		this.nbMaxMessage = 20;
	}

	// Renvoie le message dépilé pour l'ajouter à la liste de tous les messages
	// reçus
	@Override
	public Message ajouterMessage(Message m) {
		Message res = null;
		this.list.addFirst(m);
		if (this.nbMessage > nbMaxMessage) {
			res = this.list.getLast();
			this.list.removeLast();
		} else {
			this.nbMessage++;
		}

		return res;
	}

	public void setNbMaxMessage(int nbMaxMessage) {
		if (nbMaxMessage > 0) {
			this.nbMaxMessage = nbMaxMessage;
		} else {
			this.nbMaxMessage = 0;
		}
	}

	public int getNbMaxMessage() {
		return this.nbMaxMessage;
	}

}
