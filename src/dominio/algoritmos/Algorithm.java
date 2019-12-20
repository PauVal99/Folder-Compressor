package src.dominio.algoritmos;

import src.persistencia.InputBuffer;
import src.persistencia.OutputBuffer;

/**
 * Esta clase representa un algoritmo capaz de comprimir y descomprimir.
 * Es una clase abstracta.
 * 
 * @author Pau Val
 */
public abstract class Algorithm
{
    /**
     * Comprime los bytes de input.
     * 
     * @param input bytes a comprimir
     * @return bytes comprimidos
     * 
     * @see src.persistencia.InputBuffer
     * @see src.persistencia.OutputBuffer
     */
    public abstract OutputBuffer compress(InputBuffer input);

    /**
     * Descomprime los bytes de input.
     * 
     * @param input bytes a descomprimir
     * @return bytes descomprimidos
     * 
     * @see src.persistencia.InputBuffer
     * @see src.persistencia.OutputBuffer
     */
    public abstract OutputBuffer decompress(InputBuffer input);

    /**
     * Asigna la calidad del algoritmo si este lo permite.
     * Por defecto no hace nada.
     * 
     * @param quality calidad de 0 a 100
     */
    public void setQuality(int quality)
    {
        return;
    }

    /**
     * Retorna el nombre del algoritmo.
     * 
     * @return nombre del algoritmo
     */
    public abstract String getName();
}