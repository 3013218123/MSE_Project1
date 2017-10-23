package com.example.administrator.canol.blue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.canol.R;


/**
 * Created by JIChunYang on 2017/10/18.
 */

public class Ct_SendMessage extends Activity {

    public String _sendMessage = "测试数据";

    private Ct_BtSocket ceshi = BlueToothDevices.ct_btSocket;

    private Button _sendmsg;

    private Button _curve;

    private ListView listceshi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_sendcanmsg);

        this._sendmsg = (Button) findViewById(R.id.btn_sendmsg);
        this._sendmsg.setOnClickListener(sendmsgceshi);

        this._curve = (Button) findViewById(R.id.btn_curve);
        this._curve.setOnClickListener(curveceshi);

        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {

                while(true)
                {

                }

            }
        });

    }


    private View.OnClickListener sendmsgceshi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ceshi.CtSendMessage(_sendMessage);
        }
    };


    private View.OnClickListener curveceshi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

//            Intent intent = new Intent();
//            intent.setClass(Ct_SendMessage.this, CanPhysicalCurve.class);
//            startActivity(intent);
        }
    };

}
