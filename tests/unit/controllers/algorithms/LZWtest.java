package tests.unit.controllers.algorithms;

import controllers.algorithms.LZW;
import tests.unit.Tester;
import java.util.*;

public class LZWtest extends Tester 
{
    public void testFirst()
    {
        LZW lzw = new LZW();
        List<Character> compressed = lzw.compress("TOBEORNOTTOBEORTOBEORNOT");
        System.out.println(compressed);
        String decompressed = lzw.decompress(compressed);
        System.out.println(decompressed);
    }

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
        String file = "";
        LZW lzw = new LZW();
        String decompressedFile = lzw.decompress(file);
        this.assertEqualStirng(decompressedFile,"visca la democracia");
    }
}