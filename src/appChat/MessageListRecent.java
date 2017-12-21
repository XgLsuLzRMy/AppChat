package appChat;

import java.io.Serializable;

/**
 * MessageList permet de regrouper un nombre avec limite de messages. Son
 * fonctionnement est celui d'une pile FIFO.
 * 
 *
 */
public class MessageListRecent extends MessageListAbstract implements Serializable {

	private static final long serialVersionUID = 7502480414918650780L;
	// Le nombre maximal de messages qui serons stockes.
	private int nbMaxMessage;

	/**
	 * Constructeur de MessageListRecent sans arguments, le nombre maximal par
	 * defaut de messages est 20.
	 */
	public MessageListRecent() {
		super();
		this.nbMaxMessage = 20;
	}

	@Override
	/**
	 * Ajoute le message m a la liste. Fonctionne comme une pile FIFO.
	 * 
	 * @return null si le nombre de messages maximal n'a pas ete atteint, le message
	 *         qui a ete enleve pour laisse la place sinon.
	 */
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

	/**
	 * Setteur permettant de modifier le nombre maximal de messages dans la liste
	 * 
	 * @param nbMaxMessage
	 *            Le nouveau nombre maximal de messages dans la liste.
	 */
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
