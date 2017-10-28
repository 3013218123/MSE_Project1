package com.example.administrator.canol;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.os.Handler;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.canol.blue.AppComFun;
import com.example.administrator.canol.entity.FileName;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;
import com.example.administrator.canol.parse.Parse;

import java.util.ArrayList;

import java.util.List;


import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by JIChunYang on 2017/10/13.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Yibiaopan extends Activity {
    private RadioGroup radioGroup;
    private ArrayList<Signal> signals;
    private double[] phyArray;
    DashBoardView dbv = null;
    //private String[] strings;
    private List<String> strings= AppComFun.ltmp;
    private String filename;
    //  Button btn_next = null;
    String d;
    double m;
    //下方三个与动态数组有关
    private Spinner spiSign = null;
    private ArrayAdapter<String> adapteSign = null;
    private List<String> dataSign = new ArrayList<>();
    private LEDView ledView;
    private TextView myTextView;
    private String sign1;

    public Yibiaopan() {
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardview);
        ledView = (LEDView) findViewById(R.id.yibiaopan_led);
        dbv = (DashBoardView) findViewById(R.id.dbv);
        String bo = (String) getIntent().getSerializableExtra("key");
        //strings = new String[]{"t320880478C2F05A1D29A", "t31880300000000000000", "t31D80200000000000000", "t320880478C2F05A1D29A"};
        filename = FileName.filename;
        for (int i = strings.size() - 1; i >= 0; i--) {
            ParseData parsedate;
            parsedate = Parse.parse(strings.get(i), filename);
            Message message = parsedate.getBO_Mse();
            String bo_current = message.getBO_() + message.getId() + message.getMessageName()
                    + message.getSeporator() + message.getNodeName();
            if (bo.equals(bo_current)) {
                signals = parsedate.getSignals();
                final double[] phyArray = parsedate.getPhyArray();
                //获取了list和动态数组
                //问题是只获得了一个呢？？？遍历的时候 k的值很重要
                for (int k = 0; k < signals.size(); k++) {
                    //dataSign = new ArrayList<>();
                    Signal signal = signals.get(k);
                    sign1 = signal.getSG_() + signal.getSignalName() + signal.getSeporator();
                    dataSign.add(sign1);
                    spiSign = (Spinner) super.findViewById(R.id.yibiaopan_xiala);
                }
                adapteSign = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.dataSign);
                adapteSign.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spiSign.setAdapter(this.adapteSign);
                spiSign.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        Toast toast = Toast.makeText(getApplicationContext(), adapteSign.getItem(arg2) + String.valueOf(arg2), Toast.LENGTH_SHORT);
                        toast.show();
                        Signal signal = signals.get(arg2);
                        m = phyArray[arg2];
                        double C = signal.getC();
                        double D = signal.getD();
                        dbv.setStartingValue((float) C);
                        dbv.setEndValue((float) D);

                        dbv.setTextCount(5);
                        dbv.invalidate();
                        //d 是String
                        d = "" + m;
                        dbv.setArrowData(m);
                        dbv.invalidate();
                        ledView.setString(d);
                        onResume();
                        ledView.start();


                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        Toast toast = Toast.makeText(getApplicationContext(), "无点击", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
//if判断
            break;
            }
//for循环的结束
        }

//activity创建的结束
    }
//类结束
}
