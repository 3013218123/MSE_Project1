package com.example.administrator.canol.parse;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.administrator.canol.dataRead.BORead;
import com.example.administrator.canol.dataRead.SGRead;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;

/**
 * Created by wenhao on 2017/10/18.
 */
//王而川接收消息调用这个函数
public class Parse {
    public static ParseData parse(String dataStr,String fileName){
        String type=dataStr.substring(0,1);//传来的数据类型
        String id="";
        String totalDD="";
        int numOfDD=0;
        if(type.equals("T")){//T是扩展型
            id=dataStr.substring(1,9);
            /*
            id转换
             */
            id=""+Long.parseLong(id,16);
            numOfDD=Integer.parseInt(dataStr.substring(9,10));
            totalDD=dataStr.substring(10,10+numOfDD*2);

        }else if(type.equals("t")){//t是标准型
            id=dataStr.substring(1,4);
             /*
            id转换
             */
             id=""+ Integer.parseInt(id,16);
            Log.i("tag",id);
            numOfDD=Integer.parseInt(dataStr.substring(4,5));
            totalDD=dataStr.substring(5,5+numOfDD*2);
        }
        String dataMatric[]=new String[numOfDD];
        String tempTotalDD=totalDD;
        for(int i=0;i<numOfDD;i++){
            dataMatric[i]=hexString2binaryString(tempTotalDD.substring(0,2));
            tempTotalDD=tempTotalDD.substring(2,tempTotalDD.length());
        }
        Message BO_Mse= BORead.readBO(id,fileName);
        ArrayList<Signal> Signals= SGRead.readSG(id,fileName);
        double phyArray[]=new double[Signals.size()];
        int index=0;
        for(Iterator it = Signals.iterator(); it.hasNext(); )
        {
            Signal  signal= (Signal) it.next();
            int x=Integer.parseInt(analysis( signal.getStartBit(),signal.getDataLength(),dataMatric,signal.getArrangeType() ),2);//该信号的物理值
            phyArray[index]=signal.getA()*x+signal.getB();
            //Log.i("tag",signal.getA()*x+signal.getB()+"");
            index++;
        }
        ParseData pd=new ParseData(BO_Mse,Signals,phyArray);
        return pd;
    }

    public static String hexString2binaryString(String hexString)
    {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++)
        {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(hexString
                    .substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    public static String analysis(int startBit, int dataL, String [] dataMatric, String type) {

        if(type.equals("1+")) {//intel型
            int startRow=startBit/8;
            int startColumn=startBit%8;
            int emptyBit=8-startColumn;
            if(emptyBit>=dataL) { //只需要在该行显示
                String ss=dataMatric[startRow].substring(7-(startColumn+dataL-1)%8, 8-startColumn);
                return ss;
            }
            else {
                int row=(dataL-emptyBit)/8;
                int c=(dataL-emptyBit)%8;

                String ss=dataMatric[startRow].substring(0, 8-startBit%8);
                for(int i=1;i<=row;i++) {
                    ss=dataMatric[startRow+i]+ss;
                }
                if(c!=0) {
                    ss=dataMatric[startRow+row+1].substring(8-c, 8)+ss;
                }
                return ss;

            }
        }else if(type.equals("0+")){//motorola型
            int startRow=startBit/8;
            int startColumn=startBit%8;
            int emptyBit=1+startColumn;
            if(emptyBit>=dataL) { //只需要在该行显示
                String ss=dataMatric[startRow].substring(7-startColumn, dataL+7-startColumn);
                return ss;
            }
            else {
                int row=(dataL-emptyBit)/8;
                int c=(dataL-emptyBit)%8;

                String ss=dataMatric[startRow].substring(7-startColumn, 8);
                for(int i=1;i<=row;i++) {
                    ss=ss+dataMatric[startRow+i];
                }
                if(c!=0) {
                    ss=ss+dataMatric[startRow+row+1].substring(0, c);
                }
                return ss;

            }
        }else {// 需要提示的情况
            return "";
        }

    }
}
