

import java.io.IOException;

public class MessageDispatcher extends Thread {
 
    @Override
    public void run() {
        System.out.println("Message Dispatcher Started");
 
        while (true) {
 
            Message currentMessage = Server.mQ.dequeue();
 
            for (ClientHandler client : Server.connectedUsers) {
               System.out.println(currentMessage.getTopicName() + " " + client.getTopicName() + " body:" + currentMessage.getBody());
               if(currentMessage.getTopicName().equals(client.getTopicName()) && !(currentMessage.getName().equals(client.getName())) && client.canReceive() == true) {
            	   try {
					client.dos.writeUTF(currentMessage.getBody());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               }
            }
        }
    }
}