package src.dominio;

/**
 * Esta clase contiene funciones auxiliares para convertir enteros en byte arrays y a la inversa.
 * 
 * @author Pol Aguilar
 */
public class IntegerToByteHelper 
{
    /**
     * Convierte un entero en n bytes.
     * 
     * @param integer entero a convertir
     * @param nBytes numero de bytes con los que se representará el entero
     * @return array de bytes que se representa integer
     */
    public static byte[] intToByteArray(int integer, int nBytes)
    {
        byte[] buffer = new byte[nBytes];
        for(int i=0; i<nBytes; i++) buffer[i] = (byte) (integer >>> (i * 8));
        return buffer;
    }

    /**
     * Convierte un array de bytes en un entero.
     * 
     * @param buffer array de bytes a convertir
     * @return entero
     */
    public static int byteArrayToInt(byte[] buffer)
    {
        int integer = 0;
        for (int i = 0; i < buffer.length; i++) {
            int a = ((buffer[i] & 0xff)) * (int) Math.pow(2,i * 8);
            integer = integer + a;
        }
        return integer;
    }
}