package com.example.administrator.canol.Save;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;
import com.example.administrator.canol.parse.Parse;

/**
 * Created by wenhao on 2017/10/24.
 */

public class SaveCsv {
    public static final String mComma = ",";
    private static StringBuilder mStringBuilder = null;
    private static String mFileName = null;
    private static String newFileName=null;

    public static void open(String newFileName) {
        String folderName = null;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (path != null) {
                folderName = path +"/CSV/";
                Log.i("tag",folderName);
            }
        }

        File fileRobo = new File(folderName);
        if(!fileRobo.exists()){
            fileRobo.mkdir();
        }
        mFileName = folderName + newFileName;
        mStringBuilder = new StringBuilder();
        mStringBuilder.append("order");
        mStringBuilder.append(mComma);
        mStringBuilder.append("str");
        mStringBuilder.append(mComma);
        mStringBuilder.append("BO");
        mStringBuilder.append(mComma);
        mStringBuilder.append("SG");
        mStringBuilder.append(mComma);
        mStringBuilder.append("phy");
        mStringBuilder.append("\n");
    }

    public static void writeCsv(String value1, String value2, String value3, String value4,String value5) {
        mStringBuilder.append(value1);
        mStringBuilder.append(mComma);
        mStringBuilder.append(value2);
        mStringBuilder.append(mComma);
        mStringBuilder.append(value3);
        mStringBuilder.append(mComma);
        mStringBuilder.append(value4);
        mStringBuilder.append(mComma);
        mStringBuilder.append(value5);
        mStringBuilder.append("\n");
    }

    public static void flush() {
        if (mFileName != null) {
            try {
                File file = new File(mFileName);
                FileOutputStream fos = new FileOutputStream(file, false);
                fos.write(mStringBuilder.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("You should call open() before flush()");
        }
    }
    public static boolean writeAll(String fileNewName, List<String> dataStrArray, String dataBaseName){
        try{
            open(fileNewName);
            for(int i=0;i<dataStrArray.size();i++){
                ParseData pd = Parse.parse(dataStrArray.get(i),dataBaseName);
                Message mse=pd.getBO_Mse();
                ArrayList<Signal> signalArrayList=pd.getSignals();
                double [] phyArray=pd.getPhyArray();
                String boAllInf=mse.getBO_()+mse.getId()+mse.getMessageName()+mse.getSeporator()+mse.getDLC()+mse.getNodeName();
                writeCsv(""+(i+1),dataStrArray.get(i),boAllInf,"","");
                for(int k=0;k<signalArrayList.size();k++){
                    Signal signal=signalArrayList.get(k);

                    String nodeString=signal.getNodeName();
                    while(nodeString.indexOf(",")!=-1){
                        String left=nodeString.substring(0,nodeString.indexOf(","));
                        String right=nodeString.substring(nodeString.indexOf(",")+1,nodeString.length());
                        nodeString=left+" "+right;
                    }
                    Log.i("tag",nodeString);
                    String sgAllInf=signal.getSG_()+signal.getSignalName()+signal.getSeporator()+signal.getStartBit()+" "+signal.getDataLength()
                            +" "+signal.getArrangeType() +" "+signal.getA()+signal.getB()+signal.getC()+signal.getD()+signal.getUnit()+nodeString;

                    writeCsv(" "," "," ",sgAllInf,""+phyArray[k]);
                }
            }
            flush();
            return true;
        }catch (Exception e){
            return false;
        }

    }



}
