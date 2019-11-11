package src.dominio;

import java.io.File;

import src.persistencia.*;
import src.dominio.Actor;

public class Decompressor extends Actor
{
    private CompressedFile compressedFile;

    public Decompressor(CompressedFile compressedFile, File destinationFile)
    {
        super(destinationFile, compressedFile.getAlgorithm());
        this.compressedFile = compressedFile;
    }

    public void decompress()
    {
        initStadistics();
        createDestinationFile();
        byte[] b = algorithm.decompress(compressedFile);
        writeInDestiantionFile(b);
        printStadistics();
    }
}