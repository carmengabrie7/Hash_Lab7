/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hash_lab7;

/**
 *
 * @author eduar
 */

public enum EnumTrophy {
    PLATINO(5),ORO(3),PLATA(2),BRONCE(1);
    
    public int puntos;
    
    private EnumTrophy(int points){
        this.puntos = puntos;
    }
    
    public int getPuntos(){
        return puntos;
    }
}
