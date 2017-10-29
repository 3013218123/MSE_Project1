package com.example.administrator.canol.parse;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.administrator.canol.dataRead.BORead;
import com.example.administrator.canol.dataRead.SGRead;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.Signal;

/**
 * Created by wenhao on 2017/10/18.
 */
//王而川发送信息函数，
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
            if(x<0) {
                continue;
            }
            String x2=Integer.toBinaryString(x);
            int m=signal.getDataLength()-x2.length();
            for(int i=0;i<m;i++) {//补足位数
                x2="0"+x2;
            }

            int startRow=signal.getStartBit()/8;
            int startColumn=signal.getStartBit()%8;
            String strRight="",strLeft="";
            if(signal.getArrangeType().equals("1+")) {//intel型
                int emptyBit=8-startColumn;
                if(startColumn>0) strRight=data[startRow].substring(8-startColumn,8);
                if(emptyBit>=signal.getDataLength()) { //只需要在该行显示
                    if(emptyBit>signal.getDataLength()) strLeft=data[startRow].substring(0, emptyBit-signal.getDataLength());
                    x2=strLeft+x2+strRight;
                    data[startRow]=x2;
                    tempMaxRow=startRow;

                }else{
                    int row=(signal.getDataLength()-emptyBit)/8;//剩余行数
                    int c=(signal.getDataLength()-emptyBit)%8;//最后一行的个数
                    tempMaxRow=startRow+row;
                    //if(startColumn>0) strRight=data[startRow].substring(8-startColumn,8);
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
                }

            }else if(signal.getArrangeType().equals("0+")) {//motorola型
                int emptyBit=startColumn+1;
                strLeft=data[startRow].substring(0,8-emptyBit);
                if(emptyBit>=signal.getDataLength()) { //只需要在该行显示
                    if(emptyBit>signal.getDataLength()) strRight=data[startRow].substring(7-startColumn+signal.getDataLength(), 8);
                    x2=strLeft+x2+strRight;
                    data[startRow]=x2;
                    tempMaxRow=startRow;
                }else{
                    int row=(signal.getDataLength()-emptyBit)/8;//剩余行数
                    int c=(signal.getDataLength()-emptyBit)%8;//最后一行的个数
                    tempMaxRow=startRow+row;
                    data[startRow]=strLeft+x2.substring(0, emptyBit);
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

        //bo信息中有DLC 应该按照这个设置长度
        Message mse= BORead.readBO(BO_id,fileName);
        numDD=mse.getDLC();

        for(int j=0;j<numDD;j++) {
            DD=DD+binaryString2hexString(data[j]);
        }
        Log.i("tag",DD);
        String T_type="t";
        String id="";
        if(Long.parseLong(BO_id)>Integer.MAX_VALUE) {
            T_type="T";
            id=""+decimalToHex(Long.parseLong(BO_id));

        }else{
            id=""+decimalToHex(Integer.parseInt(BO_id));
            while(id.length()<3){
                id="0"+id;
            }
        }
        //这里需要进行转换
        //T_type的判断也需要重写

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
            tmp.append(decimalToHex(iTmp));
        }
        String str=tmp.toString();
        while(str.length()<2){
            str="0"+str;
        }
        return str;
    }
    public static String decimalToHex(long decimal) {
        String hex = "";
        while(decimal != 0) {
            long hexValue = decimal % 16;
            hex = toHexChar(hexValue) + hex;
            decimal = decimal / 16;
        }
        return  hex;
    }
    //将0~15的十进制数转换成0~F的十六进制数
    public static char toHexChar(long hexValue) {
        if(hexValue <= 9 && hexValue >= 0)
            return (char)(hexValue + '0');
        else
            return (char)(hexValue - 10 + 'A');
    }
}
