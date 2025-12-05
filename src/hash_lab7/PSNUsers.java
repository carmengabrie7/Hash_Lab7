
package hash_lab7;

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
    
    public HashTable getUsers(){
    return users;
}

    public RandomAccessFile getRaf() {
    return raf;
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
        throw new IOException("Usuario no encontrado.");
    }

    raf.seek(pos);
    raf.readUTF();
    int oldPoints = raf.readInt();
    int oldTrophies = raf.readInt();
    boolean active = raf.readBoolean();

    if (!active) {
        throw new IOException("Usuario no activo, no se puede agregar trofeo.");
    }

    File imgFile = new File(imagePath);
    if (!imgFile.exists()) {
        throw new IOException("Imagen no encontrada en la ruta especificada.");
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

    long pointerToPoints = raf.getFilePointer() - 1 * 4 - 1 * 4;  // reposicionar
    raf.seek(pos);
    raf.readUTF();
    long pointer = raf.getFilePointer();
    raf.seek(pointer);
    raf.writeInt(oldPoints + type.puntos);
    raf.writeInt(oldTrophies + 1);
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

