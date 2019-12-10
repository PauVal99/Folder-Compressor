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

        Map<String,Integer> dic = new HashMap<String,Integer>();
        for (int i = 0; i < dictSize; i++){
            dic.put("" + (char) i, i);
        }

        String w = "";
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] bc = {}; byte b;
        while((bc = uncompressed.readContent(1)).length != 0){
            b = bc[0];
            String wc = w + byteToChar(b);
            if (dic.containsKey(wc))
                w = wc;
            else {
                if(dic.get(w) >= maxInt){ 
                    write(result,nBytes,0);
                    nBytes++;
                    maxInt = (int) Math.pow(2,nBytes * 8);
                }
                write(result,nBytes,dic.get(w));
                dic.put(wc, dictSize++);
                w = "" + byteToChar(b);
            }
        }

        if (!w.equals(""))
            write(result,nBytes,dic.get(w));
        return result.toByteArray();
    }
    
    /**
     * Escribe en el byte array el entero n representdo por nBytes
     * 
     * @param b instancia de la clase ByteArrayOutputStream
     * @param nBytes numero de bytes con los que se representa n
     * @param n entero a escribir
     */
    private void write(ByteArrayOutputStream b, int nBytes, int n)
    {
        try{
            b.write(ByteArrayHelper.intToByteArray(n, nBytes));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
                act = w + w.charAt(0);
            try{
                for(char c: act.toCharArray()) result.write(charToByte(c));
            }
            catch(Exception e){
                e.printStackTrace();
            }
            dictionary.put(dictSize++, w + act.charAt(0));
            w = act;
        }

        return result.toByteArray();
    }

    /**
     * Convierte un caracter en un byte (solo guarda la parte baja)
     * 
     * @param c caracter a convertir
     * @return byte con que se representa c
     */
    private byte charToByte(char c)
    {
        return (byte) (c & 0xFF);
    }

    /**
     * Convierte un byte en un caracter sin extension de signo
     * 
     * @param b byte a convertir
     * @return caracter que representa b
     */
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
        return "LZW";
    }
}