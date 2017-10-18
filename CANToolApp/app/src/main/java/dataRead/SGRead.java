package dataRead;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.Message;
import entity.Signal;

/**
 * Created by wenhao on 2017/10/17.
 */

public class SGRead {
    public static ArrayList<Signal>readSG(String BO_id,String fileName){
        ArrayList<Signal> signalArrayList=new ArrayList<>();
        File file = new File("/data/data/db/"+fileName);
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                Pattern p=Pattern.compile("BO_ "+BO_id+".*");
                Matcher m=p.matcher(s);
                if(m.matches()) {
                    while((s = br.readLine())!=null&&(!s.matches("BO.*"))) {
                        if(s.length()>3) {
                            String t=s.substring(0,3);
                            if(t.equals(" SG")) {
                                String[] sArray=s.split(" ");
                                //String SignalName,int startBit,int dataLength,String arrangeType,double A,double B,double C,double D,String unit,String NodeName
                                String SignalName=sArray[2];
                                String dataIndexInformation=sArray[4];
                                String [] dataIndexInformationArray=dataIndexInformation.split("\\||@");
                                int startBit=Integer.parseInt(dataIndexInformationArray[0]);
                                int dataLength=Integer.parseInt(dataIndexInformationArray[1]);
                                String arrangeType=dataIndexInformationArray[2];
                                String ABstr=sArray[5].substring(1, sArray[5].length()-1);
                                String[] ABarray=ABstr.split(",");
                                double A=Double.parseDouble(ABarray[0]);
                                double B=Double.parseDouble(ABarray[1]);
                                String CDstr=sArray[6].substring(1, sArray[6].length()-1);
                                String[] CDarray=CDstr.split("\\|");
                                double C=Double.parseDouble(CDarray[0]);
                                double D=Double.parseDouble(CDarray[1]);
                                String unit=sArray[7];
                                String NodeName=sArray[8];
                                Signal signal=new Signal(SignalName,startBit,dataLength,arrangeType,A,B,C,D,unit,NodeName);
                                signalArrayList.add(signal);
                            }
                        }
                    }
                    return signalArrayList;
                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return signalArrayList;
    }
}
