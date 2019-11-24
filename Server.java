

import java.io.*; 
import java.util.*;
import java.util.concurrent.Semaphore;
import java.net.*; 

public class Server 
{ 

	// Vector to store active clients 
	static Vector<ClientHandler> connectedUsers = new Vector<>(); 
	static MessageQueue<Message> mQ = new MessageQueue<Message>();
	
	
	// counter for clients 
	static int i = 0; 

	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 1234 
		ServerSocket ss = new ServerSocket(1234); 
		final Semaphore semaphore = new Semaphore(1);
		
		Socket s; 
		MessageDispatcher messageDispatcher = new MessageDispatcher();
		messageDispatcher.start();
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			// Accept the incoming request 
			s = ss.accept(); 

			System.out.println("New client request received : " + s); 
			
			// obtain input and output streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
			
			System.out.println("Creating a new handler for this client..."); 

			// Create a new handler object for handling this request. 
			ClientHandler mtch = new ClientHandler(s,"client " + i, dis, dos, semaphore); 

			// Create a new Thread with this object. 
			Thread t = new Thread(mtch); 
			
			System.out.println("Adding this client to active client list"); 

			// add this client to active clients list 
			connectedUsers.add(mtch); 
		
			// start the thread. 
			t.start(); 

			// increment i for new client. 
			// i is used for naming only, and can be replaced 
			// by any naming scheme 
			i++; 

		} 
	} 
} 
