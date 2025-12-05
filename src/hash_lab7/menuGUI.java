package hash_lab7;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class menuGUI extends JFrame{
    
    public menuGUI(){
        setTitle ("Play Station - Menu");
        setSize (600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(Color.white);
        getContentPane().setLayout(null);
        
        ImageIcon logo = new ImageIcon("src/hash_Lab7/logo.png"); 
        Image img = logo.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        logo = new ImageIcon(img);

        JLabel labelLogo = new JLabel(logo);
        labelLogo.setBounds((600 - 250)/2, 30, 250, 120); 
        getContentPane().add(labelLogo);
        
        JLabel titulo = new JLabel("Menu",SwingConstants.CENTER);
        titulo.setFont(new Font("Comic Sans",Font.BOLD,45));
        titulo.setBounds(0, 150, 600, 45); 
        getContentPane().add(titulo);
        
        JButton agregar = new JButton("Agregar Usuarios");
        agregar.setBounds(200,220,200,45);
        agregar.setBackground(Color.GRAY);
        getContentPane().add(agregar);
        
        JButton desactivar = new JButton("Desactivar Usuarios");
        desactivar.setBounds(200,290,200,45);
        desactivar.setBackground(Color.GRAY);
        getContentPane().add(desactivar);
        
        JButton trofeos = new JButton("Agregar Trofeos");
        trofeos.setBounds(200,360,200,45);
        trofeos.setBackground(Color.GRAY);
        getContentPane().add(trofeos);
        
        JButton info = new JButton("Informacion Jugador");
        info.setBounds(200,430,200,45);
        info.setBackground(Color.GRAY);
        getContentPane().add(info);
        
        JButton salir = new JButton ("Salir");
        salir.setBounds(20,20,120,45);
        getContentPane().add(salir);
        
    }
    
    public static void main (String []args){
        menuGUI menu = new menuGUI();
        menu.setVisible(true);
    }
}
