package tests;

import src.persistencia.File;
import src.dominio.Compressor;
import src.dominio.Decompressor;

import java.nio.file.Files;
import java.util.Arrays;

public class Tester {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";

    public void test(String sourceName, String alg_name) 
    {
        System.out.println(CYAN_BOLD_BRIGHT + "\nTest with algorithm:"+alg_name+", File:"+sourceName + ANSI_RESET);

        File source = new File("data/"+sourceName);
        File compressFolder = new File("data/compressed/"+alg_name);
        File compressedFile = new File("data/compressed/"+alg_name+"/"+source.getFileName()+".cmprss");
        File result = new File("data/compressed/"+alg_name+"/"+sourceName);

        Compressor compressor = new Compressor(source, compressFolder, alg_name, 50);
        compressor.execute();

        Decompressor decompressor = new Decompressor(compressedFile, compressFolder);
        decompressor.execute();

        if (contentEquals(source, result)) System.out.println(ANSI_GREEN + "Test Passed! Algorithm is working good!" + ANSI_RESET);
        else System.out.println(ANSI_RED +sourceName+" test failed." + ANSI_RESET);

        compressedFile.delete();
        result.delete();
    }

    public void testJPEG(String sourceName)
    {
        System.out.print(CYAN_BOLD_BRIGHT + "\nTest with algorithm:JPEG, File:"+sourceName+"\n" + ANSI_RESET);

        File source = new File("data/"+sourceName);
        File compressFolder = new File("data/compressed/JPEG");
        File compressedFile = new File("data/compressed/JPEG/"+source.getFileName()+".cmprss");
        File result = new File("data/compressed/JPEG/"+sourceName);

        Compressor compressor = new Compressor(source, compressFolder, "JPEG", 50);
        compressor.execute();

        Decompressor decompressor = new Decompressor(compressedFile, compressFolder);
        decompressor.execute();

        if (contentEquals(source, result)) System.out.println(ANSI_GREEN + "Test Passed! Algorithm is working good!" + ANSI_RESET);
        else System.out.println(ANSI_RED +sourceName+" test failed." + ANSI_RESET);

        compressedFile.delete();
        result.delete();
    }

    private static boolean contentEquals(File expected, File given)
    {
        boolean equals = true;
        if(expected.isFile()){
            try{
                equals = Arrays.equals(Files.readAllBytes(expected.toPath()),Files.readAllBytes(given.toPath()));
            }
            catch(Exception e){
                System.out.println("Error reading file.");
            }
        }
        else {
            File[] expectedList = expected.listFiles();
            File[] givenList = given.listFiles();
            int i=0;
            for(File ef: expectedList){
                equals = equals & contentEquals(ef,givenList[i]);
                ++i;
            }
        }
        return equals;
    }
}