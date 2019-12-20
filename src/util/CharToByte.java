package src.util;

/**
 * Esta clase contiene funciones auxiliares para convertir caracteres en un byte y a la inversa.
 * 
 * @author Pau Val
 */
public class CharToByte
{
    /**
     * Convierte un caracter en un byte (solo guarda la parte baja).
     * 
     * @param c caracter a convertir
     * @return byte con que se representa c
     */
    public static byte charToByte(char c)
    {
        return (byte) (c & 0xFF);
    }

    /**
     * Convierte un byte en un caracter sin extension de signo.
     * 
     * @param b byte a convertir
     * @return caracter que representa b
     */
    public static char byteToChar(byte b)
    {
        return (char) (b & 0xFF);
    }
}