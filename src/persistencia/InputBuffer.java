package src.persistencia;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class InputBuffer extends ByteArrayInputStream
{
    public InputBuffer(byte[] buf)
    {
        super(buf);
    }

    public byte[] readAll() throws IOException
    {
        byte[] bytes = new byte[available()];
        read(bytes);
        return bytes;
    }
}