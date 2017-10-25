package com.example.administrator.canol.dataSave;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
/*��can��Ϣ����Ϊcsv�ļ�*/
public class canToCsv {
	public static void saveToCsv(String str){
		 try {  
			    String canStr=str;
	            //File csv = new File("E://canMessage.csv"); // CSV�ļ�  
			    File csv = new File("data/data/com.example.dataSave/canMessage.csv"); 
	            // ׷��ģʽ  
	            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));  
	            // ����һ������  
	            bw.newLine();  
	            //bw.write("��ǹ�İ�����" + "," + "2009" + "," + "1212");  
	            bw.write(canStr); 
	            bw.write(","); 
	            bw.close();  
	        } catch (FileNotFoundException e) {  
	            // ����File��������ʱ���쳣  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            // ����BufferedWriter����ر�ʱ���쳣  
	            e.printStackTrace();  
	        }  
	}
	/*public static void main(String[] args) { 
		String strtest = "��ǹ�İ�����";
		saveToCsv(strtest);
	}*/

}
