package com.example.wenhao.cantoolapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Save.Convert;
import Save.SaveCsv;
import dataRead.BORead;
import dataRead.BOReadAll;
import dataRead.SGRead;
import entity.Message;
import entity.ParseData;
import entity.Signal;
import parse.JudgeStr;
import parse.Parse;
import parse.ReverseParse;

public class MainActivity extends AppCompatActivity {
private Button testButton;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testButton= (Button) findViewById(R.id.Testbutton);
        testButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*
                //测试一下BORead
                Message mse=BORead.readBO("100","PowerTrain.dbc");
                Log.i("tag","天才浩"+mse.getBO_()+mse.getId()+mse.getMessageName()+mse.getSeporator()+mse.getDLC()+mse.getNodeName());
                ArrayList<Signal> signalArrayList= SGRead.readSG("100","PowerTrain.dbc");
                for(Iterator it = signalArrayList.iterator(); it.hasNext(); )
                {
                    Signal  signal= (Signal) it.next();
                    Log.i("tag",signal.getSG_()+signal.getSignalName()+signal.getSeporator()+signal.getStartBit()+" "+signal.getDataLength()
                            +" "+signal.getArrangeType() +signal.getA()+signal.getB()+signal.getC()+signal.getD()+signal.getUnit()+signal.getNodeName());
                }
                */
                //测试一下BOReadAll;
                /*
                ArrayList<Message> messageArrayList= BOReadAll.readBoAll("Comfort.dbc");
                for(Iterator it = messageArrayList.iterator(); it.hasNext(); )
                {
                    Message  mse= (Message) it.next();
                    Log.i("tag",mse.getBO_()+mse.getId()+mse.getMessageName()+mse.getSeporator()+mse.getDLC()+mse.getNodeName());
                }
                */
               // 测试一下Parse
//                String dataStr="t320880478C2F05A1D29A";
//                ParseData pd= Parse.parse(dataStr,"canmsg-sample.dbc");//canmsg-sample.dbc
//                if(pd.getSsRight()){
//                    Message mse=pd.getBO_Mse();
//                    Log.i("tag",mse.getBO_()+mse.getId()+mse.getMessageName()+mse.getSeporator()+mse.getDLC()+mse.getNodeName());
//                    ArrayList<Signal> signalArrayList=pd.getSignals();
//                    int index=0;
//                    double phyArray[]=pd.getPhyArray();
//
//
//
//                    for(Iterator it = signalArrayList.iterator(); it.hasNext(); )
//                    {
//                        Signal  signal= (Signal) it.next();
//
//                        Log.i("tag",signal.getSG_()+signal.getSignalName()+signal.getSeporator()+signal.getStartBit()+" "+signal.getDataLength()
//                                +" "+signal.getArrangeType() +" "+signal.getA()+signal.getB()+signal.getC()+signal.getD()+signal.getUnit()+signal.getNodeName());
//                        Log.i("tag", ""+phyArray[index]);
//                        index++;
//                    }
//
//                    //测试ReverseParse
//                    //String str= ReverseParse.reverseParse("416",phyArray,"Comfort.dbc");
//                    //Log.i("tag",str);
//                }else {
//                    Log.i("tag","所用解析类型不合法");
//                }
                //测试JudgeStr
                //boolean isTrue= JudgeStr.isQualifiedStr("t320880478C2F05A1D29A");
                //Log.i("tag",String.valueOf(isTrue));
                //测试保存

//                myPermission();
//                String path="/data/data/db/";
//                String newFileName="111,csv";
//                File file=new File(path+newFileName);
//                List<String> list=new ArrayList<String>();
//                list.add("1,2,3");
//                list.add("4,5,6");
//                Log.i("tag",String.valueOf(SaveCsv.exportCsv(file,list)));
                //测试一下SaveCsv
                //String [] dataStrArray=new String[]{"t31D80100000000000000","t320880478C2F05A1D29A","t360800402418E4000000"};
                //SaveCsv.writeAll("test.csv",dataStrArray,"canmsg-sample.dbc");
                //测试保存json
//                boolean result=Convert.convert("canmsg-sample.dbc","t3Json.txt","");
//                Log.i("tag",String.valueOf(result));
                //测试解析json
                //Convert.JsonToDbc("t3Json.txt","");


            }
        });



    }
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public void myPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
