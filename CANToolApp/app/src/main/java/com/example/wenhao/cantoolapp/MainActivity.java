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
import entity.Signal;

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
            }
        });



    }
}
