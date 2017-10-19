package com.mse8.teamwe.cantoolapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import android.support.annotation.Nullable;

import com.mse8.teamwe.cantoolapp.FancyChart;
import com.mse8.teamwe.cantoolapp.FancyChartPointListener;
import com.mse8.teamwe.cantoolapp.R;
import com.mse8.teamwe.cantoolapp.data.ChartData;
import com.mse8.teamwe.cantoolapp.data.Point;

/**
 * Created by JIChunYang on 2017/10/19.
 */

public class CanPhysicalCurve extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_physicalcurve);

        FancyChart chart = (FancyChart) findViewById(R.id.chart);
        chart.setOnPointClickListener(new FancyChartPointListener() {

            @Override
            public void onClick(Point point) {
                Toast.makeText(CanPhysicalCurve.this, "I clicked point " + point, Toast.LENGTH_LONG).show();
            }
        });

        ChartData data = new ChartData(ChartData.LINE_COLOR_BLUE);
        int[] yValues = new int[]{0, 8, 9, 18, 35, 30, 33, 32, 46, 53, 50, 42};
        for (int i = 8; i <= 19; i++) {
            data.addPoint(i, yValues[i - 8]);
            data.addXValue(i, i + ":00");
        }
        chart.addData(data);

        ChartData data2 = new ChartData(ChartData.LINE_COLOR_RED);
        int[] yValues2 = new int[]{0, 5, 9, 23, 15, 35, 45, 50, 41, 45, 32, 24};
        for (int i = 8; i <= 19; i++) {
            data2.addPoint(i, yValues2[i - 8]);
            data2.addXValue(i, i + ":00");
        }
        chart.addData(data2);
    }
}
