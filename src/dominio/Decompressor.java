package src.dominio;

import java.io.File;

import src.persistencia.*;
import src.dominio.Actor;

public class Decompressor extends Actor
{
    private CompressedFile compressedFile;

    public Decompressor(CompressedFile compressedFile, File destinationFolder)
    {
        super(new File(destinationFolder.toString() + File.separator + compressedFile.getOriginalName()), compressedFile.getAlgorithm());
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