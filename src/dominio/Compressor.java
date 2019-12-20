package src.dominio;

import src.persistencia.ActorStadistics;
import src.persistencia.File;
import src.dominio.FileCompressor;
import src.dominio.Actor;

import java.io.FileOutputStream;

/**
 * Esta clase representa un actor de compresión.
 * Su cometido es llamar al algoritmo requerido y escribir el resultado en el fichero de destino.
 * 
 * @author Pau Val
 */
public class Compressor extends Actor
{
    private String algorithmName;

    private FileOutputStream destinationWritter;

    private int quality;

    /**
     * Construye un Compressor.
     * 
     * @param source archivo o carpeta a comprimir
     * @param destinationFolder carpeta de destino
     * @param algorithmName nombre del algoritmo a usar
     * 
     * @see src.persistencia.File
     */
    public Compressor(File source, File destinationFolder, String algorithmName, int quality)
    {
        super(source, getDestinationFile(destinationFolder, source.getFileName()));
        try{
            this.destinationWritter = new FileOutputStream(this.destination);
            this.algorithmName = algorithmName;
            this.quality = quality;
        }
        catch(Exception e){
            System.out.println("Error opening stream in file " + this.destination.getPath());
        }
    }

    /**
     * Retorna el archivo destino de esta compresión. Se construye con la carpeta de destino mas el nombre del source y la extensión .cmprss.
     * 
     * @param destinationFolder carpeta de destino
     * @param fileName nombre del archivo a generar
     * @return ruta del destino de la compresión
     */
    private static File getDestinationFile(File destinationFolder, String fileName)
    {
        return new File(destinationFolder.getPath() + File.separator + fileName + ".cmprss");
    }

    /**
     * Realiza la acción de comprimir un fichero o carpeta con los parametros de la constructora.
     * Se encaraga de recojer las estadísticas.
     */
    public ActorStadistics execute()
    {
        ActorStadistics stadistics = new ActorStadistics();
        stadistics.setOriginalSize(source.getSize());
        stadistics.setStartTime(System.currentTimeMillis());
        try{
            recursiveCompression(source);
            destinationWritter.close();
        }
        catch(Exception e){
            System.out.println("Error compressing: " + source.getPath());
        }
        stadistics.setStopTime(System.currentTimeMillis());
        stadistics.setCompressedSize(destination.getSize());
        return stadistics;
    }

    /**
     * Metodo recursivo que comprime una estructura de carpetas.
     * 
     * @param file Archivo o carpeta a comprimir
     * @throws Exception En caso de error en la lectura o escritura de un fichero o carpeta
     */
    private void recursiveCompression(File file) throws Exception
    {
        FileCompressor fileCompressor = new FileCompressor(file, algorithmName, getRelativePath(file), quality);
        destinationWritter.write(fileCompressor.compress().toByteArray());
        if(file.isDirectory()){
            File[] folderList = file.listFiles();
            for(File f: folderList){
                recursiveCompression(f);
            }
        }
    }

    /**
     * Retorna la ruta relativa del fichero parametro i el source de la compresión.
     * 
     * @param file archivo del cual se quiere saber su ruta relativa
     * @return ruta relativa al source
     */
    private String getRelativePath(File file)
    {
        return source.getName() + file.getPath().replace(source.getPath(), "");
    }
}