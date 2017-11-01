import java.io.ByteArrayInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.util.HashMap;  
import java.util.Map;  
  
import org.kxml2.io.KXmlParser;  
import org.xmlpull.v1.XmlPullParserException; 
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.kxml2.io.KXmlSerializer;
import java.io.StringWriter;
import org.xmlpull.v1.XmlSerializer;

public class Test_Demo_1 
{
	    public static void main(String[] args) throws IllegalArgumentException, IllegalStateException, IOException {  
	    	Element[] header = new Element[1];
	    	//�����µ�Ԫ�ؽڵ�,����1:�����ռ�,����2:�ڵ���
	    	header[0] = new Element().createElement(null, "head");
	    	//Ϊ�ڵ���������,����1:�����ռ�,����2:������,����3:����ֵ
	    	header[0].setAttribute(null,"xmlns", "http://testxmlns");
	    	Element UsernameToken = new Element().createElement(null, "UsernameToken"); 
	    	Element userName = new Element().createElement(null, "Username");
	    	//����ӽڵ�,����1:�ڵ�����,����2:�ڵ����
	    	//Node.TEXT:�ı��ڵ�,Node.ELEMENT:Ԫ�ؽڵ�
	    	userName.addChild(Node.TEXT, "user123");
	    	Element passWord = new Element().createElement(null, "Password"); 
	    	passWord.addChild(Node.TEXT, "pass123");
	    	passWord.setAttribute(null,"Type", "http://testtype");
	    	UsernameToken.addChild(Node.ELEMENT, userName);
	    	UsernameToken.addChild(Node.ELEMENT, passWord);
	    	header[0].addChild(Node.ELEMENT, UsernameToken);
	    	
	    	XmlSerializer serializer = new KXmlSerializer();
	    	StringWriter writer = new StringWriter();
	    	serializer.setOutput(writer);
	    	//��xml����д�����
	    	header[0].write(serializer);
	    	//�����Զ�����
	    	serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
	    	System.out.println(writer.toString());
	    		
	    }  
}
	
