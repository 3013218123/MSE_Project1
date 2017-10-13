import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Initial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String data[]=new String[]  {"11001100","11111111","00110011","11111110","11001100",
				"11111111","00110011","11111111"};
		//String t=signal(17,8,data,"1+");
		//System.out.println(t+" "+Integer.parseInt(t,2));//2转10
		//System.out.println(t+" "+binaryString2hexString(t));
		
		 File file = new File("D:/eclipseEE/eclipse-workspace/CANTool/src/db0.txt");
	    //System.out.println(txt2String(file,"100").toString());
		 ArrayList<String> SG_list=txt2String(file,"100");
		 int SG_length=SG_list.size();//SG个数
		 String SG_name[]=new String [SG_length];//保存SG名字
		 double SG_phy[]=new double[SG_length];//保存物理值
		 double SG_phy_min[]=new double[SG_length];//保存物理值下界
		 double SG_phy_max[]=new double[SG_length];//保存物理值上界
		 String SG_unit[]=new String[SG_length];
		 int index=0;
		 for(String str:SG_list) {
			System.out.println(str);
			 String[] sArray=str.split(" "); 
			 SG_name[index]=sArray[2];
			 
			 /*
			 for(String i : sArray){
				 System.out.println(i);
				 
			 }
			*/
			String dataIndexInformation=sArray[4];
			String [] dataIndexInformationArray=dataIndexInformation.split("\\||@");
			int startBit=Integer.parseInt(dataIndexInformationArray[0]);
			int dataScale=Integer.parseInt(dataIndexInformationArray[1]);
			String type=dataIndexInformationArray[2];
			//System.out.println(startBit+" "+dataScale+" "+type);
			String ABstr=sArray[5].substring(1, sArray[5].length()-1);
			String[] ABarray=ABstr.split(",");
			double A=Double.parseDouble(ABarray[0]);
			double B=Double.parseDouble(ABarray[1]);
			int x=Integer.parseInt(signal(startBit,dataScale,data,type),2);
			double phy=A*x+B;
			SG_phy[index]=phy;
			
			String minMaxStr=sArray[6].substring(1, sArray[6].length()-1);
			String[] minMaxArray=minMaxStr.split("\\|");
			SG_phy_min[index]=Double.parseDouble(minMaxArray[0]);
			SG_phy_max[index]=Double.parseDouble(minMaxArray[1]);
			SG_unit[index]=sArray[7];
			
			
			index++;
		 }
		 for(int i=0;i<SG_length;i++) {
			 System.out.println(SG_name[i]+" 物理值："+SG_phy[i]+" 范围："+SG_phy_min[i]+" "+SG_phy_max[i]+" 单位："+SG_unit[i]);
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
	
	public static String signal(int startBit, int dataL, String [] dataMatric, String type) {
		
		if(type.equals("1+")) {//intel型
			int startRow=startBit/8;
			int startColumn=startBit%8;
			int emptyBit=8-startColumn;
			if(emptyBit>=dataL) { //只需要在该行显示
				String ss=dataMatric[startRow].substring(7-(startColumn+dataL-1)%8, 8-startColumn);
				return ss;
			}
			else {
				 int row=(dataL-emptyBit)/8;
				 int c=(dataL-emptyBit)%8;
				
					 String ss=dataMatric[startRow].substring(0, 8-startBit%8);
					 for(int i=1;i<=row;i++) {
						 ss=dataMatric[startRow+i]+ss;
					 }
					 if(c!=0) {
						 ss=dataMatric[startRow+row+1].substring(8-c, 8)+ss;
					 }
					 return ss;
				
			}
		}else if(type.equals("0+")){//motorola型
			int startRow=startBit/8;
			int startColumn=startBit%16;
			int emptyBit=8-startColumn;
			if(emptyBit>=dataL) { //只需要在该行显示
				String ss=dataMatric[startRow].substring(7-(startColumn+dataL-1)%8, 8-startColumn);
				return ss;
			}
			else {
				 int row=(dataL-emptyBit)/8;
				 int c=(dataL-emptyBit)%8;
				
					 String ss=dataMatric[startRow].substring(0, 8-startBit%8);
					 for(int i=1;i<=row;i++) {
						 ss=ss+dataMatric[startRow+i];
					 }
					 if(c!=0) {
						 ss=ss+dataMatric[startRow+row+1].substring(0, c);
					 }
					 return ss;
				
			}
		}else {// 需要提示的情况
			return "";
		}
		
	}
	public static String binaryString2hexString(String bString)
	{
		while(bString.length()%8!=0) {
			bString="0"+bString;
		}
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4)
		{
			iTmp = 0;
			for (int j = 0; j < 4; j++)
			{
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

}
