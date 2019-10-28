package controllers.algorithms;

public class LZSS extends Algorithm
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

    public String getName()
    {
        return "LZSS";
    }
}