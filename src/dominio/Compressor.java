package src.dominio;

import src.persistencia.File;
import src.dominio.FileCompressor;
import src.dominio.Actor;
import src.dominio.algoritmos.Algorithm;
import src.presentacion.GraphicMenu;

import java.io.FileOutputStream;

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
     * @param source archivo o carpeta a comprimir
     * @param destinationFolder carpeta de destino
     * @param algorithmName nombre del algoritmo a usar
     * 
     * @see src.persistencia.File
     */
    public Compressor(File source, File destinationFolder, String algorithmName)
    {
        super(source, getDestinationFile(destinationFolder, source.getFileName()));
        try{
            this.destinationWritter = new FileOutputStream(this.destination);
            this.algorithm = getAlgorithm(algorithmName);
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
    public void compress()
    {
        try{
            initStadistics();
            recursiveCompression(source);
            setStadistics();
            destinationWritter.close();
        }
        catch(Exception e){
            System.out.println("Error compressing: " + source.getPath());
        }
    }

    /**
     * Metodo recursivo que comprime una estructura de carpetas.
     * 
     * @param file Archivo o carpeta a comprimir
     * @throws Exception En caso de error en la lectura o escritura de un fichero o carpeta
     */
    private void recursiveCompression(File file) throws Exception
    {
        FileCompressor fileCompressor = new FileCompressor(file, algorithm, getRelativePath(file));
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

    /**
     * Guarda el tiempo transcurrido, el tampaño de source, el tamaño de destination y el ratio de compresión.
     * Redefine el metodo printStadistics de Actor.
     * 
     * @see src.dominio.Actor.printStadistics()
     */
    protected void setStadistics()
    {
        super.setStadistics();
        String ogsize = "Original size was "+source.getSize()+" bytes.\n";
        String cmpsize = "Compressed size is "+destination.getSize()+" bytes.\n";
        String cmpratio = "Compress ratio is "+((float)destination.getSize()/(float)source.getSize())+" bytes.\n";
        System.out.print(ogsize);
        System.out.print(cmpsize);
        System.out.print(cmpratio);

        GraphicMenu.printCompressStadistics(getTimeExec(), ogsize, cmpsize, cmpratio);
    }
}