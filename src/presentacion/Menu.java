package src.presentacion;

import src.dominio.Compressor;
import src.dominio.Decompressor;
import src.persistencia.File;
import tests.TestMenu;

import java.io.Console;
import java.util.Arrays;

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
     * Representa la petición de comprimir un archivo. Pregunta por el archivo o carpeta a comprimir, su carpeta de destino y el algoritmo con que se desea comprimir.
     */
    private void compress()
    {
        File source = readFileOrFolder("Please enter a file/folder path: ");
        File destination = readFolder("Please enter the destination folder path: ");
        String algorithmName = readAlgorithm();

        Compressor compressor = new Compressor(source, destination, algorithmName);
        compressor.compress();
    }
    
    /**
     * Representa la petición de descomprimir un archivo. Pregunta por el archivo comprimido y su carpeta de destino.
     */
    private void decompress()
    {
        File source = readFile("Please enter the compressed file path: ");
        File destination = readFolder("Please enter the destination folder path: ");
        Decompressor decompressor = new Decompressor(source, destination);
        decompressor.decompress();
    }

    /**
     * Representa la acción de ejecutar los drivers preparados.
     * Inicia el propio menu de los drivers.
     * 
     * @see tests.TestMenu
     */
    private void runTest()
    {
        TestMenu menutest = new TestMenu();
        menutest.start();
    }

    /**
     * Esta función muestra los posibles algoritmos de compressión (incluyendo la opción de automático) y espera hasta tener uno válido.
     * 
     * @return un string con el nombre del algoritmo seleccionado
     */
    private String readAlgorithm()
    {
        String[] possibleAlgorithms = {"LZ78", "LZSS", "LZW", "JPEG", "lz78", "lzss", "lzw", "jpeg", ""};
        String algorithm = console.readLine("Especify the algorithm (LZ78, LZSS, LZW, JPEG) (enter for auto): ");
        while(!Arrays.asList(possibleAlgorithms).contains(algorithm)) algorithm = console.readLine("Invalid algorithm (LZ78, LZSS, LZW, JPEG) (enter for auto): ");
        if(algorithm.equals("")) algorithm = "auto";
        return algorithm;
    }

    /**
     * Esta función pregunta por una carpeta o un fichero, si no existe pregunta por otro repetidamente.
     * 
     * @param message mensaje con el que se preguntara por la carpeta
     * @return una instancia de la clase File que representa la carpeta o el archivo
     * 
     * @see src.persistencia.File
     */
    private File readFileOrFolder(String message)
    {
        File file = new File(console.readLine(message));
        while(!file.exists())
            file = new File(console.readLine("File or folder not found. Please insert a valid file/folder path: "));
        return file;
    }

    /**
     * Esta función pregunta por una carpeta, si no existe pregunta por otra repetidamente.
     * 
     * @param message mensaje con el que se preguntara por la carpeta
     * @return una instancia de la clase File que representa la carpeta
     * 
     * @see src.persistencia.File
     */
    private File readFolder(String message)
    {
        File folder = new File(console.readLine(message));
        while(!folder.exists() || !folder.isDirectory())
            folder = new File(console.readLine("Folder not found. Please insert a valid folder: "));
        return folder;
    }

    /**
     * Esta función pregunta por un fichero, si no existe pregunta por otro repetidamente.
     * 
     * @param message mensaje con el que se preguntara por el fichero
     * @return una instancia de la clase File que representa el fichero
     * 
     * @see src.persistencia.File
     */
    private File readFile(String message)
    {
        File file = new File(console.readLine(message));
        while(!file.exists() || !file.isFile()){
            file = new File(console.readLine("File not found. Please insert a valid file: "));
        }
        return file;
    }
}