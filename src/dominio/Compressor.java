package src.dominio;

import src.persistencia.File;
import src.dominio.Actor;
import src.dominio.algoritmos.Algorithm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Esta clase representa un actor de compresión.
 * Su cometido es llamar al algoritmo requerido y escribir el resultado en el fichero de destino.
 * 
 * @author Pau Val
 */

public class Compressor extends Actor
{
    /** Tamaño original del archivo a comprimir */
    private long originalSize;

    private Algorithm algorithm;

    private FileOutputStream destinationWritter;

    /**
     * Construye un Compressor.
     * 
     * @param uncompressed archivo o carpeta a comprimir
     * @param destinationFile archivo de destino
     * @param algorithmName nombre del algoritmo a usar
     * 
     * @see src.persistencia.File
     * @see src.persistencia.UncompressedFile
     */
    public Compressor(File source, File destinationFolder, String algorithmName)
    {
        super(source, getDestinationFile(destinationFolder, source.getFileName()));
        try{
            this.destinationWritter = new FileOutputStream(this.destination);
        }
        catch(Exception e){
            System.out.println("Error in file " + this.destination.getPath());
        }
        this.algorithm = getAlgorithm(algorithmName);
    }

    private static File getDestinationFile(File destinationFolder, String name)
    {
        return new File(destinationFolder.getPath() + File.separator + name + ".cmprss");
    }

    /**
     * Realiza la acción de comprimir un fichero con los parametros de la constructora.
     * Se encaraga de recojer las estadísticas, escribir la cabecera del archivo comprimido y escribir el resultado.
     */
    public void compress()
    {
        try{
            initCompressStadistics();
            recursiveCompression(source);
            printCompressStadistics();
            destinationWritter.close();
        }
        catch(Exception e){
            System.out.println("Error closing fos.");
        }
    }

    private void recursiveCompression(File file) throws Exception
    {
        if(file.isFile()){
            ByteArrayOutputStream compressedBytes = compressFile(file);
            destinationWritter.write(getFileHeader(file, compressedBytes.toByteArray().length).getBytes());
            destinationWritter.write(compressedBytes.toByteArray());
        } else {
            destinationWritter.write(getFolderHeader(file).getBytes());
            File[] folderList = file.listFiles();
            for(File f: folderList){
                recursiveCompression(f);
            }
        }
    }

    private ByteArrayOutputStream compressFile(File file){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            FileInputStream fis = new FileInputStream(file);
            byte[] read = new byte[(int) file.length()];
            fis.read(read);
            ByteArrayInputStream bais = new ByteArrayInputStream(read);
            baos = algorithm.compress(bais);
            fis.close();
        }
        catch(Exception e){
            System.out.print("Error reading file:" + file.getPath());
        }
        return baos;
    }

    /**
     * Crea una cabezera con el nombre del archivo original y el nombre del algoritmo usado para la compresión.
     * @return string "atributo:valor\n"
     */
    private String getFolderHeader(File file)
    {
        String header = "folder"                 + ";" +
                        getRelativePath(file)    + "\n" ;
        return header;
    }

    /**
     * Crea una cabezera con el nombre del archivo original y el nombre del algoritmo usado para la compresión.
     * @return string "atributo:valor\n"
     */
    private String getFileHeader(File file, int compressedLength)
    {
        String header = "file"                + ";" +
                        getRelativePath(file) + ";" +
                        algorithm.getName()   + ";" +
                        compressedLength      + "\n" ;
        return header;
    }

    private String getRelativePath(File file)
    {
        return source.getName() + file.getPath().replace(source.getPath(), "");
    }

    /**
     * Recoje el momento en que se inicia la compresión y el tamaño del archivo a comprimir.
     * 
     * @see src.dominio.Actor::initStadistics()
     */
    private void initCompressStadistics()
    {
        initStadistics();
        originalSize = source.getSize();
    }

    /**
     * Recoje el momento en que se acaba la compresión y el tamaño del archivo resultante.
     * 
     * @param long tamaño del archivo comprimido
     * 
     * @see src.dominio.Actor::printStadistics()
     */
    private void printCompressStadistics()
    {
        printStadistics();
        System.out.print("Original size was "+originalSize+" bytes.\n");
        System.out.print("Compressed size is "+destination.length()+" bytes.\n");
        System.out.print("Compress ratio is "+((float)destination.getSize()/(float)originalSize)+" bytes.\n");
    }
}