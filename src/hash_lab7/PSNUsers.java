/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hash_lab7;

/**
 *
 * @author eduar
 */

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;

public class PSNUsers {
    
    private RandomAccessFile raf;
    private HashTable users;
    
    public PSNUsers(String filename)throws IOException{
        this.raf=new RandomAccessFile(filename + ".psn","rw");
        this.users=new HashTable();
        reloadHashTable();
    }
    
    private void reloadHashTable()throws IOException{
        raf.seek(0);
        while(raf.getFilePointer() < raf.length()){
            long position = raf.getFilePointer();
            String username = raf.readUTF();
            int points = raf.readInt();
            int trophies = raf.readInt();
            boolean active= raf.readBoolean();
            
            if(active){
                users.add(username, position);
            }
        }
    }
    
    public void addUser(String username) throws IOException{
        if(users.search(username)!= -1){
            System.out.println("El usuario ya existe");
            return;
        }
        
        raf.seek(raf.length());
        long position = raf.getFilePointer();

        raf.writeUTF(username);
        raf.writeInt(0);
        raf.writeInt(0);
        raf.writeBoolean(true);

        users.add(username, position);
        System.out.println("Usuario agregado.");
    }
       
    public void deactivateUser(String username) throws IOException {
        long pos = users.search(username);
        if (pos == -1) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        raf.seek(pos);
        raf.readUTF();
        raf.readInt();
        raf.readInt();
        raf.writeBoolean(false);

        users.remove(username);
        System.out.println("Usuario desactivado.");
    }
    
     public void addTrophieTo(String username, String game, String trophyName, EnumTrophy type, String imagePath) throws IOException {
        long pos = users.search(username);
        if (pos == -1) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        File imgFile = new File(imagePath);
        if (!imgFile.exists()) {
            System.out.println("Imagen no encontrada.");
            return;
        }

        byte[] imageBytes = Files.readAllBytes(imgFile.toPath());

        try (RandomAccessFile trophyFile = new RandomAccessFile("trophies.psn", "rw")) {
            trophyFile.seek(trophyFile.length());

            trophyFile.writeUTF(username);
            trophyFile.writeUTF(type.name());
            trophyFile.writeUTF(game);
            trophyFile.writeUTF(trophyName);
            trophyFile.writeUTF(LocalDate.now().toString());
            trophyFile.writeInt(imageBytes.length);
            trophyFile.write(imageBytes);
        }

        raf.seek(pos);
        raf.readUTF();
        long pointerToPoints = raf.getFilePointer();
        int oldPoints = raf.readInt();
        int oldTrophies = raf.readInt();

        raf.seek(pointerToPoints);
        raf.writeInt(oldPoints + type.puntos);
        raf.writeInt(oldTrophies + 1);

        System.out.println("Trofeo agregado.");
    }
     
       public void playerInfo(String username) throws IOException {
        long pos = users.search(username);
        if (pos == -1) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        raf.seek(pos);
        String name = raf.readUTF();
        int points = raf.readInt();
        int trophies = raf.readInt();
        boolean active = raf.readBoolean();

        System.out.println("Username: " + name);
        System.out.println("Puntos: " + points);
        System.out.println("Trofeos: " + trophies);
        System.out.println("Estado: " + (active ? "Activo" : "Inactivo"));
        System.out.println("Trofeos obtenidos:");

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
                    System.out.println(date + " - " + type + " - " + game + " - " + desc + " - Imagen: " + imgLen + " bytes");
                }
            }
        }
    }
}

