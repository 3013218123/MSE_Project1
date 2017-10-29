package com.example.administrator.canol.convert;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import org.kxml2.io.KXmlParser;

/**
 * Created by wenhao on 2017/10/29.
 */

public class XmlConvrt {

//    public static boolean xmlToDbc(String sourFileName, String newFileName) {
//        String s = "";
////        SAXReader reader = new SAXReader();
//        try {
//            KXmlSerializer serializer = new KXmlSerializer();
//            StringWriter writer = new StringWriter();
//            serializer.setOutput(writer);
////将xml数据写入对象
//            header
//            header[0].write(serializer);
////设置自动缩进
//            //serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
//            s=writer.toString();
////            // 通过reader对象的read方法加载xml文件,获取docuemnt对象。
////            Document document = reader.read(new File(sourFileName));
////            // 通过document对象获取根节点
////            Element dbname = document.getRootElement();
////            // 通过element对象的elementIterator方法获取迭代器
////            Iterator it = dbname.elementIterator();
////            // 遍历迭代器，获取根节点中的信息(BO)
////            while (it.hasNext()) {
////                Element boname = (Element) it.next();
////                // 获取bo
////                List<Attribute> bookAttrs = boname.attributes();
////                for (Attribute attr : bookAttrs) {
////                    s = s + attr.getValue() + "\n";
////                }
////                //解析子BO中节点SG的信息
////                Iterator itt = boname.elementIterator();
////                while (itt.hasNext()) {
////                    Element sgname = (Element) itt.next();
////                    //获取SG
////                    s = s + " " + sgname.getStringValue() + "  " + "\n";
////                }
////            }
//            File file2 = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "xml"
//                    + File.separator + newFileName);
//            if (!file2.getParentFile().exists()) {
//                file2.getParentFile().mkdirs();// 没有就创建文件
//            }
//            //写入操作
//            PrintStream pStream = null;
//            try {
//                pStream = new PrintStream(new FileOutputStream(file2));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                return false;
//            }
//            pStream.print(s);
//            pStream.close();
//
//        } catch (DocumentException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
}
