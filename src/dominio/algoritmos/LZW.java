package src.dominio.algoritmos;

import src.util.CharToByte;
import src.util.IntegerToByte;
import src.persistencia.InputBuffer;
import src.persistencia.OutputBuffer;

import java.util.*;
import java.lang.Math;

/**
 * Esta clase representa el algoritmo de compresión y descompresión LZW.
 * Se encarga de comprimir y descomprimir bytes. Guarda los códigos en un número de bytes variable para augmentar la eficiencia.
 * 
 * @author Pau Val
 */
public class LZW extends Algorithm
{
    /**
     * {@inheritDoc}
     */
    public OutputBuffer compress(InputBuffer input)
    {
        int nBytes = 1;
        int dictSize = 256;
        int maxInt = 256;

        Map<String,Integer> dic = new HashMap<String,Integer>();
        for (int i = 0; i < dictSize; i++) dic.put("" + (char) i, i);

        OutputBuffer output = new OutputBuffer();
        try{
            String w = "";
            byte b;
            while((b = (byte) (input.read() & 0xFF)) != -1){
                String wc = w + CharToByte.byteToChar(b);
                if (dic.containsKey(wc))
                    w = wc;
                else {
                    if(dic.get(w) >= maxInt){ 
                        output.write(IntegerToByte.intToByteArray(0, nBytes));
                        nBytes++;
                        maxInt = (int) Math.pow(2,nBytes * 8);
                    }
                    output.write(IntegerToByte.intToByteArray(dic.get(w), nBytes));
                    dic.put(wc, dictSize++);
                    w = "" + CharToByte.byteToChar(b);
                }
            }

            if (!w.equals(""))
                output.write(IntegerToByte.intToByteArray(dic.get(w), nBytes));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return output;
    }

    /**
     * {@inheritDoc}
     */
    public OutputBuffer decompress(InputBuffer input)
    {
        int nBytes = 1;
        int dictSize = 256;

        Map<Integer,String> dic = new HashMap<Integer,String>();
        for (int i = 0; i < dictSize; i++) dic.put(i, "" + (char)i);

        OutputBuffer output = new OutputBuffer();
        try{
            byte[] bc = new byte[nBytes];
            int r = input.read(bc);
            if(r == -1) return output;

            String w = "" + (char) (IntegerToByte.byteArrayToInt(bc) & 0xFF);

            output.write((byte) IntegerToByte.byteArrayToInt(bc));

            while((r = input.read(bc)) != -1){
                int cod = IntegerToByte.byteArrayToInt(bc);
                if(cod == 0) {
                    nBytes++;
                    bc = new byte[nBytes];
                    continue;
                }
                String act = "";
                if (dic.containsKey(cod))
                    act = dic.get(cod);
                else if (cod == dictSize)
                    act = w + w.charAt(0);
                for(char c: act.toCharArray()) output.write(CharToByte.charToByte(c));
                dic.put(dictSize++, w + act.charAt(0));
                w = act;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return output;
    }

    /**
     * {@inheritDoc}
     */
    public String getName()
    {
        return "LZW";
    }
}