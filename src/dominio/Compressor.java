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
            initStadistics();
            recursiveCompression(source);
            printStadistics();
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
     * Crea la cabezera de una carpeta comprimida con su tipo y path relativo.
     * 
     * @return string acabado en un salto de línea
     */
    private String getFolderHeader(File file)
    {
        String header = "folder"              + ";" +
                        getRelativePath(file) + "\n" ;
        return header;
    }

    /**
     * Crea la cabezera de un fichero comprimido con su tipo, path relativo, nombre con extensión y tamaño comprimido.
     * 
     * @return string acabado en un salto de línea
     */
    private String getFileHeader(File file, int compressedLength)
    {
        String header = "file"                + ";" +
                        getRelativePath(file) + ";" +
                        algorithm.getName()   + ";" +
                        compressedLength      + "\n" ;
        return header;
    }

    /**
     * Retorna el path relativo a la carpeta raíz.
     * 
     * @param file archivo del cual se quiere saber su ruta relativa
     * @return path relativo a la carpeta raíz
     */
    private String getRelativePath(File file)
    {
        return source.getName() + file.getPath().replace(source.getPath(), "");
    }

    /**
     * Imprime por pantalla el tiempo transcurrido, el tampaño de source, el tamaño de destination y el ratio de compresión.
     * Redefine el metodo printStadistics de Actor.
     * 
     * @see src.dominio.Actor.printStadistics()
     */
    protected void printStadistics()
    {
        super.printStadistics();
        System.out.print("Original size was "+source.getSize()+" bytes.\n");
        System.out.print("Compressed size is "+destination.getSize()+" bytes.\n");
        System.out.print("Compress ratio is "+((float)destination.getSize()/(float)source.getSize())+" bytes.\n");
    }
}