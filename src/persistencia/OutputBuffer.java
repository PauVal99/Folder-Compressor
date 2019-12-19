package src.persistencia;

import java.io.ByteArrayOutputStream;

/**
 * Esta clase representa un buffer de escritura.
 * Su cometido es poder realizar escrituras en un buffer de bytes.
 * Extiende la clase java.io.ByteArrayOutputStream.
 * 
 * @see java.io.ByteArrayOutputStream
 * @author Pau Val
 */
public class OutputBuffer extends ByteArrayOutputStream
{
    /**
     * Constructora por defecto.
     */
    public OutputBuffer()
    {
        super();
    }
}