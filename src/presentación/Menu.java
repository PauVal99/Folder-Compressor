package src.presentación;

import java.io.File;
import java.io.Console;
import java.util.Arrays;

import src.dominio.Compressor;
import src.dominio.Decompressor;
import src.persistencia.*;

/**
 * Esta clase representa la interfaz por consola.
 * Se encarga de leer las operaciones con sus parametros y llamar a las clases con esas funcionalidades.
 * 
 * @author Pau Val
 */

public class Menu
{
    /**
     * Instancia de la clase Console, permite hacer llamadas de entrada y salida.
     * 
     * @see java.io.Console
     */
    private Console console;

    /**
     * Constructora de la clase, inicializa Console.
     */
    public Menu()
    {
        console = System.console();
    }

    /**
     * Inicia la interfaz del menu, muestra las posibles operaciones a realizar y espera hasta tener una valida.
     */
    public void start()
    {
        String[] possibleOperations = {"1", "2", "3"};
        String operation = console.readLine("Wellcome to compressor/decompressor. Please select an operation:\n 1.Compress\n 2.Uncompress\n 3.Run tests\n");
        while(!Arrays.asList(possibleOperations).contains(operation)) 
            operation = console.readLine("Please enter a valid operation: ");
        if(operation.equals("1")) compress();
        else if(operation.equals("2")) decompress();
        else if(operation.equals("3")) runTest();
    }

    /**
     * Representa la petición de comprimir un archivo. Pregunta por el archivo a comprimir, su carpeta de destino, el nombre del archivo comprimido y el algoritmo con que se desea comprimir.
     */
    private void compress()
    {
        UncompressedFile uncompressedFile = new UncompressedFile(readFile("Please enter the file path: ").getPath());
        File destinationFolder = readFolder("Please enter the destination folder: ");
        File destinationFile = readCompressDestinationFile(uncompressedFile, destinationFolder);
        String algorithm = readAlgorithm();

        Compressor compressor = new Compressor(uncompressedFile,destinationFile,algorithm);
        compressor.compress();
    }
    
    /**
     * Representa la petición de descomprimir un archivo. Pregunta por el archivo comprimido y su carpeta de destino.
     * Si el archivo indicado no fue comprimido por este programa o esta corrupto lo indicará y acabara la ejecución.
     */
    private void decompress()
    {
        try{
            CompressedFile compressedFile = new CompressedFile(readFile("Please enter the file path: ").getPath());
            File destinationFolder = readFolder("Please enter the destination folder: ");
            File destinationFile = readDecompressDestinationFile(compressedFile, destinationFolder);
            Decompressor decompressor = new Decompressor(compressedFile,destinationFile);
            decompressor.decompress();
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }

    }

    /**
     * Representa la acción de ejecutar los drivers preparados.
     * Crea una nueva instancia del menu de los tests.
     * 
     * 
     * @see tests/TestMenu.java
     */
    private void runTest()
    {

    }

    /**
     * Esta función muestra los posibles algoritmos de compression (incluyendo la opción de automatico) y espera hasta tener uno valido.
     * 
     * @return un string con el nombre del algoritmo seleccionado
     */
    private String readAlgorithm()
    {
        String[] possibleAlgorithms = {"LZ78", "LZSS", "LZW", "JPEG", ""};
        String algorithm = console.readLine("Especify the algorithm (LZ78, LZSS, LZW, JPEG) (enter for auto): ");
        while(!Arrays.asList(possibleAlgorithms).contains(algorithm)) algorithm = console.readLine("Invalid algorithm (LZ78, LZSS, LZW, JPEG) (enter for auto): ");
        if(algorithm.equals("")) algorithm = "auto";
        return algorithm;
    }

    /**
     * Esta función lee el nombre del fichero a comprimir, si existe pregunta por otro repetidamente.
     * Si no se especifica el nombre se le da el original (sin la extensión), de la misma manera si ya existe se pregunta por otro nombre. 
     * 
     * @param file fichero a comprimir
     * @param folder carpeta de destino
     * @return una instancia de la classe File que representa el fichero de destino
     * 
     * @see java.io.File
     * @see persistencia.UncompressedFile
     */
    private File readCompressDestinationFile(UncompressedFile file, File folder)
    {
        String name = console.readLine("Please enter the name of the compressed file (enter for same name): ");
        if(name.equals("")) name = file.getFileName();
        File destinationFile = new File(folder.toString() + File.separator + name);
        while(destinationFile.exists()){
            name = console.readLine("File already exists. Please enter a valid name (enter for same name): ");
            if(name.equals("")) name = file.getFileName();
            destinationFile = new File(folder.toString() + File.separator + name);
        }
        return destinationFile;
    }

    /**
     * Esta función retorna una intancia de File que representa el archivo ya descomprimido.
     * Si el nombre original del archivo a descomprimir ya existe en la carpeta de destino pregunta por un nombre nuevo.
     * 
     * @param file fichero comprimido
     * @param folder carpeta de destino
     * @return una instancia de la classe File que representa el fichero de destino
     * 
     * @see java.io.File
     * @see persistencia.CompressedFile
     */
    private File readDecompressDestinationFile(CompressedFile file, File folder)
    {
        File destinationFile = new File(folder.toString() + File.separator + file.getOriginalName());
        String newName = null;
        while(destinationFile.exists()){
            newName = console.readLine("File to decompress already exists. Please insert a new name: ");
            destinationFile = new File(folder.toString() + File.separator + newName + file.getOriginalExtension());
        }
        return destinationFile;
    }

    /**
     * Esta función pregunta por una carpeta, si ya existe pregunta por otra repetidamente.
     * 
     * @param message mensaje con el que se preguntara por la carpeta
     * @return una instancia de la clase File que representa la carpeta
     * 
     * @see java.io.File
     */
    private File readFolder(String message)
    {
        File folder = new File(console.readLine(message));
        while(!folder.exists() || !folder.isDirectory())
            folder = new File(console.readLine("Folder not found. Please insert a valid folder: "));
        return folder;
    }

    /**
     * Esta función pregunta por un ficher, si ya existe pregunta por otro repetidamente.
     * 
     * @param message mensaje con el que se preguntara por el fichero
     * @return una instancia de la clase File que representa el fichero
     * 
     * @see java.io.File
     */
    private File readFile(String message)
    {
        String path = console.readLine(message);
        File file = new File(path);
        while(!file.exists() || !file.isFile()){
            path = console.readLine("File not found. Please insert a valid file: ");
            file = new File(path);
        }
        return file;
    }
}