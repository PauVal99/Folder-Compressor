package src.dominio.algoritmos;

import java.util.*;
import src.persistencia.*;

public class LZ78 extends Algorithm{
    public byte[] compress(UncompressedFile uncompressed){
        byte[] result = new byte[0];
        HashMap<String, Integer> codeWord = new HashMap<String, Integer>();
        String current ="";
        int key = 0;
        char c;
        boolean found = false;
        while((c = uncompressed.readChar()) != 0){
            current += c;
            found = false;
            if(codeWord.containsKey(current)){
                key = codeWord.get(current);
                found = true;
            }
            else {
                result = concatenate(result ,intToByteArray(key));
                byte[] aux = {(byte) c};
                result = concatenate(result, aux);
               // System.out.print(c + " ");
                codeWord.put(current,codeWord.size()+1);
                key = 0;
                current = "";
            }
        }
         if(found){ //!current.equals("")
              result = concatenate(result ,intToByteArray(key));
              byte[] aux = {(byte)current.charAt(0)};
              result = concatenate(result, aux);
            //  System.out.print("\ny" + (byte) current.charAt(0) + "\nx");
         }

        return result;
    }

    public byte[] decompress(CompressedFile compressed){
        ArrayList<String> dictionary = new ArrayList<String>();
        int first;
        char last;
        byte[] pair;
        while((pair = compressed.readContent(4)).length != 0) {
            first = byteArrayToInt(pair);
            last = (char) pair[3];
         //   if(last == '.')
            if(first == 0){
                dictionary.add(last+"");
            }
            else{
                dictionary.add(dictionary.get(first-1)+last);
            }
        }
        StringBuilder sb = new StringBuilder();
        for(String d : dictionary){
            sb.append(d);
         //   System.out.print(d + " ");
        }
        return sb.toString().getBytes();
    }

    private byte[] intToByteArray(int n)
    {
        byte[] buffer = new byte[3];
        for(int i=0; i<3; i++) buffer[i] = (byte) (n >>> (i * 8));
        return buffer;
    }

    private int byteArrayToInt(byte[] buffer)
    {
        int n = 0;
        for (int i = 0; i < 3; i++) {
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
        return "LZ78";
    }
}