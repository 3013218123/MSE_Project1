import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Initial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 File file = new File("D:/eclipseEE/eclipse-workspace/CANTool/src/db0.txt");
	    //System.out.println(txt2String(file,"100").toString());
		 ArrayList<String> SG_list=txt2String(file,"100");
		 for(String str:SG_list) {
			 System.out.println(str);
		 }
	}
	public static ArrayList<String> txt2String(File file,String id){
		ArrayList<String> SG_list = new ArrayList<>(); 
        //StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                //result.append(System.lineSeparator()+s);
            	
            	Pattern p=Pattern.compile("BO_ "+id+".*");
            	Matcher m=p.matcher(s);
	           if(m.matches()) {
	        	   while((s = br.readLine())!=null&&(!s.matches("BO.*"))) {
	        		   if(s.length()>3) {
	        			   String t=s.substring(0,3);
	 	        		  if(t.equals(" SG")) {
	 	        			  SG_list.add(s);
	 	        		  }
	        		   }
	        		 
	        		  
	        	   }
	           }
            		
            
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return SG_list;
    }

}
