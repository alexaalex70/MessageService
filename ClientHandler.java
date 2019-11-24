
import java.io.*; 
import java.util.*; 
import java.net.*; 
import java.util.concurrent.Semaphore;

class ClientHandler implements Runnable 
{ 
	Scanner scn = new Scanner(System.in); 
	final DataInputStream dis; 
	final DataOutputStream dos; 
	private String topicName;
	private String name;
	private String type;
	private Socket socket; 
	private boolean canReceive;
	private boolean running;
	private Semaphore sem;
	
	// constructor 
	public ClientHandler(Socket s, String name, 
							DataInputStream dis, DataOutputStream dos, Semaphore sem) { 
		this.dis = dis; 
		this.dos = dos; 
		this.socket = s; 
		this.running=true;
		this.sem = sem;
	} 

	private void close() {
		System.out.println("Socket disconnected");
		
		try {
			this.socket.close();
		} catch (IOException e1) {
			System.out.println("Error closing socket connection");
		}
		this.running = false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean canReceive() {
		return this.canReceive;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getTopicName() {
		return this.topicName;
	}
	
	@Override
	public void run() { 
		String received; 
		while (this.running) 
		{ 
			try
			{ 
				
				System.out.println("Semaphore permited");
				// receive the string 
				received = dis.readUTF(); 
				
				if(received == null) {
					close();
					return;
				}
				sem.acquire();
				
				System.out.println("Received:" + received); 
			
				
				// break the string into message and recipient part 
				StringTokenizer st = new StringTokenizer(received, "#"); 
				String Header = st.nextToken(); 
				String Body = st.nextToken();
				
				StringTokenizer header = new StringTokenizer(Header, "-");
				if(header.countTokens() < 3) {
					System.out.println("Invalid header format");
					continue;
				}
				
				String me = header.nextToken();
				this.name = me;
				String type = header.nextToken();
				this.type = type;
				String topicName = header.nextToken();
				this.topicName = topicName;
				
				
				if(!type.equals("topic")) {
					this.canReceive = false;
					Server.mQ.enqueue(new Message(me, topicName, type, Body));
				}{
					this.canReceive = true;
				}

				sem.release();
				
			} catch (Exception e) { 
				if(e instanceof java.io.EOFException) {
					this.close();
				}
				
				if(e instanceof java.util.NoSuchElementException) {

					System.out.println(e.getMessage());
					System.out.println("Invalid socket format");
				}
				sem.release();
			} 
			
		} 
		try
		{ 
			// closing resources 
			this.dis.close(); 
			this.dos.close(); 
			
		}catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 
} 
