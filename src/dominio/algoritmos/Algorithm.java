package src.dominio.algoritmos;

public abstract class Algorithm
{
    public abstract byte[] compress(String uncompressed);
    public abstract String decompress(byte[] compressedBytes);
    public abstract String getName();
}