package src.dominio.algoritmos;

import java.util.*;
import java.lang.Math;

import src.persistencia.*;

public class LZW extends Algorithm
{
    private int nBytes = 1;
    private int dictSize = 128;

    public byte[] compress(UncompressedFile uncompressed)
    {
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < dictSize; i++)
            dictionary.put("" + (char)i, i);

        String w = "";
        byte[] result = new byte[0];
        char c;
        while((c = uncompressed.readChar()) != 0){
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                if(dictionary.get(w) >= Math.pow(2,nBytes * 8)){ 
                    result = concatenate(result, intToByteArray(0));
                    nBytes++;
                }
                result = concatenate(result, intToByteArray(dictionary.get(w)));
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }

        if (!w.equals(""))
            result = concatenate(result, intToByteArray(dictionary.get(w)));
        return result;
    }
    
    public byte[] decompress(CompressedFile compressed) {
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < dictSize; i++)
            dictionary.put(i, "" + (char)i);

        byte[] bc;
        String w = "" + (char)byteArrayToInt(compressed.readContent(nBytes));
        StringBuilder result = new StringBuilder(w);
        while((bc = compressed.readContent(nBytes)).length != 0){
            int k = byteArrayToInt(bc);
            if(k == 0) {nBytes++; continue;}
            String entry = "";
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);
 
            result.append(entry);

            dictionary.put(dictSize++, w + entry.charAt(0));
 
            w = entry;
        }

        return result.toString().getBytes();
    }

    private byte[] intToByteArray(int n)
    {
        byte[] buffer = new byte[nBytes];
        for(int i=0; i<nBytes; i++) buffer[i] = (byte) (n >>> (i * 8));
        return buffer;
    }

    private int byteArrayToInt(byte[] buffer)
    {
        int n = 0;
        for (int i = 0; i < buffer.length; i++) {
            int a = ((buffer[i] & 0xff)) * (int) Math.pow(2,i * 8);
            n = n + a;
        }
        return n;
    }

    private byte[] concatenate(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;

        byte[] c = new byte[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }


    public String getName()
    {
        return "LZW";
    }
}