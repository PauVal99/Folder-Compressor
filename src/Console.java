package src;

import src.presentacion.ConsoleMenu;

/**
 * Esta clase representa el inicio de ejecución de nuestro programa en el caso de usar la consola.
 * 
 * @author Pau Val
 */
public class Console {
    
    /**
     * Función que llama el makefile en caso de usar la consola.
     * 
     * @param args representa los parametros de entrada. No se espera ninguno.
     */
    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        menu.start();
    }
}
