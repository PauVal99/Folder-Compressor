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
    /**
     * Archivo origen.
     * 
     * @see src.persistencia.File
     */
    protected File source;

    /**
     * Archivo de destino.
     * 
     * @see src.persistencia.File
     */
    protected File destination;

    /**
     * Construye un Actor.
     * 
     * @param source archivo origen
     * @param destination archivo destino
     * 
     * @see src.persistencia.File
     */
    public Actor(File source, File destination)
    {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Realiza la acción de la classe.
     * 
     * @return clase con las estadísticas de la ejecucción
     * 
     * @see src.persistencia.ActorStadistics
     */
    public abstract ActorStadistics execute();
}