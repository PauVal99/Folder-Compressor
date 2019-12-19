package src.persistencia;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Esta clase representa un buffer de lectura.
 * Su cometido es poder realizar consultas a un buffer de bytes.
 * Extiende la clase java.io.ByteArrayInputStream.
 * 
 * @see java.io.ByteArrayInputStream
 * @author Pau Val
 */
public class InputBuffer extends ByteArrayInputStream
{
    /**
     * Constructora por defecto.
     */
    public InputBuffer(byte[] buf)
    {
        super(buf);
    }

    /**
     * Retorna todos los bytes que quedan en el buffer.
     * 
     * @return todos los bytes que quedan en el buffer
     * @throws IOException en el caso de error
     */
    public byte[] readAll() throws IOException
    {
        byte[] bytes = new byte[available()];
        read(bytes);
        return bytes;
    }
}