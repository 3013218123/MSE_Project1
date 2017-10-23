package com.example.administrator.canol;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import android.os.Handler;


/**
 * Created by JIChunYang on 2017/10/13.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Yibiaopan extends Activity {
    DashBoardView dbv = null;
  //  Button btn_next = null;
    String d;
    double m;
    private LEDView ledView;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardview);
        ledView = (LEDView) findViewById(R.id.yibiaopan_led);
        dbv = (DashBoardView) findViewById(R.id.dbv);

      //  btn_next = (Button) findViewById(R.id.btn_next);

        dbv.setStartingValue(0);
        dbv.setEndValue(10);
        dbv.setTextCount(5);
        dbv.invalidate();
        m=8;
        //d 是String
        d=""+m;

                //随机

                Handler handler=new Handler();

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        dbv.setArrowData(m);
                        dbv.invalidate();
                    }
                });

    }

    @Override
    protected void onResume() {
//        private String mm;
//        mm.String.valueOf(d);
        ledView.setString(d);
        super.onResume();
        ledView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ledView.stop();
    }
}
