import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import dataRead.BORead;
import entity.Message;

/**
 * Created by wenhao on 2017/10/18.
 */

public class Test {
    @org.junit.Test
    public void read(){
        Message mse=BORead.readBO("100","D:/eclipseEE/eclipse-workspace/CANTool/src/db0.dbc");
        System.out.println(mse.getBO_()+"*"+mse.getId()+"*"+mse.getMessageName()+"*"+mse.getSeporator()+"*"+mse.getDLC()+"*"+mse.getNodeName());
       /* File file = new File("D:/eclipseEE/eclipse-workspace/CANTool/src/db0.dbc");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            System.out.print(String.valueOf(file.exists()));

            String s=br.readLine();
            System.out.print(s);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
