package src.dominio;

import src.persistencia.File;
import src.dominio.algoritmos.*;

import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Esta clase representa la compresión de un Único archivo.
 * Según su extensión decide si usar un algoritmo de texto o JPEG.
 * 
 * @author Pau Val
 */
public class FileCompressor
{
    /** Algoritmo usado en caso de texto. */
    private Algorithm algorithm;

    /** Archivo a comprimir */
    private File source;

    /**
     * Construye un FileCompressor.
     * 
     * @param source archivo a comprimir
     * @param algorithm instancia del algoritmo a usar
     * 
     * @see src.persistencia.File
     * @see src.dominio.algoritmos.Algorithm
     */
    public FileCompressor(File source, Algorithm algorithm)
    {
        this.source = source;
        this.algorithm = algorithm;
    }

    /**
     * Realiza la acción de comprimir un fichero con los parametros de la constructora.
     * Se encaraga de recojer las estadísticas, escribir la cabecera del archivo comprimido y escribir el resultado.
     */
    public ByteArrayOutputStream compress()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            FileInputStream fis = new FileInputStream(source);
            byte[] read = new byte[(int) source.length()];
            fis.read(read);
            ByteArrayInputStream bais = new ByteArrayInputStream(read);

            if(source.getExtension().equals("ppm")) algorithm = new JPEG();
            baos = algorithm.compress(bais);
            
            fis.close();
        }
        catch(Exception e){
            System.out.print("Error reading file:" + source.getPath());
        }
        return baos;
    }
}