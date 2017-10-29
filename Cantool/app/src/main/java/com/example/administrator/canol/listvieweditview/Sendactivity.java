package com.example.administrator.canol.listvieweditview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.canol.Fasong;
import com.example.administrator.canol.R;

import com.example.administrator.canol.Shezhi;
import com.example.administrator.canol.blue.AppComFun;

import com.example.administrator.canol.blue.Ct_BtSocket;
import com.example.administrator.canol.data.ControlData;
import com.example.administrator.canol.dataRead.SGRead;
import com.example.administrator.canol.entity.FileName;
import com.example.administrator.canol.entity.Signal;
import com.example.administrator.canol.parse.ReverseParse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhao on 2017/10/27.
 */

public class Sendactivity extends Activity {


    private Ct_BtSocket ceshi = AppComFun.ct_btSocket;
    private EditText cycleEditText;



    private ListView mListView;
    private Button fasongButton;
    double userPhyArray[];

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            if(ControlData.returnData!=""){
                String gangR=asciiToString("13");
                if(ControlData.returnData.equals(gangR)){
                    Toast.makeText(Sendactivity.this, "发送成功", Toast.LENGTH_LONG).show();
                }else if(ControlData.returnData.equals("\\BEL")){
                    Toast.makeText(Sendactivity.this, "发送失败", Toast.LENGTH_LONG).show();
                }
                ControlData.returnData="";
            }


            handler.postDelayed(this, 1000 * 3);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendlayout);
        String bo = (String) getIntent().getSerializableExtra("key");

        String[] boInfArray=bo.split(" ");
        final String boId=boInfArray[1];
        final String fileName = FileName.filename;
        Log.i("tag",boId);
        cycleEditText=findViewById(R.id.sendEditText);


        mListView = (ListView) findViewById(R.id.list_view);
        final List<Bean> list = new ArrayList<>();
        ArrayList<Signal> signalArrayList = SGRead.readSG(boId, fileName);
        for (int i = 0; i < signalArrayList.size(); i++) {
            Signal signal = signalArrayList.get(i);
            String name = signal.getSignalName() + "\n范围：" + signal.getC() + "--" + signal.getD();
            Bean bean = new Bean(name, "");
            list.add(bean);
        }
        MyAdapter mAdapter = new MyAdapter(this, list);
        mListView.setAdapter(mAdapter);


        fasongButton = findViewById(R.id.fasongButton);
        fasongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhyArray = new double[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    Bean bean = list.get(i);
                    Log.i("tag", bean.getName() + " " + bean.getInput());
                    try {
                        userPhyArray[i] = Double.parseDouble(bean.getInput());
                    } catch (Exception e) {

                    }

                }

                String cycle=cycleEditText.getText().toString();
                if(cycle.equals("")){
                    cycle="0000";
                }else { //转成16进制数
                    try{
                        Long cycleLong=Long.parseLong(cycle);
                        cycle=decimalToHex(cycleLong);

                        while (cycle.length()<4){
                            cycle="0"+cycle;
                        }

                    }catch (Exception e){
                        cycle="0000";
                    }

                }
                if(cycle.length()>4){
                    Toast.makeText(Sendactivity.this, "你输入的周期超出了长度限制，发送失败", Toast.LENGTH_LONG).show();
                }else {
                    Log.i("tag","cycle: "+cycle);
                    String reverseParseStr=ReverseParse.reverseParse(boId,userPhyArray,fileName);
                    Log.i("tag","reversePareStr: "+reverseParseStr);
                    reverseParseStr=reverseParseStr+cycle+asciiToString("13");
                    Log.i("tag","最终发送的: "+reverseParseStr);
                    ceshi.CtSendMessage(reverseParseStr);
//                    Intent intent=new Intent(Sendactivity.this, Fasong.class);
//                    startActivity(intent);
                }



            }
        });

        handler.postDelayed(runnable, 1000 * 3);
    }
    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
    public static String decimalToHex(long decimal) {
        String hex = "";
        while(decimal != 0) {
            long hexValue = decimal % 16;
            hex = toHexChar(hexValue) + hex;
            decimal = decimal / 16;
        }
        return  hex;
    }
    //将0~15的十进制数转换成0~F的十六进制数
    public static char toHexChar(long hexValue) {
        if(hexValue <= 9 && hexValue >= 0)
            return (char)(hexValue + '0');
        else
            return (char)(hexValue - 10 + 'A');
    }
}
