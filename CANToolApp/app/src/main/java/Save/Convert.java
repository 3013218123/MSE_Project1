package Save;

import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataRead.SGRead;
import entity.Message;
import entity.Signal;

/**
 * Created by wenhao on 2017/10/25.
 */

public class Convert {
    public static boolean convert(String soureFileName, String targetFileName,String type){
        //File file = new File("/data/data/db/"+sourceFileName);//虚拟机调试
        File file = new File("storage/sdcard1/db/"+soureFileName);//真机调试
        JSONArray BOJsonArray=new JSONArray();
        if(!file.exists()) return false;
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
                    ArrayList<Signal> signalArrayList= SGRead.readSG(BO_id,soureFileName);
                    Message mse=new Message(BO_id,MessageName,DLC,NodeName);
                    //json
                    JSONObject boJson=new JSONObject();
                    JSONArray SGJsonArray=new JSONArray();
                    for(int i=0;i<signalArrayList.size();i++){
                        JSONObject sgJson=new JSONObject();
                        Signal signal=signalArrayList.get(i);
                        sgJson.put("SG_",signal.getSG_());
                        sgJson.put("SignalName",signal.getSignalName());
                        sgJson.put("Seporator",signal.getSeporator());
                        sgJson.put("StartBit",signal.getStartBit());
                        sgJson.put("DataLength",signal.getDataLength());
                        sgJson.put("ArrangeType",signal.getArrangeType());
                        sgJson.put("A",signal.getA());
                        sgJson.put("B",signal.getB());
                        sgJson.put("C",signal.getC());
                        sgJson.put("D",signal.getD());
                        sgJson.put("Unit",signal.getUnit().substring(1,signal.getUnit().length()-1));
                        //Log.i("tag",signal.getUnit().substring(1,signal.getUnit().length()-1));
                        sgJson.put("NodeName",signal.getNodeName());
                        SGJsonArray.put(sgJson);
                    }
                    String boAllInf=mse.getBO_()+" "+mse.getId()+" "+mse.getMessageName()+mse.getSeporator()+" "+mse.getDLC()+" "+mse.getNodeName();
                    boJson.put(boAllInf,SGJsonArray);
                    BOJsonArray.put(boJson);


                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        File file2 = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "Json"
                + File.separator + targetFileName);
        if (!file2.getParentFile().exists()) {
            file2.getParentFile().mkdirs();// 没有就创建文件
        }
        //写入操作
        PrintStream pStream = null;
        try {
            pStream = new PrintStream(new FileOutputStream(file2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return  false;
        }
        pStream.print(BOJsonArray);
        pStream.close();

        return true;
    }

    public static void JsonToDbc(String sourceFileName,String targetFileName){
        //先获取文件，该文件为上文写入位置
        File file=new File("storage/sdcard1/Json/"+sourceFileName);
        try {
            JsonReader jsonReader=new JsonReader(new InputStreamReader(new FileInputStream(file)));


                  //打开bo数组
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                            //打开数组内的bo对象
                            jsonReader.beginObject();
                            Log.i("tag",jsonReader.nextName());
                            //打开sg数组
                            jsonReader.beginArray();
                            while (jsonReader.hasNext()){
                                //打开sg数组内的sg对象
                                jsonReader.beginObject();
                                while ((jsonReader.hasNext())){

                                if(jsonReader.nextName().equals("SG_")){
                                    Log.i("tag",jsonReader.nextString());
                                }
                                if(jsonReader.nextName().equals("SignalName")){
                                    Log.i("tag",jsonReader.nextString());
                                }
                                if(jsonReader.nextName().equals("Seporator")){
                                    Log.i("tag",jsonReader.nextString());
                                }
                                if(jsonReader.nextName().equals("StartBit")){
                                    Log.i("tag",String.valueOf(jsonReader.nextInt()));
                                }
                                if(jsonReader.nextName().equals("DataLength")){
                                    Log.i("tag",String.valueOf(jsonReader.nextInt()));
                                }
                                if(jsonReader.nextName().equals("ArrangeType")){
                                    Log.i("tag",jsonReader.nextString());
                                }
                                if(jsonReader.nextName().equals("A")){
                                    Log.i("tag",String.valueOf(jsonReader.nextDouble()));
                                }
                                if(jsonReader.nextName().equals("B")){
                                    Log.i("tag",String.valueOf(jsonReader.nextDouble()));
                                }
                                if(jsonReader.nextName().equals("C")){
                                    Log.i("tag",String.valueOf(jsonReader.nextDouble()));
                                }
                                if(jsonReader.nextName().equals("D")){
                                    Log.i("tag",String.valueOf(jsonReader.nextDouble()));
                                }
                                if(jsonReader.nextName().equals("Unit")){
                                    Log.i("tag",jsonReader.nextString());
                                }
                                if(jsonReader.nextName().equals("NodeName")){
                                    Log.i("tag",jsonReader.nextString());
                                }


                                }
                                jsonReader.endObject();

                            }
                            jsonReader.endArray();
                    }
            jsonReader.endObject();
            jsonReader.endArray();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
