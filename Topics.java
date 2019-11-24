import javax.swing.*;  
import java.awt.event.*;  

public class Topics extends JFrame implements ActionListener{  
    JLabel l;  
    JCheckBox cb1,cb2,cb3;  
    JButton b;  
    private boolean topicSelect;
    
    Topics(){  
    	this.setTopicSelect(false);
        l=new JLabel("Choose Topics you want to see:");  
        l.setBounds(50,50,300,20);  
        cb1=new JCheckBox("BoilerPlateEngine");  
        cb1.setBounds(100,100,150,20);  
        cb2=new JCheckBox("AI");  
        cb2.setBounds(100,150,150,20);  
        cb3=new JCheckBox("VR");  
        cb3.setBounds(100,200,150,20);  
        b=new JButton("Enter");  
        b.setBounds(100,250,80,30);  
        b.addActionListener(this);  
        add(l);add(cb1);add(cb2);add(cb3);add(b);  
        setSize(400,400);  
        setLayout(null);  
        setVisible(true);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
    } 
    
    public void actionPerformed(ActionEvent e){  
    	if (cb1.isSelected() || cb2.isSelected() || cb3.isSelected()) {
    		JOptionPane.showMessageDialog(this,"Welcome to chat!");
    		this.setTopicSelect(true);
    	} else {
    		JOptionPane.showMessageDialog(this,"Please select at least one Topic!");  
    	}
    }

	public boolean isTopicSelect() {
		return topicSelect;
	}

	public void setTopicSelect(boolean topicSelect) {
		this.topicSelect = topicSelect;
	}
	
	public void hideTopic() {
		setVisible(false);
	}
	
	public void showTopic() {
		setVisible(true);
	}
}  