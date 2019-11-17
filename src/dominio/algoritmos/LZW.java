package src.dominio.algoritmos;

import java.util.*;
import java.io.ByteArrayOutputStream;
import java.lang.Math;

import src.persistencia.*;
import src.dominio.ByteArrayHelper;

/**
 * Esta clase representa el algoritmo de compresión y descompresión LZW.
 * Se encarga de comprimir y descomprimir archivos. Guarda los códigos en un número de bytes variable para augmentar la eficiencia.
 * 
 * @author Pau Val
 */
public class LZW extends Algorithm
{
    /**
     * Comprime todo el texto del archivo representado por uncompressed.
     * Inicialmente escribe los códigos en un byte, cuando no es suficiente hace una marca y augmenta el numero de bytes.
     * 
     * @param uncompressed archivo a comprimir
     * @return array de bytes con el texto comprimido (no es legible, hay que descomprimirlo)
     * 
     * @see src.persistencia.UncompressedFile
     */
    public byte[] compress(UncompressedFile uncompressed)
    {
        int nBytes = 1;
        int dictSize = 256;
        int maxInt = 256;

        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < dictSize; i++){
            dictionary.put("" + (char) i, i);
        }

        String w = "";
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        char c;
        while((c = uncompressed.readChar()) != 0){
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                if(dictionary.get(w) >= maxInt){ 
                    add(result,0,nBytes);
                    nBytes++;
                    maxInt = (int) Math.pow(2,nBytes * 8);
                }
                add(result,dictionary.get(w),nBytes);
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }

        if (!w.equals(""))
            add(result,dictionary.get(w),nBytes);
        return result.toByteArray();
    }
    
    /**
     * Descomprime el archivo representado por compressed.
     * Inicialmente lee los códigos en un byte, cuando recive una marca augmenta el numero de bytes.
     * 
     * @param compressed archivo a descomprimir
     * @return array de bytes con el texto descomprimido
     * 
     * @see src.persistencia.CompressedFile
     */
    public byte[] decompress(CompressedFile compressed) {
        int nBytes = 1;
        int dictSize = 256;

        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < dictSize; i++)
            dictionary.put(i, "" + (char)i);

        byte[] bc;
        bc = compressed.readContent(nBytes);
        if(bc.length == 0) return new byte[0];

        String w = "" + (char) (ByteArrayHelper.byteArrayToInt(bc) & 0xFF);

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        result.write((byte) ByteArrayHelper.byteArrayToInt(bc));

        while((bc = compressed.readContent(nBytes)).length != 0){
            int cod = ByteArrayHelper.byteArrayToInt(bc);
            if(cod == 0) {
                nBytes++; 
                continue;
            }
            String act = "";
            if (dictionary.containsKey(cod))
                act = dictionary.get(cod);
            else if (cod == dictSize)
                act = w + (char)(w.charAt(0) & 0xFF);
            try{
                for(char c: act.toCharArray()) result.write((byte) (c & 0xFF));
            }
            catch(Exception e){
                e.printStackTrace();
            }
            dictionary.put(dictSize++, w + (char)(act.charAt(0) & 0xFF));
            w = act;
        }

        return result.toByteArray();
    }

    private void add(ByteArrayOutputStream b, int n, int nBytes)
    {
        for(int i=0; i<nBytes; i++) b.write((byte) (n >>> (i * 8)));
    }

    /**
     * Retorna el nombre de este algoritmo
     * 
     * @return nombre del algoritmo
     */
    public String getName()
    {
        return "LZW";
    }
}