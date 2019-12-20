package src.persistencia;

import src.dominio.algoritmos.*;
import src.dominio.algoritmos.Algorithm;

/**
 * Esta clase representa la cabezera que usamos para descomprir un archivo.
 * 
 * @author Pau Val
 */
public class Header
{
    /** Tipo de archivo */
    private String type;

    /** Ruta relatica a la raíz */
    private String relativePath;

    /** Algoritmo usado en la compressión */
    private Algorithm algorithm;

    /** Tamaño del archivo comprimido */
    private long size;

    /**
     * Construye un header a partir de un string.
     * 
     * @param header header en formato string
     */
    public Header(String header)
    {
        String[] camp = header.split(";");
        type = camp[0];
        relativePath = camp[1];
        if(type.equals("file")) {
            algorithm = stringToAlgorithm(camp[2]);
            size = Long.parseLong(camp[3]);
        }
    }

    /**
     * Contruye un header a partir de su información minima.
     * 
     * @param isFile true si el archivo es un fichero, falso en cualquier otro caso
     * @param relativePath ruta relativa a la raíz
     * @param algorithm algoritmo usado en la compressión
     * @param size tamaño del archivo comprimido
     */
    public Header(boolean isFile, String relativePath, Algorithm algorithm, int size)
    {
        if(isFile) type = "file";
        else type = "fodler";
        this.relativePath = relativePath;
        this.algorithm = algorithm;
        this.size = size;
    }

    /**
     * Retorna el tipo de archivo
     * 
     * @return "file" si es un ficher, "folder" si es una carpeta
     */
    public String getType()
    {
        return type;
    }

    /**
     * Retorna el path relativo a la raíz
     * 
     * @return ruta relativa a la raíz
     */
    public String getRelativePath()
    {
        return relativePath;
    }

    /**
     * Retorna el algoritmo usado en la compressión
     * 
     * @return algorimo usado en la compessión
     */
    public Algorithm getAlgorithm()
    {
        return algorithm;
    }


    /**
     * Retorna el tamaño del archivo comprimido
     * 
     * @return tamaño del archivo comprimido
     */
    public long getSize()
    {
        return size;
    }

    /**
     * Convierte el header en un string
     * 
     * @return el header en formato string
     */
    public String toString()
    {
        if(type.equals("file")) return "file"  +";"+relativePath+";"+algorithm.getName()+";"+size+"\n";
        else                    return "folder"+";"+relativePath+"\n";
    }

    /**
     * Del nombre de un algoritmo se retorna una instancia de la clase del algoritmo.
     * 
     * @param algorithmName nombre del algoritmo
     * @return instancia del algoritmo
     */
    private Algorithm stringToAlgorithm(String algorithmName)
    {
        if(algorithmName.equals("LZ78")) return new LZ78();
        else if(algorithmName.equals("LZSS")) return new LZSS();
        else if(algorithmName.equals("LZW")) return new LZW();
        else if(algorithmName.equals("JPEG")) return new JPEG();
        return new LZW();
    }
}