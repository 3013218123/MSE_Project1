package com.example.administrator.canol;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.app.Activity;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.canol.data.ChartData;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;
import com.example.administrator.canol.parse.Parse;
import com.example.administrator.canol.quxian.FancyChart;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017\10\20 0020.
 */

public class Qushi extends Activity{
    private ArrayList<Signal> signals;
    private double [] phyArray;
    private Spinner spiSign = null;
    private ArrayAdapter<String> adapteSign = null;
    private List<String> dataSign = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quxian);
        spiSign = (Spinner) super.findViewById(R.id.quxian_xiala);
        String bo = (String) getIntent().getSerializableExtra("key");
        String[] strings = new String[]{"t320880478C2F05A1D29A", "t31880300000000000000", "t31D80200000000000000", "t320880478C2F05A1D29A"};
        String filename = "canmsg-sample.dbc";
        for (int i = strings.length - 1; i >= 0; i--) {
            ParseData parsedate;
            parsedate = Parse.parse(strings[i], filename);
            Message message = parsedate.getBO_Mse();
            String bo_current = message.getBO_() + message.getId() + message.getMessageName()
                    + message.getSeporator() + message.getNodeName();
            if (bo.equals(bo_current)) {
                signals = parsedate.getSignals();
                final double[] phyArray = parsedate.getPhyArray();
                //获取了list和动态数组
                //问题是只获得了一个呢？？？遍历的时候 k的值很重要
                for (int k = 0; k < signals.size(); k++) {
                    Signal signal = signals.get(k);
                    String sign1 = signal.getSG_() + signal.getSignalName() + signal.getSeporator();
                    dataSign.add(sign1);
              //k循环  下拉添加
                }
                adapteSign = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.dataSign);
                adapteSign.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spiSign.setAdapter(this.adapteSign);
                spiSign.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    //有点击的监听
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        //显示监听的是哪一个
                        String toa="点击的是"+arg2;
                        //两个表判断
                        FancyChart chart = (FancyChart) findViewById(R.id.chart);
                        ChartData data = new ChartData(ChartData.LINE_COLOR_BLUE);
                        ChartData data1 = new ChartData(ChartData.LINE_COLOR_BLUE);
                        int[] yValues = new int[]{0, 10, 40, 50, 10, 30, 33, 32, 46, 53, 50, 42};
                        int[] yValues1=new int[]{40, 40, 10, 10, 50, 30, 10, 40, 46, 53, 50, 42};
                        for (int n = 8; n <= 19; n++) {
                            data.addPoint(n, yValues[n - 8]);
                            data.addXValue(n, n + "");
                        }
                        for (int n = 8; n <= 19; n++) {
                            data1.addPoint(n, yValues1[n - 8]);
                            data1.addXValue(n, n + "");
                        }
                        if(arg2>0) {
                            chart.addData(data);
                        }else{
                            chart.addData(data1);
                        }

                        Toast toast = Toast.makeText(getApplicationContext(), toa, Toast.LENGTH_SHORT);
                        toast.show();

                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        Toast toast = Toast.makeText(getApplicationContext(), "无点击", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
   //if 判断
                break;
            }
//第一个for循环
        }



    }}