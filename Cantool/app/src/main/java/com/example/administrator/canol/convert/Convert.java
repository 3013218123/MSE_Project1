package com.example.administrator.canol.convert;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Created by wenhao on 2017/10/29.
 */

public class Convert {

//    public  static boolean dbcToXml(String sourFileName, String newFileName) {
//
//        File file = new File("storage/sdcard1/db/" + sourFileName);//真机调试
//        try {
//            DocumentBuilderFactory fct = DocumentBuilderFactory.newInstance();
//            DocumentBuilder bui = fct.newDocumentBuilder();
//            Document doc = bui.newDocument();
//
//            Element fname = doc.createElement(sourFileName);
//            doc.appendChild(fname);
//
//            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
//            String s = null;
//            String result = "";
//            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
//                Pattern p = Pattern.compile("BO_ " + ".*");
//                Matcher m = p.matcher(s);
//                if (m.matches()) {//找到了一条BO
//                    //现在s是boString
//                    String boString = s;
//
//                    Element boo = doc.createElement("BO_item");
//                    Attr bname = doc.createAttribute("BO");
//                    bname.setNodeValue(boString);
//                    fname.appendChild(boo);
//                    boo.setAttributeNode(bname);
//
//                    while ((s = br.readLine()) != null && (!s.matches("BO.*"))) {
//                        if (s.length() > 3) {
//                            String t = s.substring(0, 3);
//                            if (t.equals(" SG")) {
//                                //现在s是sgString
//
//                                Element sgg = doc.createElement("SG_item");
//                                Attr sname = doc.createAttribute("SG");
//                                sname.setNodeValue(t);
//                                boo.appendChild(sgg);
//                                sgg.setAttributeNode(sname);
//
//                            }
//                        }
//                    }
//                }
//            }
//            try {
//                FileOutputStream fos = new FileOutputStream(new File(newFileName));
//                try {
//                    ((org.apache.crimson.tree.XmlDocument) doc).write(fos);
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                try {
//                    fos.flush();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    public static boolean dbcToJson(String sourFileName, String newFileName){
        ArrayList<BEAN> BEANArrylist=new ArrayList<>();
        File file = new File("storage/sdcard1/db/"+sourFileName);//真机调试
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            String result="";
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                Pattern p=Pattern.compile("BO_ "+".*");
                Matcher m=p.matcher(s);
                if(m.matches()) {//找到了一条BO
                   //现在s是boString
                    String boString=s;
                    ArrayList<String> sgStringArray=new ArrayList<>();
                    while((s = br.readLine())!=null&&(!s.matches("BO.*"))) {
                        if(s.length()>3) {
                            String t=s.substring(0,3);
                            if(t.equals(" SG")) {
                                //现在s是sgString
                                sgStringArray.add(s);
                            }
                        }
                    }
                    BEAN bean=new BEAN(boString,sgStringArray);
                    BEANArrylist.add(bean);


                }
            }
            Gson g=new Gson();
            result=g.toJson(BEANArrylist);
            File file2 = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "Json"
                    + File.separator + newFileName);
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
            pStream.print(result);
            pStream.close();
            br.close();

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean jsonTodbc(String sourFileName, String newFileName){
        String path="storage/sdcard1/Json/"+sourFileName;
        try{
            String content=readToString(path);
            Type type = new TypeToken<List<BEAN>>(){}.getType();
            Gson g=new Gson();
            List<BEAN> BEANArray=g.fromJson(content,type);
            File file=new File("storage/sdcard1/db/"+newFileName);
            StringBuilder mStringBuilder = null;
            mStringBuilder = new StringBuilder();
            for(int i=0;i<BEANArray.size();i++){
               BEAN bean=BEANArray.get(i);
                String boString=bean.getBoString();
                ArrayList<String> sgStringArraylist=bean.getSgStringArraylist();
                mStringBuilder.append(boString+"\n");
                for(int j=0;j<sgStringArraylist.size();j++){
                    mStringBuilder.append(sgStringArraylist.get(j)+"\n");
                }
            }
            FileOutputStream fos = new FileOutputStream(file, false);
            fos.write(mStringBuilder.toString().getBytes());
            fos.flush();
            fos.close();

        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static String readToString(String fileName) {
        String encoding = "ISO-8859-1";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

}
