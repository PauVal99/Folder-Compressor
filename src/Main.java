package src;

import src.presentacion.GraphicMenu;

/**
 * Esta clase representa el inicio de ejecución de nuestro programa.
 * 
 * @author Sebastian Acurio Navas
 */
public class Main {
    
    /**
     * Función que llama por defecto Java al iniciar la ejecución.
     * 
     * @param args representa los parametros de entrada. No se espera ninguno.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraphicMenu().setVisible(true);
            }
        });
    }
}
