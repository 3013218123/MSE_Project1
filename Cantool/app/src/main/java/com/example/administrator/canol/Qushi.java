package com.example.administrator.canol;

import android.os.Bundle;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.app.Activity;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.canol.blue.AppComFun;
import com.example.administrator.canol.data.ChartData;
import com.example.administrator.canol.dataRead.BORead;
import com.example.administrator.canol.dataRead.SGRead;
import com.example.administrator.canol.entity.FileName;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;
import com.example.administrator.canol.parse.Parse;
import com.example.administrator.canol.physicalcurve.LineGraphicView;
import com.example.administrator.canol.quxian.FancyChart;
import com.example.administrator.canol.trylistviewcheckbox.DataHolder;
import com.example.administrator.canol.trylistviewcheckbox.ListitemAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017\10\20 0020.
 */

public class Qushi extends Activity {
    private List<String> strings = AppComFun.ltmp;
    private LineGraphicView lineGraphicView;
    private ListView listView;

    private String bo = "";
    private String filename = FileName.filename;

    private int lastLength = 0;
    private boolean[] signalchecked;
    private ArrayList<Double> maxArray = new ArrayList<>();
    private ArrayList<ArrayList<Double>> tarr;
    private ArrayList<String> xRawDatas;
    private ArrayList<Double> tmp1;
    String tStr="";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    //需要各组信号值的最大值.
                    lineGraphicView.SetData(tarr, xRawDatas, tmp1, 2);
                    lineGraphicView.invalidate();
                    break;

                default:
                    break;
            }
        }
    };

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            signalchecked = GetCheckedStatus();
            int count = GetCheckedCount(signalchecked);
            if (lastLength != strings.size()||count>0) {

                Update(count);

                android.os.Message msg = new android.os.Message();
                msg.obj = "1";
                msg.what = 0x11;

                mHandler.sendMessage(msg);
                //lineGraphicView.Refresh();
            }
            lastLength = strings.size();

            handler.postDelayed(this, 1000 * 3);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yibiaopanactivity);
        lineGraphicView = findViewById(R.id.linegraph);

        bo = (String) getIntent().getSerializableExtra("key");
        String [] boArrays=bo.split(" ");
        String bo_id=boArrays[1];

        if(Integer.parseInt(bo_id)>Integer.MAX_VALUE){
            String tid=""+decimalToHex(Long.parseLong(bo_id));
            tStr="T"+tid;
        }else{
            String tid=""+decimalToHex(Integer.parseInt(bo_id));
            while(tid.length()<3){
                tid="0"+tid;
            }
            tStr="t"+tid;

        }


        //构建listview
        final List<DataHolder> dataList = new ArrayList<DataHolder>();
        ArrayList<Signal> signals = SGRead.readSG(bo_id,filename);
        //构造listview数据
        for (int k = 0; k < signals.size(); k++) {
            Signal signal = signals.get(k);
            dataList.add(new DataHolder(signal.getSignalName(), ""));
            maxArray.add(signal.getD());
        }

        //绑定listview的适配器
        ListitemAdapter adapter = new ListitemAdapter(Qushi.this, dataList);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        handler.postDelayed(runnable, 1000 * 3);
    }

    private void Update(int count) {
        ArrayList<double[]> phyArrayArraylist = new ArrayList<>();
        int tp=0;
        for (int i = strings.size()-1; i>=0 ; i--) {
            if (strings.get(i).indexOf(tStr)!=-1) {
                tp++;
                if(tp<=6){
                    ParseData parsedate = Parse.parse(strings.get(i), filename);
                    double[] phyArray = parsedate.getPhyArray();
                    phyArrayArraylist.add(phyArray);
                }

            }
        }

        tmp1 = new ArrayList<>();
        for (int i = 0; i < signalchecked.length; i++) {
            if (signalchecked[i] == true) {
                tmp1.add(maxArray.get(i));
            }
        }

        double[][] temp = new double[count][phyArrayArraylist.size()];
        int y = 0;
        for (int i = 0; i < phyArrayArraylist.size(); i++) {
            double[] tmp = phyArrayArraylist.get(i);

            int x = 0;
            for (int j = 0; j < tmp.length; j++) {

                if (signalchecked[j] == true) {
                    temp[x][y] = tmp[j];
                    x++;
                }
            }
            y++;
        }

        tarr = new ArrayList<ArrayList<Double>>();
        for (int i = 0; i < count; i++) {
            ArrayList arr = new ArrayList<Double>();
            for (int j = phyArrayArraylist.size()-1; j >=0 ; j--) {
                arr.add(temp[i][j]);
            }
            tarr.add(arr);
        }

        xRawDatas = new ArrayList<String>();
        int m=tp-6;
        if(m<0) m=0;
        for (int i = (tp-1); i >=m; i--) {
            xRawDatas.add(String.valueOf(tp-i));
        }

        //需要各组信号值的最大值.
        //lineGraphicView.SetData(tarr, xRawDatas, tmp1, 2);
    }

    private boolean[] GetCheckedStatus() {
        boolean[] checked = new boolean[listView.getCount()];
        for (int i = 0; i < listView.getCount(); i++) {
            checked[i] = false;
        }

        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                checked[i] = true;
            }
        }

        return checked;
    }

    private int GetCheckedCount(boolean[] booleen) {
        int count = 0;
        for (int i = 0; i < booleen.length; i++) {
            if (booleen[i] == true) {
                count++;
            }
        }

        return count;
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




