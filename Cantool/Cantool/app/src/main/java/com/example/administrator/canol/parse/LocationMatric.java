package com.example.administrator.canol.parse;

import java.util.ArrayList;

import com.example.administrator.canol.dataRead.SGRead;
import com.example.administrator.canol.entity.Signal;

/**
 * Created by wenhao on 2017/10/25.
 */

public class LocationMatric {
    public static int [][]  returnColorMatric(String BO_id,String fileName){
        int [][] coloMatric=new int[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                coloMatric[i][j]=0;
            }
        }
        ArrayList<Signal> signalArrayList= SGRead.readSG(BO_id,fileName);
        for(int k=0;k<signalArrayList.size();k++){
            Signal signal=signalArrayList.get(k);
            int startRow=signal.getStartBit()/8;
            int startColumn=signal.getStartBit()%8;
            if(signal.getArrangeType().equals("1+")){
                int emptyBit=8-startColumn;
                if(emptyBit>=signal.getDataLength()) { //只需要在该行显示
                    for(int i=startColumn;i<signal.getDataLength();i++){
                        coloMatric[startRow][i]=(k+1);
                    }
                }else{//多行显示
                    int row=(signal.getDataLength()-emptyBit)/8;
                    int c=(signal.getDataLength()-emptyBit)%8;
                    for(int i=startColumn;i<8;i++){ //第一行开始位的左边全变色
                        coloMatric[startRow][i]=(k+1);
                    }
                    for(int i=1;i<=row;i++){
                        for(int j=0;j<8;j++){
                            coloMatric[startRow+i][j]=(k+1);
                        }
                    }
                    if(c!=0){
                        for(int i=0;i<c;i++){
                            coloMatric[startRow+row+1][i]=(k+1);
                        }
                    }
                }
            }else if(signal.getArrangeType().equals("0+")){
                int emptyBit=1+startColumn;
                if(emptyBit>=signal.getDataLength()){ //只在一行显示
                    for(int i=startColumn;i>startColumn-signal.getDataLength();i--){
                        coloMatric[startRow][i]=(k+1);
                    }
                }else{//需要多行显示
                    int row=(signal.getDataLength()-emptyBit)/8;
                    int c=(signal.getDataLength()-emptyBit)%8;
                    for(int i=startColumn;i>=0;i--){
                        coloMatric[startRow][i]=(k+1);
                    }
                    for(int i=1;i<=row;i++){
                        for(int j=0;j<8;j++){
                            coloMatric[startRow+i][j]=(k+1);
                        }
                    }
                    if(c!=0){
                        for(int i=7;i>7-c;i--){
                            coloMatric[startRow+row+1][i]=(k+1);
                        }
                    }
                }
            }
        }
        return coloMatric;
    }
}
