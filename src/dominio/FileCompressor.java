package src.dominio;

import src.persistencia.File;
import src.dominio.algoritmos.*;

import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Esta clase representa la compresión de un único archivo.
 * Según su extensión decide si usar un algoritmo de texto o JPEG.
 * 
 * @author Pau Val
 */
public class FileCompressor
{
    /** Archivo a comprimir */
    private File source;

    /** Algoritmo usado en caso de texto. */
    private Algorithm algorithm;

    /** Path relativo que deve escribirse un el header */
    private String relativePath;

    /** Bytes que ocupa el archivo comprimido */
    private long compressedLength;

    private int quality;

    /**
     * Construye el compresor de un fichero
     * 
     * @param source fichero a comprimir
     * @param algorithm algoritmo que se usara en caso de texto
     * @param relativePath parametro necesario del header
     */
    public FileCompressor(File source, Algorithm algorithm, String relativePath, int quality)
    {
        this.source = source;
        this.algorithm = algorithm;
        this.relativePath = relativePath;
        this.quality = quality;
    }

    /**
     * Realiza la acción de comprimir un fichero.
     * 
     * @return el fichero comprimido con su header
     * @throws Exception En caso de error en la entrada o salida
     * 
     * @see java.io.ByteArrayOutputStream
     */
    public ByteArrayOutputStream compress() throws Exception
    {
        ByteArrayOutputStream compressedFile = new ByteArrayOutputStream();

        if(source.isFile()){
            ByteArrayInputStream fileBytes = readSource();
            ByteArrayOutputStream compressedFileBytes = compressFile(fileBytes);
            compressedLength = compressedFileBytes.toByteArray().length;

            compressedFile.write(getHeader().getBytes());
            compressedFile.write(compressedFileBytes.toByteArray());
        } else 
            compressedFile.write(getHeader().getBytes());

        return compressedFile;
    }

    /**
     * Retorna el header del fichero a comprimir.
     * Este le indica al descompresor información necesaria.
     * 
     * @return header de source
     */
    private String getHeader()
    {
        String header = "";
        if(source.isFile()) header = "file"  +";"+relativePath+";"+algorithm.getName()+";"+compressedLength+"\n";
        else                header = "folder"+";"+relativePath+"\n";
        return header;
    }

    /**
     * Lee y guarda en un buffer el fichero source.
     * 
     * @return bytes de source
     * @throws Exception En caso de error en la lectura del fichero
     * 
     * @see java.io.ByteArrayInputStream
     */
    private ByteArrayInputStream readSource() throws Exception
    {
        byte[] read = new byte[(int) source.length()];
        FileInputStream sourceReader = new FileInputStream(source);
        sourceReader.read(read);
        sourceReader.close();
        return new ByteArrayInputStream(read);
    }

    /**
     * Llama al algoritmo de texto o si source es una foto a JPEG para la compresión fileBytes
     * 
     * @param fileBytes bytes a comprimir
     * @return bytes comprimidos
     * 
     * @see java.io.ByteArrayOutputStream
     */
    private ByteArrayOutputStream compressFile(ByteArrayInputStream fileBytes)
    {
        if(source.getExtension().equals("ppm")) algorithm = new JPEG();
        algorithm.setQuality(quality);
        return algorithm.compress(fileBytes);
    }
}