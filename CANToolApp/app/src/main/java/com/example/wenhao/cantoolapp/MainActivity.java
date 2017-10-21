package com.example.wenhao.cantoolapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import dataRead.BORead;
import dataRead.SGRead;
import entity.Message;
import entity.ParseData;
import entity.Signal;
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
                //测试一下Parse
                ParseData pd= Parse.parse("t39380000381403000000","canmsg-sample.dbc");
                Message mse=pd.getBO_Mse();
                Log.i("tag",mse.getBO_()+mse.getId()+mse.getMessageName()+mse.getSeporator()+mse.getDLC()+mse.getNodeName());
                ArrayList<Signal> signalArrayList=pd.getSignals();
                int index=0;
                double phyArray[]=pd.getPhyArray();
                for(Iterator it = signalArrayList.iterator(); it.hasNext(); )
                {
                    Signal  signal= (Signal) it.next();
                    Log.i("tag",signal.getSG_()+signal.getSignalName()+signal.getSeporator()+signal.getStartBit()+" "+signal.getDataLength()
                            +" "+signal.getArrangeType() +" "+signal.getA()+signal.getB()+signal.getC()+signal.getD()+signal.getUnit()+signal.getNodeName());
                    Log.i("tag", ""+phyArray[index]);
                    index++;
                }
                //测试ReverseParse
                //String str= ReverseParse.reverseParse("800",phyArray,"canmsg-sample.dbc");
                //Log.i("tag",str);

            }
        });



    }

}
