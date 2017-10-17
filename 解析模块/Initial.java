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
		//System.out.println(t+" "+Integer.parseInt(t,2));//2ת10
		//System.out.println(t+" "+binaryString2hexString(t));
		
		 File file = new File("D:/eclipseEE/eclipse-workspace/CANTool/src/db0.txt");
	    //System.out.println(txt2String(file,"100").toString());
		 ArrayList<String> SG_list=txt2String(file,"100");
		 int SG_length=SG_list.size();//SG����
		 String SG_name[]=new String [SG_length];//����SG����
		 double SG_phy[]=new double[SG_length];//��������ֵ
		 double SG_phy_min[]=new double[SG_length];//��������ֵ�½�
		 double SG_phy_max[]=new double[SG_length];//��������ֵ�Ͻ�
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
			 System.out.println(SG_name[i]+" ����ֵ��"+SG_phy[i]+" ��Χ��"+SG_phy_min[i]+" "+SG_phy_max[i]+" ��λ��"+SG_unit[i]);
		 }
		 
		 //������� �û�ѡ��can��Ϣ�����ȷ�����Ҫ���������ֵ�ĸ��� 
		 //�û�����Ҫ֪��ÿ������ֵ�����뷶Χ
		 double minMax[]=minMaxOfSG("100","D:/eclipseEE/eclipse-workspace/CANTool/src/db0.txt");
		 int sLength=minMax.length/2;//��Ҫ���������ֵ�ĸ���
		 for(int k=0;k<minMax.length;k++) {
			 System.out.println(minMax[k]); //����ǰsLengthλ���½磬��sLengthλ���Ͻ�
		 }
		//�����û���������ֵ������һ��double����,���canId100
		 double userInputPhy[]=new double[] {254,653.31,65484,0,52,65484};
		 System.out.println(ReverseParse(userInputPhy,"100","D:/eclipseEE/eclipse-workspace/CANTool/src/db0.txt"));
		//����
		 String str="11001100"+"11111111"+"00110011"+"11111110"+"11001100"+
					"11111111"+"00110011"+"11111111";
		 System.out.println(binaryString2hexString(str));
		 
		 
	}
	public static ArrayList<String> txt2String(File file,String id){
		ArrayList<String> SG_list = new ArrayList<>(); 
        //StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
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
		
		if(type.equals("1+")) {//intel��
			int startRow=startBit/8;
			int startColumn=startBit%8;
			int emptyBit=8-startColumn;
			if(emptyBit>=dataL) { //ֻ��Ҫ�ڸ�����ʾ
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
		}else if(type.equals("0+")){//motorola��
			int startRow=startBit/8;
			int startColumn=startBit%16;
			int emptyBit=8-startColumn;
			if(emptyBit>=dataL) { //ֻ��Ҫ�ڸ�����ʾ
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
		}else {// ��Ҫ��ʾ�����
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
	
	public static double[] minMaxOfSG(String canId,String fileLocation) {
		File file = new File(fileLocation);
		ArrayList<String> SG_list=txt2String(file,canId);
		int SG_length=SG_list.size();
		double SG_phy_minmax[]=new double[SG_length*2];//��������ֵ�½�
		int index=0;
		for(String str:SG_list) {
			String[] sArray=str.split(" "); 
			String minMaxStr=sArray[6].substring(1, sArray[6].length()-1);
			String[] minMaxArray=minMaxStr.split("\\|");
			SG_phy_minmax[index]=Double.parseDouble(minMaxArray[0]);
			SG_phy_minmax[index+SG_length]=Double.parseDouble(minMaxArray[1]);
			index++;
		 }
		return SG_phy_minmax;
		
	}
	
	public static String ReverseParse(double [] userInputPhy,String canId,String fileLocation) {
		File file = new File(fileLocation);
		ArrayList<String> SG_list=txt2String(file,canId);
		int SG_length=SG_list.size();	
		 double SG_x[]=new double[SG_length];//����xֵ
		 int index=0;
		//���ȳ�ʼ��һ��ȫ0��data���飬������������
			String data[] = new String [] {"00000000","00000000","00000000","00000000",
					"00000000","00000000","00000000","00000000"};
		//ȫ���������
			int maxRow=0;
		 for(String str:SG_list) {
			String[] sArray=str.split(" "); 
			String dataIndexInformation=sArray[4];
			String [] dataIndexInformationArray=dataIndexInformation.split("\\||@");
			int startBit=Integer.parseInt(dataIndexInformationArray[0]);
			int dataScale=Integer.parseInt(dataIndexInformationArray[1]);
			String type=dataIndexInformationArray[2];
			String ABstr=sArray[5].substring(1, sArray[5].length()-1);
			String[] ABarray=ABstr.split(",");
			double A=Double.parseDouble(ABarray[0]);
			double B=Double.parseDouble(ABarray[1]);
			//����һ����ʱ�����������ʹ�õ������
			int tempMaxRow=0;
			//������ݾ��󣬴Ӷ��õ�DD
			//1.�����ź���x,int��,x2��ʾ2�����ź���
			int x=(int)((userInputPhy[index]-B)/A);
			String x2=Integer.toBinaryString(x);
			for(int i=0;i<dataScale-x2.length();i++) x2="0"+x2;//����λ��
			//���ֻ��һ����ʾ�������ַ�ʽ��ͬ��һ��д
			int startRow=startBit/8;
			int startColumn=startBit%8;
			int emptyBit=8-startColumn;
			String strRight="",strLeft="";
			if(startColumn>0) strRight=data[startRow].substring(8-startColumn,8);
			if(emptyBit>=dataScale) { //ֻ��Ҫ�ڸ�����ʾ
				if(emptyBit>dataScale) data[startRow].substring(0, emptyBit-dataScale);
				x2=strLeft+x2+strRight;
				data[startRow]=x2;
				tempMaxRow=startRow;
			}else {//������ʾ��������
				if(type.equals("1+")) {//intel��
					 int row=(dataScale-emptyBit)/8;//ʣ������
					 int c=(dataScale-emptyBit)%8;//���һ�еĸ���
					 tempMaxRow=startRow+row;
					 data[startRow]=x2.substring(x2.length()-emptyBit, x2.length())+strRight;
					 x2=x2.substring(0,x2.length()-emptyBit);
						 for(int i=1;i<=row;i++) {
							 data[startRow+i]=x2.substring(x2.length()-8, x2.length());
							 x2=x2.substring(0,x2.length()-8);
						 }
						 if(c!=0) {
							 data[startRow+row+1]=data[startRow+row+1].substring(0, 8-c)+x2;
							 tempMaxRow++;
						 }
				}else if(type.equals("0+")) {//motorola��
					int row=(dataScale-emptyBit)/8;//ʣ������
					int c=(dataScale-emptyBit)%8;//���һ�еĸ���
					tempMaxRow=startRow+row;
					data[startRow]=x2.substring(0, emptyBit)+strRight;
					x2=x2.substring(emptyBit,x2.length());
						 for(int i=1;i<=row;i++) {
							 data[startRow+i]=x2.substring(0, 8);
							 x2=x2.substring(8,x2.length());
						 }
						 if(c!=0) {
							 data[startRow+row+1]=x2+data[startRow+row+1].substring(c, 8);
							 tempMaxRow++;
						 }
				}
			}
			
			if(tempMaxRow>maxRow) maxRow=tempMaxRow;
			
			
			index++;
		 }
		//��Ҫ֪��ʹ����ǰ�����ֽڣ����С��8��������ֽڲ�Ҫ��
		//ʹ����0-maxRow��
		 String DD="";
		 int numDD=maxRow+1;
		for(int j=0;j<numDD;j++) {
			DD=DD+binaryString2hexString(data[j]);
		}
		String T_type="";
		if(canId.length()==3) {
			T_type="t";
		}else{
			T_type="T";
		}
		String str=T_type+canId+numDD+DD;
		return str;
	}

}
