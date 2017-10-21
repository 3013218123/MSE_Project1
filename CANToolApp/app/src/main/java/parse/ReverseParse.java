package parse;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import dataRead.SGRead;
import entity.Signal;

/**
 * Created by wenhao on 2017/10/18.
 */

public class ReverseParse {

    public static String reverseParse(String BO_id,double[] userInputPhy,String fileName){
        ArrayList<Signal> Signals= SGRead.readSG(BO_id,fileName);
        int index=0;
        //初始化一个全0的data数组，用来保存数据
        String data[] = new String [] {"00000000","00000000","00000000","00000000",
                "00000000","00000000","00000000","00000000"};
        //全局最大行数
        int maxRow=0;
        for(Iterator it = Signals.iterator(); it.hasNext(); )
        {
            //声明一个临时最大行数，找使用的最大行
            int tempMaxRow=0;
            Signal  signal= (Signal) it.next();
            //填充数据矩阵，从而得到DD
            //1.计算信号量x,int型,x2表示2进制信号量
            int x=(int)((userInputPhy[index]-signal.getB())/signal.getA());
            String x2=Integer.toBinaryString(x);
            for(int i=0;i<signal.getDataLength()-x2.length();i++) x2="0"+x2;//补足位数
            //如果只在一行显示，则两种方式相同，一起写
            int startRow=signal.getStartBit()/8;
            int startColumn=signal.getStartBit()%8;
            int emptyBit=8-startColumn;
            String strRight="",strLeft="";
            if(startColumn>0) strRight=data[startRow].substring(8-startColumn,8);
            if(emptyBit>=signal.getDataLength()) { //只需要在该行显示
                if(emptyBit>signal.getDataLength()) strLeft=data[startRow].substring(0, emptyBit-signal.getDataLength());
                x2=strLeft+x2+strRight;
                data[startRow]=x2;
                tempMaxRow=startRow;
            }else {//多行显示，分类型
                if(signal.getArrangeType().equals("1+")) {//intel型
                    int row=(signal.getDataLength()-emptyBit)/8;//剩余行数
                    int c=(signal.getDataLength()-emptyBit)%8;//最后一行的个数
                    tempMaxRow=startRow+row;
                    data[startRow]=x2.substring(x2.length()-emptyBit, x2.length())+strRight;
                    x2=x2.substring(0,x2.length()-emptyBit);
                    for(int i=1;i<=row;i++) {
                        data[startRow+i]=x2.substring(x2.length()-8, x2.length());
                        x2=x2.substring(0,x2.length()-8);
                    }
                    if(c!=0) {
                        data[startRow+row+1]=data[startRow+row+1].substring(0, 8-c)+x2;
                        tempMaxRow++;
                    }
                }else if(signal.getArrangeType().equals("0+")) {//motorola型
                    int row=(signal.getDataLength()-emptyBit)/8;//剩余行数
                    int c=(signal.getDataLength()-emptyBit)%8;//最后一行的个数
                    tempMaxRow=startRow+row;
                    data[startRow]=x2.substring(0, emptyBit)+strRight;
                    x2=x2.substring(emptyBit,x2.length());
                    for(int i=1;i<=row;i++) {
                        data[startRow+i]=x2.substring(0, 8);
                        x2=x2.substring(8,x2.length());
                    }
                    if(c!=0) {
                        data[startRow+row+1]=x2+data[startRow+row+1].substring(c, 8);
                        tempMaxRow++;
                    }
                }
            }

            if(tempMaxRow>maxRow) maxRow=tempMaxRow;
            index++;
        }
        //需要知道使用了前多少字节，如果小于8，后面的字节不要了
        //使用了0-maxRow行

        String DD="";
        int numDD=maxRow+1;
        for(int j=0;j<numDD;j++) {
            DD=DD+binaryString2hexString(data[j]);
        }
        Log.i("tag",DD);
        /*
        if(BO_id.length()==3) {
            T_type="t";
        }else{
            T_type="T";
        }
        */

        //这里需要进行转换
        //T_type的判断也需要重写
        String T_type="t";
        String id=""+decimalToHex(Integer.parseInt(BO_id));
        while(id.length()<3){
            id="0"+id;
        }
        String str=T_type+id+numDD+DD;

        return str;


    }

    public static String binaryString2hexString(String bString)
    {
        while(bString.length()%8!=0) {
            bString="0"+bString;
        }
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4)
        {
            iTmp = 0;
            for (int j = 0; j < 4; j++)
            {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }
    public static String decimalToHex(int decimal) {
        String hex = "";
        while(decimal != 0) {
            int hexValue = decimal % 16;
            hex = toHexChar(hexValue) + hex;
            decimal = decimal / 16;
        }
        return  hex;
    }
    //将0~15的十进制数转换成0~F的十六进制数
    public static char toHexChar(int hexValue) {
        if(hexValue <= 9 && hexValue >= 0)
            return (char)(hexValue + '0');
        else
            return (char)(hexValue - 10 + 'A');
    }
}
