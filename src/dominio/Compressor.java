package src.dominio;

import java.io.File;

import src.dominio.Actor;
import src.persistencia.UncompressedFile;;

public class Compressor extends Actor
{
    private UncompressedFile uncompressedFile;
    private long originalSize;

    public Compressor(UncompressedFile uncompressedFile, File destinationFile, String algorithmName)
    {
        super(destinationFile,algorithmName);
        this.uncompressedFile = uncompressedFile;
    }

    public void compress()
    {
        initCompressStadistics();
        createDestinationFile();
        writeInDestiantionFile(getHeader().getBytes());
        byte[] b = algorithm.compress(uncompressedFile);
        writeInDestiantionFile(b);
        printCompressStadistics(b.length);
    }

    private String getHeader()
    {
        String header = "name:"      + uncompressedFile.getName() + "\n" +
                        "algorithm:" + algorithm.getName()        + "\n" ;
        return header;
    }

    private void initCompressStadistics()
    {
        initStadistics();
        originalSize = uncompressedFile.length();
    }

    private void printCompressStadistics(long compressedSize)
    {
        printStadistics();
        System.out.print("Original size was "+originalSize+" bytes.\n");
        System.out.print("Compressed size is "+compressedSize+" bytes.\n");
        System.out.print("Compress ratio is "+((float)compressedSize/(float)originalSize)+" bytes.\n");
    }
}