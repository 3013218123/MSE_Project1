package com.example.administrator.canol;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.app.Activity;

import com.example.administrator.canol.data.ChartData;
import com.example.administrator.canol.data.Point;
import com.example.administrator.canol.quxian.FancyChart;
import com.example.administrator.canol.quxian.FancyChartPointListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017\10\20 0020.
 */

public class Qushi extends Activity{

    private RadioGroup radioGroup;
    private List<String> list=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quxian);

       final FancyChart chart = (FancyChart) findViewById(R.id.chart);
//        chart.setOnPointClickListener(new FancyChartPointListener() {
//
//            @Override
//            public void onClick(Point point) {
//                Toast.makeText(Qushi.this, "I clicked point " + point, Toast.LENGTH_LONG).show();
//            }
//        });

        final ChartData data = new ChartData(ChartData.LINE_COLOR_BLUE);
        int[] yValues = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for(int i = 8; i <= 19; i++) {
            data.addPoint(i, yValues[i-8]);
            data.addXValue(i, i + ":00");
        }
        chart.addData(data);

//        ChartData data2 = new ChartData(ChartData.LINE_COLOR_RED);
//        int[] yValues2 = new int[]{0, 50, 90, 23, 105, 35, 45, 50, 41, 45, 32, 24};
//        for(int i = 8; i <= 19; i++) {
//            data2.addPoint(i, yValues2[i-8]);
//            data2.addXValue(i, i + ":00");
//        }
//        chart.addData(data2);
        radioGroup = (RadioGroup) findViewById(R.id.quxian_rg);

        list.add("SG149");
        list.add("SG280");
        list.add("SG320");
        list.add("SG50");
        list.add("SG80");
        for(int i=0;i<list.size();i++) {
            RadioButton radioButton = new RadioButton(this);
            final   String num=(String) list.get(i);
            radioButton.setText(num);
            radioGroup.addView(radioButton);
            radioButton.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                    Handler handler=new Handler();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            int[] yValues = new int[]{0, 100, 80, 50, 100, 30, 33, 32, 46, 53, 50, 42};
                            for(int i = 8; i <= 19; i++) {
                                data.addPoint(i, yValues[i-8]);
                                data.addXValue(i, i + ":00");
                            }
                            chart.addData(data);
                        }
                    });


                }

            });
        }
    }


}

