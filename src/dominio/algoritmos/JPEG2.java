package src.dominio.algoritmos;

import src.persistencia.File;

public class JPEG extends Algorithm
{
    public byte[] compress(File uncompressed)
    {
    
        byte[] version= new byte[3];
        version[0]=data[0];
        version[1]=data[1];
        version[2]=data[2];

        int it;
        ArrayList<Byte> wid= new ArrayList<>();
        ArrayList<Byte> hei= new ArrayList<>();

        //OBTENIR SIZE FOTO
        boolean comment=false,first=false;
        for (it = 3; it < data.length; it++) {
            if(!comment){
                if((char)data[it] == '#'){
                    comment= true;
                }
                else if (data[it] == ' ') first= true;

                else if(! first){
                    wid.add(data[it]);
                }
                else if(first){
                    if(data[it] == 10) break;
                    hei.add(data[it]);
                }
            }
            else{
                if(data[it] == 10){
                    comment= false;
                }
            }
        }

        ArrayList<Byte> max_val= new ArrayList<>();
        comment=false;

        for (++it; it < data.length; it++) {
            if(!comment){
                if((char)data[it] == '#'){
                    comment= true;
                }
                else if(data[it] == 10) break;
                else max_val.add(data[it]);
            }
            else{
                if(data[it] == 10){
                    comment= false;
                }
            }
        }
        it+=3;
         //OBTENIR TAMAÑ IMATGE
        short w= im_size(wid);
        short h= im_size(hei);
        /*
        System.out.println(w);
        System.out.println(h);
        */

        int imageSize= w*h;
        byte[] Y= new byte[imageSize];
        byte[] U= new byte[imageSize];
        byte[] V= new byte[imageSize];
        int imageSizeX3=data.length;

        //TRANSFORMACIO DE RGB A YCbC|YUV
        for(int i=0; it < imageSizeX3; it+=3, ++i) {
            Y[i]= (byte)(0 + ( 0.299 * data[it-2] ) + ( 0.587 * data[it-1] ) + ( 0.114 * data[it] ) );
            U[i]= (byte)(128 - ( 0.168736 * data[it-2] ) - (0.331264 * data[it-1] ) + (0.5 * data[it]) );
            V[i]= (byte)(128 + ( 0.5 * data[it-2] ) - ( 0.418688 * data[it-1] ) - ( 0.081312 * data[it]) );
        }

        //RESTAR 128 PARA PODER APLICAR DCT(FUNCIÓ AMB COSINUS, VALORS ENTRE -128,127)
        for(int j=0; j < imageSize; j++){
            Y[j]-=128;
            U[j]-=128;
            V[j]-=128;
        }

        short[] YD= new short[imageSize];
        short[] UD= new short[imageSize];
        short[] VD= new short[imageSize];

        //DCT TRANSFORMATION
        double alphaU,alphaV;

        for (int i = 0; i < imageSize; i+=64) {
            int u=0;
            int v=0;
            for (int j = 0; j < 64; j++) {
                if(j%8 == 0 && j!=0) {
                    u++;
                    v=0;
                }

                if(u == 0)alphaU= 1/Math.sqrt(2);
                else alphaU= 1;
                if(v == 0)alphaV= 1/Math.sqrt(2);
                else alphaV= 1;

                float x,y;
                x=y=0;
                short YAUX=0;
                short UAUX=0;
                short VAUX=0;
                for (int k = 0; k < 64; k++) {
                    if(k%8 == 0 && k!=0){
                        x++;
                        y=0;
                    }
                    double calc= ( Math.cos( ((2*x+1)*Math.PI*u)/16 ) * Math.cos( ((2*y+1)*Math.PI*v)/16 ) );

                    YAUX += (short)( ( Y[i+k] * calc ) );
                    UAUX += (short)( ( U[i+k] * calc ) );
                    VAUX += (short)( ( V[i+k] * calc ) );

                    ++y;
                }
                double calc= 1.0/4 * alphaU * alphaV;
                YD[i+j]= (short)(YAUX * calc);
                UD[i+j]= (short)(UAUX * calc);
                VD[i+j]= (short)(VAUX * calc);
                ++v;
            }
        }

        //Cuantification
        for (int i = 0; i < imageSize; i++) {
            YD[i]= (short)Math.round(YD[i]/cuant_mat[i%64]);
            UD[i]= (short)Math.round(UD[i]/cuant_mat[i%64]);
            VD[i]= (short)Math.round(VD[i]/cuant_mat[i%64]);
        }

        ArrayList<Short> resY= new ArrayList<>();
        ArrayList<Short> resU= new ArrayList<>();
        ArrayList<Short> resV= new ArrayList<>();
        ArrayList<Short> aux= new ArrayList<>();

        //SIMPLIFICAR ARRAYS
        simplify_res(resY,aux,YD,imageSize);
        aux.clear();

        simplify_res(resU,aux,UD,imageSize);
        aux.clear();

        simplify_res(resV,aux,VD,imageSize);

        //FALTA ESCRIURE CONTINGUT A UN FILE
        ArrayList<Short> RES = new ArrayList<>(resY);
        RES.add((short)3100);
        RES.addAll(resU);
        RES.add((short)3100);
        RES.addAll(resV);
        RES.add((short)3100);
        RES.add((short)version[1]);
        RES.add(w);
        RES.add(h);
        ArrayList<Byte> rbyte=new ArrayList<>();
        toBytes(RES,rbyte);
        
        byte[] b = new byte[1];
        return b;
    }
    public byte[] decompress(File compressedBytes)
    {
        ArrayList<Short> Y= new ArrayList<>();
        ArrayList<Short> U= new ArrayList<>();
        ArrayList<Short> V= new ArrayList<>();

        //RECONSTRUIR INPUT
        int it=1;
        char ver='6';
        short w=0,h=0;

        int[] prev= restoreData(Y,data,it);

        prev= restoreData(U,data,prev[0]);

        prev= restoreData(V,data,prev[0]);

        if((short)prev[1] == 3000) fillData(V,prev[2]);

        else if(prev[1] != 3100) V.add((short)prev[1]);

        else{
            it= prev[0];
            short info= (short)( ( (data[it-1] & 0xFF) << 8) | (data[it] & 0xFF) );
            if(info == 3100){
                it+=2;
                info= (short)( ( (data[it-1] & 0xFF) << 8) | (data[it] & 0xFF) );
            }
            ver=(char)info;
            it+=2;
            w= (short)( ( (data[it-1] & 0xFF) << 8) | (data[it] & 0xFF) );
            it+=2;
            h= (short)( ( (data[it-1] & 0xFF) << 8) | (data[it] & 0xFF) );
        }

        //Decuantification
        int imageSize=w*h;

        for (int i = 0; i < imageSize; i++) {
            Y.set(i, (short) (Y.get(i) * cuant_mat[i % 64]));
            U.set(i, (short) (U.get(i) * cuant_mat[i % 64]));
            V.set(i, (short) (V.get(i) * cuant_mat[i % 64]));
        }

        short[] YD= new short[imageSize];
        short[] UD= new short[imageSize];
        short[] VD= new short[imageSize];


        //INVERSE DCT
        double alphaU,alphaV;

        for (int i = 0; i < imageSize; i+=64) {
            int x=0;
            int y=0;
            for (int j = 0; j < 64; j++) {
                if(j%8 == 0 && j!=0) {
                    x++;
                    y=0;
                };

                float u,v;
                u=v=0;

                short YAUX=0;
                short UAUX=0;
                short VAUX=0;

                for (int k = 0; k < 64; k++) {
                    if(k%8 == 0 && k!=0){
                        u++;
                        v=0;
                    }

                    if(u == 0)alphaU= 1/Math.sqrt(2);
                    else alphaU= 1;
                    if(v == 0)alphaV= 1/Math.sqrt(2);
                    else alphaV= 1;

                    double calc= ( Math.cos( ((2*x+1)*Math.PI*u)/16 ) * Math.cos( ((2*y+1)*Math.PI*v)/16 ) );
                    calc= calc * alphaV*alphaU;

                    YAUX += (short)((Y.get(i + k) * calc));
                    UAUX += (short)((U.get(i + k) * calc));
                    VAUX += (short)((V.get(i + k) * calc));

                    ++v;
                }
                YD[i+j]= (short)(YAUX * 1.0/4);
                UD[i+j]= (short)(UAUX * 1.0/4);
                VD[i+j]= (short)(VAUX * 1.0/4);
                ++y;
            }
        }
        //SUMAR
        for(int i=0; i < imageSize; i++){
            YD[i]+=128;
            UD[i]+=128;
            VD[i]+=128;
        }
        //System.out.println("owo");
        byte[] R= new byte[imageSize];
        byte[] G= new byte[imageSize];
        byte[] B= new byte[imageSize];
        //TRANSFORMAR DE YUV-> RGB
        for (int i = 0; i < imageSize; i++) {
            R[i]= (byte)( YD[i] + ( 1.402 * ( VD[i] - 128 ) ) );
            G[i]= (byte)( YD[i] - ( 0.344136 * ( UD[i] - 128 ) ) - ( 0.714136 * ( VD[i] - 128 ) ) );
            B[i]= (byte)( YD[i] + ( 1.772 * (UD[i] - 128 ) ) );
        }
        char[] he= short2ascii(h);
        char[] wi= short2ascii(w);
        byte[] RES= new byte[imageSize * 3 + 3 + wi.length + 1 +he.length + 1 + 3 +1];

        //INSERIR HEADER
        RES[0]='P';
        RES[1]=(byte)ver;
        RES[2]=10;
        int pos=3;

        for (int i = 0; i < wi.length; i++) RES[pos++]=(byte)wi[i];

        RES[pos++]=' ';

        for (int i = 0; i < he.length; i++) RES[pos++]=(byte)he[i];

        RES[pos++]= 10;

        RES[pos++]='2';
        RES[pos++]='5';
        RES[pos++]='5';
        RES[pos++]=10;

        for (int i = 0; i < imageSize; i++) {
            RES[pos++]=R[i];
            RES[pos++]=G[i];
            RES[pos++]=B[i];
        }
        return new byte[0];
    }

    public String getName()
    {
        return "JPEG";
    }
    
    private static float[] cuant_mat= {16,11,10,16,24,40,51,61,
            12,12,14,19,26,58,60,55,
            14,13,16,24,40,57,69,56,
            14,17,22,29,51,87,80,62,
            18,22,37,56,68,109,103,77,
            24,35,55,64,81,104,113,92,
            49,64,78,87,103,121,120,101,
            72,92,95,98,112,100,103,99
    };
    
    private static char[] asciiDigits={'0','1','2','3','4','5','6','7','8','9'};
    
    public void toBytes(ArrayList<Short> res, ArrayList<Byte> rbyte){
        byte partB,partA;

        for (int i = 0; i < res.size(); i++) {
            int aux=res.get(i);

            partB= (byte)aux;
            aux= aux >> 8;
            partA=(byte)aux;

            rbyte.add(partA);
            rbyte.add(partB);
        }
        System.out.println(rbyte.size());
        System.out.println(res.size());
    }
    
    public char[] short2ascii(short i){
    
        char[] res;
        short aux= i;
        ArrayList<Character> ra= new ArrayList<>();
        while (aux > 0){
            ra.add(asciiDigits[aux%10]);
            aux/=10;
        }
        res= new char[ra.size()];
        int k=0;
        for (int j = ra.size()-1; j >= 0; j--) res[k++]= ra.get(j);
        return res;
    }
    public int[] restoreData(ArrayList<Short> D, byte[] data, int it){
    
        int dataSize=data.length, elements=0;
        boolean end=false;
        short info=0;
        while (it < dataSize && ! end){

            info= (short)( ( (data[it-1] & 0xFF) << 8) | (data[it] & 0xFF) );

            if(info == 3100) end=true;
            else if(info == 3000) {
                fillData(D,elements);
                elements=0;
            }
            else{
                D.add(info);
                ++elements;
            }
            it+=2;
        }
        int[] ret= new int[3];
        ret[0]=it;
        ret[1]=info;
        ret[2]=elements;
        return ret;
    }
    public  void fillData(ArrayList<Short> D, int elements){
    
        int iterations= 64-elements;
        for (int i = 0; i < iterations; i++) D.add((short)0);
    }
    public short im_size(ArrayList<Byte> in){
    
        short aux=0;

        for (int i = 0; i < in.size(); i++) {
            aux=(short) ((aux) +(in.get(i)-(byte)48));
            if(i < in.size()-1)aux *=10;
        }
        return aux;
    }
    public void simplify_res(ArrayList<Short> res, ArrayList<Short> aux, short[] data, int imageSize) {
        boolean endseq=false;

        for (int i = 0; i < imageSize; i++) {

            if(i%64 == 0 && i != 0){
                aux.clear();
                if(endseq){
                    res.add((short)3000);
                    endseq=false;
                }
            }

            if(endseq) {
                if(data[i] != 0) {
                    endseq=false;
                    res.addAll(aux);
                    aux.clear();
                    res.add(data[i]);
                }
                else {
                    aux.add(data[i]);
                }
            }

            else{
                if(data[i] == 0){
                    endseq=true;
                    aux.add(data[i]);
                }
                else{
                    res.add(data[i]);
                }
            }
        }
        if(endseq){
            res.add((short)3000);
        }
    }
    
}
