package src.dominio.algoritmos;

import src.persistencia.*;

public abstract class Algorithm
{
    public abstract byte[] compress(UncompressedFile uncompressed);
    public abstract byte[] decompress(CompressedFile compressedBytes);
    public abstract String getName();
}