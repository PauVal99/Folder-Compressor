package tests;

import java.io.File;
import java.util.Arrays;
import java.lang.Exception;

import src.dominio.*;
import src.persistencia.*;

public class Tester {

    public void test(String file, String alg_name) 
    {
        System.out.print("\tTest with algorithm:"+alg_name+", File:"+file+".txt\n");
        String originalFile = "data/"+file+".txt" ;
        String compressedFile = "data/compressed/"+alg_name+"/"+file;
        String uncompressedFile = "data/compressed/"+alg_name+"/"+file+".txt";

        compression(originalFile, compressedFile, alg_name);
        decompression(compressedFile, uncompressedFile);

        UncompressedFile original = new UncompressedFile(originalFile);
        UncompressedFile result = new UncompressedFile(uncompressedFile);
        comprovation(original, result, file);
    }

    private void compression(String fileOriginal, String fileCompressed, String algorithm)
    {
        System.out.print("Compression:\n");
        UncompressedFile uncompressedFileToTest = new UncompressedFile(fileOriginal);
        File uncompressedDestinationFile = new File(fileCompressed);
        Compressor compressor = new Compressor(uncompressedFileToTest,uncompressedDestinationFile, algorithm);
        compressor.compress();
    }

    private void decompression(String fileCompressed, String fileUncompressed)
    {
        System.out.print("\nDecompression:\n");
        CompressedFile compressedFileToTest = null;
        try {
            compressedFileToTest = new CompressedFile(fileCompressed);
        }
        catch (Exception e) {}
        File compressedDestinationFile = new File(fileUncompressed);
        Decompressor decompressor = new Decompressor(compressedFileToTest, compressedDestinationFile);
        decompressor.decompress();
        compressedFileToTest.delete();
    }

    private void comprovation(UncompressedFile original, UncompressedFile result, String file)
    {
        System.out.print("\nResult:\n");
        if (!Arrays.equals(original.readAll(),result.readAll())) System.out.print(file+".txt test failed.\n\n");
        else System.out.print("Test Passed! Algorithm is working good!\n\n");
        result.delete();
    }

}