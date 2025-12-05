package hash_lab7;

import javax.swing.JFrame;


public class menuGUI extends JFrame{
    
    public menuGUI(){
        setTitle ("Play Station - Menu");
        setSize (600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        
    }
    
    public static void main (String []args){
        menuGUI menu = new menuGUI();
        menu.setVisible(true);
    }
}
