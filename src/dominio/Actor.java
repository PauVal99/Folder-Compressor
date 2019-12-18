package src.dominio;

import src.persistencia.File;
import src.presentacion.GraphicMenu;
import src.dominio.algoritmos.*;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private String timeExecution;

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

    /**
     * Guarda el inicio de una ejecución
     */
    protected void initStadistics()
    {
        startTime = System.currentTimeMillis();
    }

    /**
     * Guarda el tiempo del fin de la ejecución y escribe por pantalla en un formato legible el tiempo de ejecución.
     * 
     * @see java.text.SimpleDateFormat
     * @see java.util.Date
     */
    protected void setStadistics()
    {
        elapsedTime = System.currentTimeMillis() - startTime;
        timeExecution = "Done in " + (new SimpleDateFormat("mm 'minute(s)' ss 'second(s)' SSS 'milliseconds'")).format(new Date(elapsedTime)) + ".\n";
        System.out.print(timeExecution);
        GraphicMenu.printDecompressStadistics(getTimeExec());
    }

    protected String getTimeExec()
    {
        return timeExecution;
    }

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