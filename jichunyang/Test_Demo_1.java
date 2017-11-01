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
	    	//创建新的元素节点,参数1:命名空间,参数2:节点命
	    	header[0] = new Element().createElement(null, "head");
	    	//为节点设置属性,参数1:命名空间,参数2:属性名,参数3:属性值
	    	header[0].setAttribute(null,"xmlns", "http://testxmlns");
	    	Element UsernameToken = new Element().createElement(null, "UsernameToken"); 
	    	Element userName = new Element().createElement(null, "Username");
	    	//添加子节点,参数1:节点类型,参数2:节点对象
	    	//Node.TEXT:文本节点,Node.ELEMENT:元素节点
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
	    	//将xml数据写入对象
	    	header[0].write(serializer);
	    	//设置自动缩进
	    	serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
	    	System.out.println(writer.toString());
	    		
	    }  
}
	
