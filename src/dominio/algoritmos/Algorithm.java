package src.dominio.algoritmos;

import src.persistencia.File;

public abstract class Algorithm
{
    public abstract byte[] compress(File uncompressed);
    public abstract byte[] decompress(File compressedBytes);
    public abstract String getName();
}