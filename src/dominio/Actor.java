package src.dominio;

import src.persistencia.ActorStadistics;
import src.persistencia.File;
import src.dominio.algoritmos.*;

/**
 * Esta clase representa un actor que hara funciones tanto de compresión como descompresión.
 * Contiene funciones necesarias para obtener estadísticas o escribir en los archivos de destino.
 * 
 * @author Pau Val
 */
public abstract class Actor
{
    /** Inicio de la ejecución */
    protected long startTime;

    /** Fin de la ejecución */
    protected long elapsedTime;

    protected File source;

    /**
     * Archivo de destino
     * 
     * @see src.persistencia.File
     */
    protected File destination;

    /**
     * Construye un Actor.
     * 
     * @param destinationFile archivo de destino
     * @param algorithmName nombre del algoritmo a usar
     * 
     * @see src.persistencia.File
     * @see src.dominio.Actor::setAlgortihm()
     */
    public Actor(File source, File destination)
    {
        this.source = source;
        this.destination = destination;
    }

    public abstract ActorStadistics execute();

    /**
     * Del nombre de un algoritmo se retorna una instancia de la clase del algoritmo.
     * 
     * @param algorithmName nombre del algoritmo
     * @return instancia del algoritmo
     * 
     * @see src.dominio.algoritmos.Algorithm
     */
    protected Algorithm getAlgorithm(String algorithmName)
    {
        if(algorithmName.equals("LZ78")) return new LZ78();
        else if(algorithmName.equals("LZSS")) return new LZSS();
        else if(algorithmName.equals("LZW")) return new LZW();
        else if(algorithmName.equals("JPEG")) return new JPEG();
        return new LZW();
    }
}