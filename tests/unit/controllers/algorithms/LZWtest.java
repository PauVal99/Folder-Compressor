package tests.unit.controllers.algorithms;

import controllers.algorithms.LZW;
import tests.unit.Tester;

public class LZWtest extends Tester 
{
    public void testCompressVoidFile()
    {
        String voidFile = "";
        LZW lzw = new LZW();
        String compressedFile = lzw.compress(voidFile);
        this.assertEqualStirng(compressedFile,"");
    }

    public void testCompressFile()
    {
        String file = "visca la democracia";
        LZW lzw = new LZW();
        String compressedFile = lzw.compress(file);
        this.assertEqualStirng(compressedFile,"");
    }

    public void testDecompressVoidFile()
    {
        String voidFile = "";
        LZW lzw = new LZW();
        String decompressedFile = lzw.decompress(voidFile);
        this.assertEqualStirng(decompressedFile,"");
    }

    public void testDecompressFile()
    {
        String file = "11810511599973210813210010110911199114979910597";
        LZW lzw = new LZW();
        String decompressedFile = lzw.decompress(file);
        this.assertEqualStirng(decompressedFile,"visca la democracia");
    }
}