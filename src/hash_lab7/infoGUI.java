package hash_lab7;

import java.awt.*;
import javax.swing.*;
import java.io.RandomAccessFile;

public class infoGUI extends JFrame {

    private PSNUsers psn;
    private JFrame menu;
    private JTextField txtUser;
    private JPanel contentPanel;
    private JPanel imagesPanel;
    private JLabel lblText;

    public infoGUI(PSNUsers psn, JFrame menu) {
    this.psn = psn;
    this.menu = menu;

        setTitle("Información del Jugador");
        setSize(700,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.white);
        setLayout(null);

        
        JLabel lblTitle = new JLabel("Información del Jugador");
        lblTitle.setFont(new Font("Comic Sans", Font.BOLD, 28));
        lblTitle.setBounds(0,20,700,40);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(50,80,100,30);
        lblUser.setFont(new Font("Arial",Font.BOLD,14));
        add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(150,80,300,30);
        add(txtUser);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(470,80,120,30);
        btnBuscar.setBackground(new Color(5,108,201));
        btnBuscar.setForeground(Color.white);
        add(btnBuscar);
        
        JButton btnVolver = new JButton("Volver");
btnVolver.setBounds(50, 50, 80, 30); 
btnVolver.setBackground(new Color(226,226,226));
add(btnVolver);

btnVolver.addActionListener(e -> {
    menu.setVisible(true);
    dispose();
});

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.white);

        lblText = new JLabel();
        lblText.setFont(new Font("Consolas", Font.PLAIN, 14));
        lblText.setVerticalAlignment(SwingConstants.TOP);
        contentPanel.add(lblText);

        imagesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imagesPanel.setBackground(Color.white);
        contentPanel.add(imagesPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBounds(50,140,600,600);
        add(scrollPane);

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
                lblText.setText("<html><pre>Usuario no encontrado.</pre></html>");
                imagesPanel.removeAll();
                imagesPanel.revalidate();
                imagesPanel.repaint();
                return;
            }

            RandomAccessFile raf = psn.getRaf();
            raf.seek(pos);

            String name = raf.readUTF();
            int points = raf.readInt();
            int trophies = raf.readInt();
            boolean active = raf.readBoolean();

            StringBuilder sb = new StringBuilder();
            sb.append("<html><pre>");
            sb.append("=== INFORMACIÓN DEL JUGADOR ===\n\n");
            sb.append("Username: ").append(name).append("\n");
            sb.append("Puntos: ").append(points).append("\n");
            sb.append("Trofeos: ").append(trophies).append("\n");
            sb.append("Estado: ").append(active ? "Activo" : "Inactivo").append("\n");
            sb.append("\n=== TROFEOS OBTENIDOS ===\n");

            imagesPanel.removeAll();

            try (RandomAccessFile trophyFile = new RandomAccessFile("trophies.psn", "r")) {

                while (trophyFile.getFilePointer() < trophyFile.length()) {
                    String user = trophyFile.readUTF();
                    String type = trophyFile.readUTF();
                    String game = trophyFile.readUTF();
                    String desc = trophyFile.readUTF();
                    String date = trophyFile.readUTF();
                    int imgLen = trophyFile.readInt();

                    byte[] imgBytes = new byte[imgLen];
                    trophyFile.readFully(imgBytes);

                    if (user.equals(username)) {
                        sb.append("\n");
                        sb.append("Fecha: ").append(date).append("\n");
                        sb.append("Tipo: ").append(type).append("\n");
                        sb.append("Juego: ").append(game).append("\n");
                        sb.append("Trofeo: ").append(desc).append("\n");
                        sb.append("-------------------------------------\n");

                        ImageIcon icon = new ImageIcon(imgBytes);
                        Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(scaled);

                        JLabel lblImg = new JLabel(icon);
                        lblImg.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                        imagesPanel.add(lblImg);
                    }
                }
            }

            sb.append("</pre></html>");
            lblText.setText(sb.toString());

            imagesPanel.revalidate();
            imagesPanel.repaint();

        } catch (Exception ex) {
            lblText.setText("<html><pre>Error: " + ex.getMessage() + "</pre></html>");
        }
    }
}
