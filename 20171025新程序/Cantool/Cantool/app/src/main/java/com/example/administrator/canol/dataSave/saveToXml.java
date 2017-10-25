package com.example.administrator.canol.dataSave;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import com.example.administrator.canol.R;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;

/*把CanMsgValue对象保存到xml文件*/
public class saveToXml {
	// 原来的 public CanMsgValue canmsgvalue;

	public Message BO_Mse;
	public ArrayList<Signal> Signals=new ArrayList<>();

	public static void save(Message BO_Mse,ArrayList<Signal> Signals){



		BO_Mse=BO_Mse;
		//  原来的signlist   List<SignalValue> SigValueList;
		Signals=Signals;
		String id;
		String name;
		int DLC;
		String Sep;//即为发送的Node名称
		String Node;
		String  Bo;
		id=BO_Mse.getId();
		name=BO_Mse.getMessageName();
		DLC=BO_Mse.getDLC();
		Sep=BO_Mse.getSeporator();
		Node=BO_Mse.getNodeName();
		Bo=BO_Mse.getBO_();


//		name=canmsgvalue.getName();
//		DLC=canmsgvalue.getDLC();
//		Dir=canmsgvalue.getDir();
//		Data=canmsgvalue.getData();
//		sigValueNum=canmsgvalue.getSigValueNum();
//		SigValueList=canmsgvalue.getSigValueList();
		DocumentBuilderFactory  fct=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder bui=fct.newDocumentBuilder();
			Document doc=bui.newDocument();
			Element cv=doc.createElement("canmsgvalues");






			Element c1=doc.createElement("canmsgvalue");
			Attr id1=doc.createAttribute("id");
//	            Attr id2=doc.createAttribute("id");
			id1.setNodeValue(id);
//	            id2.setNodeValue("2");
			Element id2=doc.createElement("id");
			Text i2=doc.createTextNode(id);
			Element name1=doc.createElement("name");
			Text na1=doc.createTextNode(name);
			Element dlc1=doc.createElement("DLC");
			Text dl1=doc.createTextNode( String.valueOf(DLC));
//	            Element sex2=doc.createElement("sex");
//	            Text se2=doc.createTextNode("妹子");
			Element dir1=doc.createElement("Sep");
			Text di1=doc.createTextNode(Sep);
			Element date1=doc.createElement("Node");
			Text da1=doc.createTextNode(Node);
			Element sig1=doc.createElement("Bo");
			Text si1=doc.createTextNode(Bo);
			Element sv=doc.createElement("SignalValues");
			doc.appendChild(cv);
			cv.appendChild(c1);
			c1.appendChild(id2);
			c1.setAttributeNode(id1);
			id2.appendChild(i2);
			c1.appendChild(name1);
			name1.appendChild(na1);
			c1.appendChild(dlc1);
			dlc1.appendChild(dl1);
			c1.appendChild(dir1);
			dir1.appendChild(di1);
			c1.appendChild(date1);
			date1.appendChild(da1);
			c1.appendChild(sig1);
			sig1.appendChild(si1);
			c1.appendChild(sv);

			for(int i=0;i<Signals.size();i++){

				// 原来的累SignalValue sig=SigValueList.get(i);
				Signal sig=Signals.get(i);
				String sid=String.valueOf(i+1);
				//上边是id正式从此开始
				 String SG_=sig.getSG_();
				 String SignalName=sig.getSignalName();
				 String seporator=sig.getSeporator();
				 int startBit=sig.getStartBit();
				 int dataLength=sig.getDataLength();
				 String arrangeType=sig.getArrangeType();
				 double A=sig.getA();
				 double B=sig.getB();
				 double C=sig.getC();
				 double D=sig.getD();
				 String unit=sig.getUnit();
				 String NodeName=sig.getNodeName();



				Element s1=doc.createElement("signalvalue");
				Attr sid1=doc.createAttribute("id");
				sid1.setNodeValue(String.valueOf(i+1));
				Element sg_1=doc.createElement("SG_");
				Text sg1=doc.createTextNode(SG_);
				Element signalName1=doc.createElement("SignalName");
				Text sn1=doc.createTextNode(SignalName);
				Element sepor1=doc.createElement("seporator");
				Text sep1=doc.createTextNode(seporator);
				Element startBit1=doc.createElement("startBit");
				Text stB1=doc.createTextNode(String.valueOf(startBit));
				Element dataLength1=doc.createElement("dataLength");
				Text data1=doc.createTextNode(String.valueOf(dataLength));
				Element arr1=doc.createElement("arrangeType");
				Text ar1=doc.createTextNode(arrangeType);
				Element aa1=doc.createElement("A");
				Text a1=doc.createTextNode(String.valueOf(A));
				Element bb1=doc.createElement("B");
				Text b1=doc.createTextNode(String.valueOf(B));
				//重点是 因为有重复所以把c这里C的命名给改了
				Element ccc1=doc.createElement("C");
				Text cc1=doc.createTextNode(String.valueOf(C));
				Element dd1=doc.createElement("D");
				Text d1=doc.createTextNode(String.valueOf(D));
				//哈哈 这个有呀
				Element unit1=doc.createElement("unit");
				Text un1=doc.createTextNode(unit);
				Element nodename1=doc.createElement("Nodename");
				Text no1=doc.createTextNode(NodeName);



				sv.appendChild(s1);
				s1.appendChild(signalName1);
				s1.setAttributeNode(sid1);
				signalName1.appendChild(sn1);
				s1.appendChild(sg_1);
				sg_1.appendChild(sg1);
				s1.appendChild(sepor1);
				sepor1.appendChild(sep1);
				s1.appendChild(startBit1);
				startBit1.appendChild(stB1);
				s1.appendChild(dataLength1);
				dataLength1.appendChild(data1);
				s1.appendChild(arr1);
				arr1.appendChild(ar1);
				s1.appendChild(aa1);
				aa1.appendChild(a1);
				s1.appendChild(bb1);
				bb1.appendChild(b1);
				s1.appendChild(cc1);
				cc1.appendChild(c1);
				s1.appendChild(dd1);
				dd1.appendChild(d1);
				s1.appendChild(unit1);
				unit1.appendChild(un1);
				s1.appendChild(nodename1);
				nodename1.appendChild(no1);

			}
//

//	            ps.appendChild(p2);
//	                    p2.appendChild(name2);
//	                        p2.setAttributeNode(id2);
//	                        name2.appendChild(na2);
//	                    p2.appendChild(sex2);
//	                        sex2.appendChild(se2);

			try {
				//目录应该怎么写

				FileOutputStream fos=new FileOutputStream(new File("data/data/com.example.dataSave/longdada.xml"));

				try {
					((org.apache.crimson.tree.XmlDocument)doc)
							.write(fos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					fos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}





		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}







	}

}