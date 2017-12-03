package appChat;

import java.util.Date;

public class Message {
	
	private String contenu;
	private Date date;
	private int retweetCount;
	
	public Message() {
		this.contenu = "";
		this.date = new Date();
		this.retweetCount = 0;
	}
	
	public Message(String contenu) {
		this.contenu = contenu;
		this.date = new Date();
		this.retweetCount = 0;
	}
	
	public String getContenu() {
		return this.contenu;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public int getRetweetCount() {
		return this.retweetCount;
	}
	
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	
	public void incrementerRewtweetCount() {
		this.retweetCount++;
	}
}
