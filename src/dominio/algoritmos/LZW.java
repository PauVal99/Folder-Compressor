package src.dominio.algoritmos;

import src.dominio.IntegerToByteHelper;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.Math;

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
    public ByteArrayOutputStream compress(ByteArrayInputStream input)
    {
        int nBytes = 1;
        int dictSize = 256;
        int maxInt = 256;

        Map<String,Integer> dic = new HashMap<String,Integer>();
        for (int i = 0; i < dictSize; i++){
            dic.put("" + (char) i, i);
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try{
            String w = "";
            byte b;
            while((b = (byte) (input.read() & 0xFF)) != -1){
                String wc = w + byteToChar(b);
                if (dic.containsKey(wc))
                    w = wc;
                else {
                    if(dic.get(w) >= maxInt){ 
                        output.write(IntegerToByteHelper.intToByteArray(0, nBytes));
                        nBytes++;
                        maxInt = (int) Math.pow(2,nBytes * 8);
                    }
                    output.write(IntegerToByteHelper.intToByteArray(dic.get(w), nBytes));
                    dic.put(wc, dictSize++);
                    w = "" + byteToChar(b);
                }
            }

            if (!w.equals(""))
                output.write(IntegerToByteHelper.intToByteArray(dic.get(w), nBytes));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return output;
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
    public ByteArrayOutputStream decompress(ByteArrayInputStream input)
    {
        int nBytes = 1;
        int dictSize = 256;

        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < dictSize; i++)
            dictionary.put(i, "" + (char)i);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try{
            byte[] bc = new byte[nBytes];
            int r = input.read(bc);
            if(r == -1) return output;

            String w = "" + (char) (IntegerToByteHelper.byteArrayToInt(bc) & 0xFF);

            output.write((byte) IntegerToByteHelper.byteArrayToInt(bc));

            while((r = input.read(bc)) != -1){
                int cod = IntegerToByteHelper.byteArrayToInt(bc);
                if(cod == 0) {
                    nBytes++;
                    bc = new byte[nBytes];
                    continue;
                }
                String act = "";
                if (dictionary.containsKey(cod))
                    act = dictionary.get(cod);
                else if (cod == dictSize)
                    act = w + w.charAt(0);
                try{
                    for(char c: act.toCharArray()) output.write(charToByte(c));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                dictionary.put(dictSize++, w + act.charAt(0));
                w = act;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return output;
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