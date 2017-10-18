package dataRead;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.File;
import entity.Message;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenhao on 2017/10/17.
 */

public class BORead {
    public  static Message readBO(String BO_id, String fileName){


        File file = new File("/data/data/db/"+fileName);
        // Log.i("tag",String.valueOf(file.exists()));
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件

           // Log.i("tag",String.valueOf(file.available()));
            String s = null;

            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                //Log.i("tag",s);
                Pattern p=Pattern.compile("BO_ "+BO_id+".*");
                Matcher m=p.matcher(s);
                if(m.matches()) {
                    //Log.i("tag","匹配了"+s);
                    String [] BO_Array=s.split(" ");
                    String MessageName=BO_Array[2].substring(0,BO_Array[2].length()-1);
                    int DLC=Integer.parseInt(BO_Array[3]);
                    String NodeName=BO_Array[4];
                    Message mse=new Message(BO_id,MessageName,DLC,NodeName);
                    return  mse;
                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        Message mse=new Message(BO_id,"",0,"");
        return mse;
    }
}
