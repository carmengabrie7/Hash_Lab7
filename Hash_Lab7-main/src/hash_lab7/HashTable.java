/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7_hash;

import hash_lab7.Entry;

/**
 *
 * @author CarlosXl
 */
public class HashTable {
    
    private Entry head; // Inicio de la lista

    public HashTable() {
        this.head = null;
    }

    /**
     * Agrega un nuevo elemento al final de la lista.
     * @param username Nombre del usuario
     * @param pos Posición en el archivo .psn
     */
    public void add(String username, long pos,Entry next) {
        Entry newEntry = new Entry(username, pos , next);
        
        if (head == null) {
            // Si la lista está vacía, el nuevo es el primero
            head = newEntry;
        } else {
            // Si no, recorremos hasta el final
            Entry current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newEntry;
        }
    }

    /**
     * Remueve un elemento de la lista que concuerde con dicho username.
     * @param username Nombre a eliminar
     */
    public void remove(String username) {
        if (head == null) return;

        // Caso especial: El nodo a borrar es la cabeza (head)
        if (head.username.equals(username)) {
            head = head.next;
            return;
        }

        // Caso normal: Buscar en el resto de la lista
        Entry current = head;
        while (current.next != null) {
            if (current.next.username.equals(username)) {
                // Saltamos el nodo encontrado para desconectarlo
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    /**
     * Recorre la lista buscando el username.
     * @param username Nombre a buscar
     * @return La posición (long) en el archivo si existe, o -1 si no existe.
     */
    public long search(String username) {
        Entry current = head;
        while (current != null) {
            if (current.username.equals(username)) {
                return current.position;
            }
            current = current.next;
        }
        return -1;
    }
}
