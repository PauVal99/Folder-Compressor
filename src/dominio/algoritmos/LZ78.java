package src.dominio.algoritmos;

import java.util.*;
import src.persistencia.*;

public class LZ78 extends Algorithm{
    public byte[] compress(UncompressedFile uncompressed){
        String result = "";
        HashMap<String, Integer> codeWord = new HashMap<String, Integer>();
        String current ="";
        int key = 0;
        boolean inD = false;
        byte[] in;
        while((in = uncompressed.readContent(1)).length != 0) {
            char c = new String(in).charAt(0);
            inD = false;
            current += c;
            if(codeWord.containsKey(current)){
                key = codeWord.get(current);
                inD = true;
            }
            else {
                result += key + ":" + c;
                codeWord.put(current,codeWord.size()+1);
                key = 0;
                current = "";
            }
        }
       /* if(inD){
            result += key;
        }*/

        return result.getBytes();
    }

    public byte[] decompress(CompressedFile compressedBytes){
        ArrayList<String> dictionary = new ArrayList<String>();
        String first, last;
        byte[] in;
        while((in = compressedBytes.readContent(3)).length != 0) {
            String s = new String(in);
            String[] sa = s.split(":");
            first = sa[0];
            last = sa[1];
            if(Integer.parseInt(first) == 0){
                dictionary.add(last);
            }
            else{
                int index = Integer.parseInt(first);
                dictionary.add(dictionary.get(index-1)+last);
            }
        }
        StringBuilder sb = new StringBuilder();
        for(String d : dictionary){
            sb.append(d);
        }
        return sb.toString().getBytes();
    }

    public String getName()
    {
        return "LZ78";
    }
}