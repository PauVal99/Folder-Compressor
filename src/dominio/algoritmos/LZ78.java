package src.dominio.algoritmos;

import java.util.*;
import src.persistencia.InputBuffer;
import src.persistencia.OutputBuffer;
import src.dominio.IntegerToByteHelper;

/**
 * Esta clase representa el algoritmo de compresión y descompresión LZ78.
 * Se encarga de comprimir y descomprimir archivos. Usando parejas de enteros y caracteres donde los primeros referencian a los segundos.
 * 
 * @author Pol Aguilar
 */

public class LZ78 extends Algorithm{

     /**
     * Comprime todo el texto del archivo representado por el input.
     * Se va leyendo y tratando cada caracter de uno en uno.
     * 
     * @param input ByteArrayInputStream con el archivo a comprimir
     * @return ByteArrayOutputStream con el texto comprimido (no es legible, hay que descomprimirlo)
     * 
     *  
     */
    public OutputBuffer compress(InputBuffer input){
        OutputBuffer result = new OutputBuffer();
        HashMap<String, Integer> codeWord = new HashMap<String, Integer>();
        String current ="";
        int key = 0;
        byte b;
        boolean found = false;
        while((b = (byte) (input.read() & 0xFF)) != -1){
            current += byteToChar(b);
            found = false;
            if(codeWord.containsKey(current)){
                key = codeWord.get(current);
                found = true;
            }
            else {
                try{
                result.write(IntegerToByteHelper.intToByteArray(key, 4));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                result.write(b);
                codeWord.put(current,codeWord.size()+1);
                key = 0;
                current = "";
            }
        }
        
        if(found){
            try{
                result.write(IntegerToByteHelper.intToByteArray(0, 4));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            result.write(charToByte(current.charAt(0)));
         }

        return result;
    }

  /**
     * Descomprime el archivo representado por el input.
     * Lee enteros y caracteres y los trata con un dictionary para descomprimirlo.
     * 
     * @param input ByteArrayInputStream con el archivo a descomprimir
     * @return ByteArrayOutputStream con el texto descomprimido
     * 
     * 
     */

    public OutputBuffer  decompress(InputBuffer input){
        OutputBuffer result = new OutputBuffer();
        try{ 
        ArrayList<String> dictionary = new ArrayList<String>();
        int first;
        char last;
        byte[]  pair = new byte[5];
        while((input.read(pair)) != -1) {
            byte[] convert = Arrays.copyOfRange(pair,0,3);
            first = IntegerToByteHelper.byteArrayToInt(convert);
            last = byteToChar(pair[4]);
            if(first == 0){
                dictionary.add(last+"");
            }
            else{
                dictionary.add(dictionary.get(first-1)+last);
            }
        }
       
        for(String d : dictionary)
            for(char c : d.toCharArray())
                result.write(charToByte(c));

    
    }
    
        catch(Exception e){
        System.out.println(e.getMessage());
        }
        return result;
    }

    private byte charToByte(char c) 
    {
        return (byte) (c & 0xFF);
    }

    private char byteToChar(byte b)
    {
        return (char) (b & 0xFF);
    }

     /**
     * Retorna el nombre de este algoritmo
     * 
     * @return nombre del algoritmo
     */
    public String getName()
    {
        return "LZ78";
    }
}