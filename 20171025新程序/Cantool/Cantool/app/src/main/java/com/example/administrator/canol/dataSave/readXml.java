package com.example.administrator.canol.dataSave;



import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;



public class readXml {

   public  ParseData parseData;
   public static ParseData xmlToCanMsgValue(){
       Message message1=null;
//       Message message ;
       ArrayList<Signal> Signals=new ArrayList<>();
       SAXReader reader = new SAXReader();
       try {
           // 通过reader对象的read方法加载xml文件,获取docuemnt对象。
           Document document = reader.read(new File("data/data/com.example.dataSave/longdada.xml"));
           // 通过document对象获取根节点canmsgvalues
           Element canmsgvalues = document.getRootElement();
           // 通过element对象的elementIterator方法获取迭代器
           Iterator it = canmsgvalues.elementIterator();
           // 遍历迭代器，获取根的子节点中的信息（canmsgvalue）canmsgvalue
           while (it.hasNext()) {
               // System.out.println("=====开始遍历某个canmsgvalue=====");
               Element canmsgvalue = (Element) it.next();
               // 获取canmsgvalue的属性名以及 属性值,赋值给canmsgvalue1的id字段
               List<Attribute> canmsgAttrs = canmsgvalue.attributes();
               for (Attribute attr : canmsgAttrs) {
                   //System.out.println("属性名：" + attr.getName() + "--属性值：" + attr.getValue());
                    String id=attr.getValue();
//                   message.setId(attr.getValue());
               }
               String id=canmsgvalue.elementText("id");
               String MessageName=canmsgvalue.elementText("name");
               int DLC=canmsgvalue.elementText("DLC").charAt(0);
               String seporator=canmsgvalue.elementText("Sep");
               String NodeName=canmsgvalue.elementText("Node");
               String BO_=canmsgvalue.elementText("Bo");
               //建立message
             message1=new Message(id, MessageName, DLC, NodeName);


               // 根据节点名获取canmsgvalue的子节点值 ,并赋值给canmsgvalue1
//               canmsgvalue1.setName(canmsgvalue.elementText("name"));
//               canmsgvalue1.setDLC(canmsgvalue.elementText("DLC").charAt(0));
//               canmsgvalue1.setDir(canmsgvalue.elementText("Dir"));
//               canmsgvalue1.setData(canmsgvalue.elementText("Date"));
//               canmsgvalue1.setSigValueNum(Integer.parseInt(canmsgvalue.elementText("sigValueNum")));

               //获取SignalValues标签
               Element signals =canmsgvalue.element("SignalValues");
               // Element signalx =signals.element("SignalValue");
               //获取所有SignalValue标签，放入signalList中
               List<Element> signalList =signals.elements();
               //循环把signalValue的子标签名对应的值，赋值给signalValue1对象的字段
               Element signalx;
               for(int i=0;i<signalList.size();i++){
                   signalx =signalList.get(i);
                   //SignalValue signalValue1;

                   String SG_ =signalx.elementText("SG_");
                   String SignalName=signalx.elementText("SignalName");
                   //sign信息的sep命名需要注意
                   String seporato=signalx.elementText("seporator");
                   int startBit=signalx.elementText("startBit").charAt(0);
                   int dataLength=signalx.elementText("dataLength").charAt(0);
                   String arrangeType=signalx.elementText("arrangeType");
                   //这里错了？？
                   double A=signalx.elementText("A").charAt(0);
                   double B=signalx.elementText("B").charAt(0);
                   double C=signalx.elementText("C").charAt(0);
                   double D=signalx.elementText("D").charAt(0);
                   String unit=signalx.elementText("unit");
                   String Nodename=signalx.elementText("Nodename");
                   double phy=signalx.elementText("phy").charAt(0);
                   //  初始化

                   Signal signal=new Signal( SignalName, startBit, dataLength, arrangeType, A, B, C, D, unit, NodeName);
                   signals.add(signals);


//for循环
           }

               //while循环
       }

       //try
       }catch (DocumentException e) {
           e.printStackTrace();
       }
       double[] doubles=null;
       ParseData parseData=new ParseData(message1,Signals,doubles);
       //返回两个信息
       return parseData;
   }
}
