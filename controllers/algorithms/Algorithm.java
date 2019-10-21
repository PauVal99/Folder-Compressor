package controllers.algorithms;

public abstract class Algorithm
{
    public byte[] compress(String uncompressed)
    {
        byte[] b = new byte[1];
        return b;
    }
    public String decompress(byte[] compressedBytes)
    {
        return "";
    }
}