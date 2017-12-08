package appChat;

import java.io.Serializable;
import java.util.LinkedList;

public class MessageList extends MessageListAbstract implements Serializable{

	private static final long serialVersionUID = -522213576867692593L;

	public MessageList() {
		super();
	}
	
	@Override
	public Message ajouterMessage(Message m) {
		this.list.addFirst(m);
		this.nbMessage++;
		return null;
	}

	@Override
	public LinkedList<Message> getMessageList() {
		return this.list;
	}
	
	@Override
	public int getNbMessage() {
		return this.nbMessage;
	}
	
	
}
