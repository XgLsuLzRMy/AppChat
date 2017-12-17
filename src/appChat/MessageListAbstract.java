package appChat;

import java.io.Serializable;
import java.util.LinkedList;

public abstract class MessageListAbstract implements Serializable {
	private static final long serialVersionUID = -7117671540093494269L;
	protected LinkedList<Message> list;
	protected int nbMessage;

	public abstract Message ajouterMessage(Message m);

	public MessageListAbstract() {
		this.list = new LinkedList<Message>();
		this.nbMessage = 0;
	}

	public void supprimerMessage(Message m) {
		if (this.list.contains(m)) {
			this.list.remove(m);
			this.nbMessage--;
		}
	}

	public void setList(LinkedList<Message> list) {
		this.list = list;
	}

	public LinkedList<Message> getMessageList() {
		return this.list;
	}

	public int getNbMessage() {
		return this.nbMessage;
	}

	public String toString() {
		return this.nbMessage + " messages\n" + this.list.toString();
	}

}
