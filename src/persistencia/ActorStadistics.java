package src.persistencia;

/**
 * Esta clase representa las estadisticas de un actor.
 * Su cometido es almacenar todas las estadísticas utiles para mostrar al usuario.
 * 
 * @author Pau Val
 */
public class ActorStadistics
{
    /** Tiempo de inicio de la ejecucción */
    private long startTime;

    /** Tiempo de fin de la ejecucción */
    private long stopTime;

    /** Tamaño del archivo original */
    private long originalSize;

    /** Tamaño del archivo comprimido */
    private long compressedSize;
    
    /** 
     * Asigna el valor startTime.
     */
    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }
    /**
     * Asigna el valor stopTime.
     */
    public void setStopTime(long stopTime)
    {
        this.stopTime = stopTime;
    }

    /**
     * Asigna el valor originalSize.
     */
    public void setOriginalSize(long originalSize)
    {
        this.originalSize = originalSize;
    }

    /**
     * Asigna el valor compressedSize.
    */
    public void setCompressedSize(long compressedSize)
    {
        this.compressedSize = compressedSize;
    }

    /**
     * Retorna la velocidad de compressión en Megabytes por segundo.
     * 
     * @return velocidad de la compressión
     */
    public float getVelocity()
    {
        return ((float)originalSize/(float)1048576)/((float)getElapsedTime()/(float)1000);
    }

    /**
     * Retorna el ratio de compressión (0 a 1).
     * 
     * @return ratio de compressión
     */
    public float getCompressRatio()
    {
        return (float)compressedSize/(float)originalSize;
    }

    /**
     * Retorna el tiempo transcurrio en milisegundos.
     * 
     * @return tiempo transcurrio en milisegundos
     */
    public long getElapsedTime()
    {
        return stopTime - startTime;
    }
}