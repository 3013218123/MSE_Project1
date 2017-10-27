package com.example.administrator.canol.dataRead;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.Signal;

/**
 * Created by wenhao on 2017/10/23.
 */

public class BOReadAll {
    public static ArrayList<Message> readBoAll(String fileName){
        ArrayList<Message> messageArrayList=new ArrayList<>();
        //File file = new File("/data/data/db/"+fileName);//虚拟机调试
        File file = new File("storage/sdcard1/db/"+fileName);//真机调试
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                Pattern p=Pattern.compile("BO_ "+".*");
                Matcher m=p.matcher(s);
                if(m.matches()) {//找到了一条BO
                    String [] BO_Array=s.split(" ");
                    String BO_id=BO_Array[1];
                    String MessageName=BO_Array[2].substring(0,BO_Array[2].length()-1);
                    int DLC=Integer.parseInt(BO_Array[3]);
                    String NodeName=BO_Array[4];
                    boolean isTypeTrue=true;
                    ArrayList<Signal> signalArrayList=SGRead.readSG(BO_id,fileName);
                    if(signalArrayList.size()<1) isTypeTrue=false;
                    for(Iterator it = signalArrayList.iterator(); it.hasNext(); )
                    {
                        Signal  signal= (Signal) it.next();
                       if(!(signal.getArrangeType().equals("0+")||signal.getArrangeType().equals("1+"))){
                           isTypeTrue=false;
                       }
                    }
                    if(isTypeTrue){
                        Message mse=new Message(BO_id,MessageName,DLC,NodeName);
                        messageArrayList.add(mse);
                    }

                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return messageArrayList;
    }
}
