package hash_lab7;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.io.RandomAccessFile;

public class infoGUI extends JFrame {

    private PSNUsers psn;
    private JTextField txtUser;
    private JTextArea txtInfo;

    public infoGUI(PSNUsers psn) {
        this.psn = psn;

        setTitle("Información del Jugador");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.white);
        setLayout(null);

        // TITULO
        JLabel lblTitle = new JLabel("Información del Jugador");
        lblTitle.setFont(new Font("Comic Sans", Font.BOLD, 28));
        lblTitle.setBounds(0, 20, 600, 40);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle);

        // Usuario
        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(50, 80, 150, 30);
        add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(150, 80, 250, 30);
        add(txtUser);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(420, 80, 100, 30);
        btnBuscar.setBackground(new Color(5,108,201));
        btnBuscar.setForeground(Color.white);
        add(btnBuscar);

        // AREA INFO
        txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Consolas", Font.PLAIN, 14));
        
        JScrollPane scroll = new JScrollPane(txtInfo);
        scroll.setBounds(50, 140, 500, 380);
        add(scroll);

        btnBuscar.addActionListener(e -> showPlayerInfo());
    }

    private void showPlayerInfo() {
        String username = txtUser.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un usuario.");
            return;
        }

        try {
            long pos = psn.getUsers().search(username);

            if (pos == -1) {
                txtInfo.setText("Usuario no encontrado.");
                return;
            }

            RandomAccessFile raf = psn.getRaf();
            raf.seek(pos);

            // Leer datos del usuario
            String name = raf.readUTF();
            int points = raf.readInt();
            int trophies = raf.readInt();
            boolean active = raf.readBoolean();

            // Mostrar datos del archivo principal
            StringBuilder sb = new StringBuilder();
            sb.append("=== INFORMACIÓN DEL JUGADOR ===\n\n");
            sb.append("Username: ").append(name).append("\n");
            sb.append("Puntos: ").append(points).append("\n");
            sb.append("Trofeos: ").append(trophies).append("\n");
            sb.append("Estado: ").append(active ? "Activo" : "Inactivo").append("\n\n");

            sb.append("=== TROFEOS OBTENIDOS ===\n");

            // Leer archivo trophies.psn
            try (RandomAccessFile trophyFile = new RandomAccessFile("trophies.psn", "r")) {

                while (trophyFile.getFilePointer() < trophyFile.length()) {
                    String user = trophyFile.readUTF();
                    String type = trophyFile.readUTF();
                    String game = trophyFile.readUTF();
                    String desc = trophyFile.readUTF();
                    String date = trophyFile.readUTF();
                    int imgLen = trophyFile.readInt();
                    
                    // Saltar bytes de la imagen
                    trophyFile.skipBytes(imgLen);

                    if (user.equals(username)) {
                        sb.append("\n");
                        sb.append("Fecha: ").append(date).append("\n");
                        sb.append("Tipo: ").append(type).append("\n");
                        sb.append("Juego: ").append(game).append("\n");
                        sb.append("Trofeo: ").append(desc).append("\n");
                        sb.append("Imagen: ").append(imgLen).append(" bytes\n");
                        sb.append("-------------------------------------\n");
                    }
                }
            }

            txtInfo.setText(sb.toString());

        } catch (Exception ex) {
            txtInfo.setText("Error: " + ex.getMessage());
        }
    }
}
