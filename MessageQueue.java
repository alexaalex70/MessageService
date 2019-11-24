
import java.util.ArrayList;


public class MessageQueue<Message> {
 
    private ArrayList<Message> queue = new ArrayList<Message>();
 
    public synchronized void enqueue(Message msg) {
        System.out.println("Enqueued the message");
        this.queue.add(msg);
        notify();
    }
    
    public int getSize() {
    	return queue.size();
    }
    
 
    public synchronized Message dequeue() {
        while (this.queue.isEmpty()) {
            try {
                System.out.println("Inside Dequeue -- Waiting");
                wait();
            } catch (Exception ex) {
                System.out.println("Exception occured in Dequeue");
            }
        }
        System.out.println("Dequeue -- Completed");
        return this.queue.remove(0);
    }
}