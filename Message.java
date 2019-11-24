

import java.net.*; 

public class Message {

	private String name;
	private String topicName;
	private boolean isTopicReg;
	private Socket toSocket;
	private String body;
	
	
	public Message(String name, String topic, String type, String body) {
		this.name = name;
		this.isTopicReg = type.equals("topic") ? true : false;
		this.topicName = topic;
		this.body = body;
	}
	
	public boolean isTopicReg() {
		return this.isTopicReg;
	}
	
	public String getTopicName() {
		return this.topicName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public Socket getSocket() {
		return this.toSocket;
	}
}
