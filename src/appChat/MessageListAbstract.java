package appChat;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * MessageListAbstract est une classe abstraite permettant de stocker une liste
 * de messages. Avoir une classe abstraite permet d'instancier une liste de
 * message sans savoir s'il s'agit d'une liste dont le nombre de messages est
 * limite ou non et ainsi factoriser des methodes communes.
 *
 */
public abstract class MessageListAbstract implements Serializable {
	private static final long serialVersionUID = -7117671540093494269L;

	protected LinkedList<Message> list;
	protected int nbMessage;

	/**
	 * Constructeur de MessageListAbstract. Instancie simplement une liste vide et
	 * un nombre de message egal a zero.
	 */
	public MessageListAbstract() {
		this.list = new LinkedList<Message>();
		this.nbMessage = 0;
	}

	/**
	 * Retire un message m donne en argument de la liste de messages si il est
	 * present dans la liste. La methode decremente aussi le nombre de messages.
	 * 
	 * @param m
	 *            Le message a retirer.
	 */
	public void supprimerMessage(Message m) {
		if (this.list.contains(m)) {
			this.list.remove(m);
			this.nbMessage--;
		}
	}

	/**
	 * Setteur permettant de modifier la lsite de messages.
	 * 
	 * @param list
	 *            La nouvelle liste de messages.
	 */
	public void setList(LinkedList<Message> list) {
		this.list = list;
	}

	/**
	 * Accesseur permettant de recuperer la liste de messages sous forme de
	 * LinkedList.
	 * 
	 * @return La liste de messages sous forme de LinkedList.
	 */
	public LinkedList<Message> getMessageList() {
		return this.list;
	}

	/**
	 * Accesseur permettant de recuperer le nombre de messages contenus dans la
	 * liste.
	 * 
	 * @return Le nombre de messages contenus dans la liste
	 */
	public int getNbMessage() {
		return this.nbMessage;
	}

	/**
	 * Donne une representation sous la forme de String de la MessageList.
	 * 
	 * @return La liste de message sous forme de String.
	 */
	public String toString() {
		return this.nbMessage + " messages\n" + this.list.toString();
	}

	/**
	 * Ajoute un nouveau message dans la liste. Methode abstraite.
	 * 
	 * @param m
	 *            Le nouveau message
	 * @return null dans le cas d'une MessageList, le message qui a ete retire pour
	 *         donner une place au nouveau message dans le cas d'une
	 *         MessageListRecent
	 */
	public abstract Message ajouterMessage(Message m);

}
