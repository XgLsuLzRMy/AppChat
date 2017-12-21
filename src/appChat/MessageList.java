package appChat;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * MessageList permet de regrouper un nombre non limite de messages.
 * 
 *
 */
public class MessageList extends MessageListAbstract implements Serializable {

	private static final long serialVersionUID = -522213576867692593L;

	/**
	 * Constructeur de MessageList. Appelle simplement le constructeur de
	 * MessageListAbstract.
	 */
	public MessageList() {
		super();
	}

	@Override
	/**
	 * Ajoute un message donne en argument a la liste de messages.
	 * 
	 * @param m
	 *            Le message a rajouter dans la liste.
	 * @return Retourn toujours null.
	 */
	public Message ajouterMessage(Message m) {
		this.list.addFirst(m);
		this.nbMessage++;
		return null;
	}

	@Override
	/**
	 * Accesseur permettant de recuperer la liste des messages sous forme de
	 * LinkedList
	 * 
	 * @return La liste des message sous forme de LinkedList.
	 */
	public LinkedList<Message> getMessageList() {
		return this.list;
	}

	@Override
	/**
	 * Donne le nombre de messages contenus dans la liste.
	 * 
	 * @return le nombre de messages contenus dans la liste.
	 */
	public int getNbMessage() {
		return this.nbMessage;
	}

}
