package com.mse8.jichunyang.dashbardui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import android.os.Handler;


/**
 * Created by JIChunYang on 2017/10/13.
 */

public class MainActivity extends Activity {
    DashBoardView dbv = null;
    Button btn_next = null;
    double d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardview);

        dbv = (DashBoardView) findViewById(R.id.dbv);
        btn_next = (Button) findViewById(R.id.btn_next);

        dbv.setStartingValue(0);
        dbv.setEndValue(10);
        dbv.setTextCount(5);
        dbv.invalidate();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //随机

                Handler handler=new Handler();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        d = Math.random() * 10;
                        dbv.setArrowData(d);
                        dbv.invalidate();
                    }
                });

            }
        });
    }
}
