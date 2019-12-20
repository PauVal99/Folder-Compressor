package src.dominio;

import src.persistencia.ActorStadistics;
import src.persistencia.File;

/**
 * Esta clase representa un actor que hara funciones tanto de compresión como descompresión.
 * Contiene funciones necesarias para obtener estadísticas o escribir en los archivos de destino.
 * 
 * @author Pau Val
 */
public abstract class Actor
{
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
}