package appChat;

import java.io.Serializable;
import java.util.LinkedList;

public class MessageList implements Serializable{

	/*
	 * Il faudrait faire une classe abstraite MessageListAbstraite puis 2 classes
	 * MessageList (pour une liste normale) et MessageListRecent (pour une liste
	 * avec un nombre maximal de messages pour ne pas submerger l'utilisateur de
	 * trop de messages lorsqu'il se connecte) ?
	 */

	private LinkedList<Message> list;
	private int nbMessage, nbMaxMessage;

	public MessageList() {
		this.list = new LinkedList<Message>();
		this.nbMaxMessage = 5;
		this.nbMessage = 0;
	}

	public void supprimerMessage(Message m) {
		if (this.list.contains(m)) {
			this.list.remove(m);
			this.nbMessage--;
		}

	}

	public void ajouterMessage(Message m) {
		this.list.add(m);
		if (this.nbMessage > nbMaxMessage) {
			this.list.removeFirst();
		} else {
			this.nbMessage++;
		}
	}

	public void setNbMaxMessage(int nbMaxMessage) {
		this.nbMaxMessage = nbMaxMessage;
	}

	public LinkedList<Message> getMessageList() {
		return this.list;
	}

	public int getNbMessage() {
		return this.nbMessage;
	}

	public int getNbMaxMessage() {
		return this.nbMaxMessage;
	}
	
	public String toString() {
		return this.nbMessage +" msg :\n" + this.list.toString();
	}
}
