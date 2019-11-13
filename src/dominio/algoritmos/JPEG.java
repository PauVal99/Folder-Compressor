package src.dominio.algoritmos;

import src.persistencia.*;

public class JPEG extends Algorithm
{
    public byte[] compress(UncompressedFile uncompressed)
    {
        byte[] b = new byte[1];
        return b;
    }
    public byte[] decompress(CompressedFile compressedBytes)
    {
        return new byte[0];
    }

    public String getName()
    {
        return "JPEG";
    }
}