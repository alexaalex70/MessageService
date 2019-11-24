import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class ChatClients {
	/*
	 * For server address
	 * For communication with the server in/out
	 */
	String serverAddress;
	String x = "null";
    Scanner in;
    PrintWriter out;
    /*
     * For visual part
     */
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(50);
    JTextArea messageArea = new JTextArea(16, 50);
    Topics chooseTopic = new Topics();
    JLabel l;  
    JCheckBox cb1,cb2,cb3;  
    JButton b;  
    
    
    /*
     * constructor
     */
    public ChatClients(String serverAddress) {
        this.serverAddress = serverAddress;
        
        String collectionSet[] = new String[20]; 
        textField.setEditable(false);
        messageArea.setEditable(false);
        chooseTopic.hideTopic();
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();

        // Send on enter then clear to prepare for next message
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Message sent to the server:");
                out.println(textField.getText());
                textField.setText("");
            }
        });
    }
    
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE
        );
    }
 
    
    /*
     * Run client, set socket with the server id, on part 59001 and also where
     * to read and write
     * Server send a message back with submit name/accepted/message
     */
    private void run() throws IOException {
        try {
            Socket socket = new Socket(serverAddress, 5000);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
                if (line.startsWith("SUBMITNAME")) {
                    out.println(getName());
                } else if (line.startsWith("NAMEACCEPTED")) {
                    this.frame.setTitle("Chat - " + line.substring(13));
                    chooseTopic.showTopic();
                } else if(line.startsWith("TOPICCHOOSE")) {
                    if(chooseTopic.isTopicSelect()) {
                     textField.setEditable(true);
                     out.println("topicSelected");
                    } else {
                    	out.println("");
                    }
                } else if(line.startsWith("successSelected"))  {
                	System.out.println("User selected with success Topics");
                	chooseTopic.hideTopic();
                } else if (line.startsWith("MESSAGE")) {
                    messageArea.append(line.substring(8) + "\n");
                }
            }
            socket.close();
        } finally {
            frame.setVisible(false);
            frame.dispose();
        }
    }
    
    /*
     * Each user when is connecting to the server need to pass IP as an argument
     * Than we run the client with Runnable
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        ChatClients client = new ChatClients(args[0]);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
    
    
}