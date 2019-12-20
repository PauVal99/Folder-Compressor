package src.persistencia;

import src.dominio.algoritmos.*;
import src.dominio.algoritmos.Algorithm;

public class Header
{
    private String type;

    private String relativePath;

    private Algorithm algorithm;

    private int size;

    public Header(String header)
    {
        String[] camp = header.split(";");
        type = camp[0];
        relativePath = camp[1];
        if(type.equals("file")) {
            algorithm = stringToAlgorithm(camp[2]);
            size = Integer.parseInt(camp[3]);
        }
    }

    public Header(boolean isFile, String relativePath, Algorithm algorithm, int size)
    {
        if(isFile) type = "file";
        else type = "fodler";
        this.relativePath = relativePath;
        this.algorithm = algorithm;
        this.size = size;
    }

    public String getType()
    {
        return type;
    }

    public String getRelativePath()
    {
        return relativePath;
    }

    public Algorithm getAlgorithm()
    {
        return algorithm;
    }

    public int getSize()
    {
        return size;
    }

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
     * 
     * @see src.dominio.algoritmos.Algorithm
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