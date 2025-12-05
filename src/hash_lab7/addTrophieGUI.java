package hash_lab7;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.io.File;

public class addTrophieGUI extends JFrame {

    private JFrame menu; 
    private PSNUsers psn;
    private JTextField txtUser, txtGame, txtTrophy;
    private JComboBox<EnumTrophy> comboTipo;
    private File selectedImage = null;

    public addTrophieGUI(PSNUsers psn, JFrame menu) {

        this.psn = psn;
        this.menu = menu;

        setTitle("Play Station - Agregar Trofeo");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.white);
        setLayout(null);

        
        JLabel lblTitle = new JLabel("Agregar Trofeo");
        lblTitle.setFont(new Font("Comic Sans", Font.BOLD, 30));
        lblTitle.setBounds(0, 20, 500, 40);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle);

      
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setBounds(50, 90, 150, 30);
        add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(180, 90, 250, 30);
        add(txtUser);

      
        JLabel lblGame = new JLabel("Juego:");
        lblGame.setBounds(50, 140, 150, 30);
        add(lblGame);

        txtGame = new JTextField();
        txtGame.setBounds(180, 140, 250, 30);
        add(txtGame);

        
        JLabel lblTrophy = new JLabel("Nombre trofeo:");
        lblTrophy.setBounds(50, 190, 150, 30);
        add(lblTrophy);

        txtTrophy = new JTextField();
        txtTrophy.setBounds(180, 190, 250, 30);
        add(txtTrophy);

       
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(50, 240, 150, 30);
        add(lblTipo);

        comboTipo = new JComboBox<>(EnumTrophy.values());
        comboTipo.setBounds(180, 240, 250, 30);
        add(comboTipo);

        
        JButton btnImagen = new JButton("Seleccionar imagen");
        btnImagen.setBounds(50, 290, 170, 35);
        btnImagen.setBackground(new Color(226,226,226));
        add(btnImagen);

        JLabel lblImagen = new JLabel("Ningún archivo seleccionado");
        lblImagen.setBounds(230, 290, 240, 35);
        add(lblImagen);

        btnImagen.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedImage = chooser.getSelectedFile();
                lblImagen.setText(selectedImage.getName());
            }
        });

        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(80, 360, 150, 45);
        btnGuardar.setBackground(new Color(5,108,201));
        btnGuardar.setForeground(Color.white);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> saveTrophy());

        
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(250, 360, 150, 45);
        btnVolver.setBackground(new Color(226,226,226));
        add(btnVolver);

        btnVolver.addActionListener(e -> {
            menu.setVisible(true);
            dispose(); 
        });
    }

    private void saveTrophy() {

        String user = txtUser.getText().trim();
        String game = txtGame.getText().trim();
        String trophyName = txtTrophy.getText().trim();
        EnumTrophy type = (EnumTrophy) comboTipo.getSelectedItem();

        if (user.isEmpty() || game.isEmpty() || trophyName.isEmpty() || selectedImage == null) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        try {

            psn.addTrophieTo(
                user,
                game,
                trophyName,
                type,
                selectedImage.getAbsolutePath()
            );

            JOptionPane.showMessageDialog(this, "Trofeo agregado correctamente.");
            menu.setVisible(true);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
