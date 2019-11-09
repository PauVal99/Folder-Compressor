package src.dominio.algoritmos;

import src.persistencia.File;

public class LZ78 extends Algorithm
{
    public byte[] compress(File uncompressed)
    {
        byte[] b = new byte[1];
        return b;
    }
    public byte[] decompress(File compressedBytes)
    {
        return new byte[0];
    }

    public String getName()
    {
        return "LZ78";
    }
}