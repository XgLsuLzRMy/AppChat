package appChat;

import java.util.LinkedList;

public class MessageList {
	
	LinkedList<Message> list;
	
	public MessageList() {
		this.list = new LinkedList<Message>();
	}
	
	public void ajouterMessage(Message m){
		this.list.add(m);
	}
}
