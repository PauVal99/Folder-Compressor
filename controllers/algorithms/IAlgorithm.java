package controllers.algorithms;

interface IAlgorithm
{
    public Byte[] compress(Byte[] uncompressed);

    public Byte[] decompress(Byte[] compressed);
}