package hash_lab7;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;

public class menuGUI extends JFrame {
    
    private PSNUsers psn;

    public menuGUI() {
        setTitle("Play Station - Menu");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        Color azulPlay = new Color(0, 48, 135);
        getContentPane().setBackground(azulPlay);
        getContentPane().setLayout(null);

        try {
            psn = new PSNUsers("usuarios");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageIcon logo = new ImageIcon("src/hash_Lab7/logo2.png");
        Image img = logo.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        logo = new ImageIcon(img);

        JLabel labelLogo = new JLabel(logo);
        labelLogo.setBounds((600 - 250)/2, 30, 250, 120);
        add(labelLogo);

        JLabel titulo = new JLabel("Menu",SwingConstants.CENTER);
        titulo.setFont(new Font("Comic Sans", Font.BOLD, 45));
        titulo.setBounds(0,150,600,45);
        add(titulo);

        JButton agregar = new JButton("Agregar Usuario");
        agregar.setBounds(200,220,200,45);
        agregar.setBackground(Color.white);
        add(agregar);

        agregar.addActionListener(e -> {
    String user = JOptionPane.showInputDialog("Ingrese un usuario:");
    if(user == null || user.trim().isEmpty()) return;

    try {

        long pos = psn.getUsers().search(user.trim());
        
        if (pos != -1) {
            // Usuario ya existe y está activo
            JOptionPane.showMessageDialog(null, "Usuario ya existe. No se puede agregar.");
            return;
        }

        // Usuario nuevo: valores por defecto
        psn.addUser(user.trim());
        JOptionPane.showMessageDialog(null, "Usuario agregado correctamente.\n"
                + "\nPuntos: 0"
                + "\nTrofeos: 0"
                + "\nEstado: activo");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "No se pudo agregar: " + ex.getMessage());
    }
});

        JButton desactivar = new JButton("Desactivar Usuario");
        desactivar.setBounds(200,290,200,45);
        desactivar.setBackground(Color.white);
        add(desactivar);

        desactivar.addActionListener(e -> {
    String user = JOptionPane.showInputDialog("Usuario a desactivar:");
    if(user == null || user.trim().isEmpty()) return;

    try {
        long pos = psn.getUsers().search(user.trim());
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            return;
        }

        // Leer estado actual
        psn.getRaf().seek(pos);
        psn.getRaf().readUTF();
        psn.getRaf().readInt();
        psn.getRaf().readInt();
        boolean activo = psn.getRaf().readBoolean();

        if (!activo) {
            JOptionPane.showMessageDialog(null, "El usuario ya estaba desactivado.");
            return;
        }

        // Desactivar correctamente
        psn.deactivateUser(user.trim());
        JOptionPane.showMessageDialog(null, "Usuario desactivado correctamente.");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "No se pudo desactivar: " + ex.getMessage());
    }
});


        JButton trofeos = new JButton("Agregar Trofeos");
        trofeos.setBounds(200,360,200,45);
        trofeos.setBackground(Color.white);
        add(trofeos);
        trofeos.addActionListener(e -> {
    new addTrophieGUI(psn).setVisible(true);
});


        JButton info = new JButton("Informacion Jugador");
        info.setBounds(200,430,200,45);
        info.setBackground(Color.white);
        add(info);
        info.addActionListener(e -> {
    new infoGUI(psn).setVisible(true);
});

        JButton btnsalir = new JButton("Salir");
        btnsalir.setBounds(20,20,120,45);
        btnsalir.setBackground(Color.white);
        add(btnsalir);

        btnsalir.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        new menuGUI().setVisible(true);
    }
}
