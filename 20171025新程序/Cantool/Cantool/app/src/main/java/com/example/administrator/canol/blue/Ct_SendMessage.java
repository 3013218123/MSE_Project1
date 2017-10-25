package com.example.administrator.canol.blue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.canol.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JIChunYang on 2017/10/18.
 */

public class Ct_SendMessage extends Activity {

    public String _sendMessage = "测试数据";

    private Ct_BtSocket ceshi = BlueToothDevices.ct_btSocket;

    private Button _sendmsg;

    private Button _curve;

    private TextView _listreceive;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String info = (String) msg.obj;

            switch (msg.what) {
                case AppComFun._STATUS_CONNECT:

                    String t = "";
                    if (AppComFun.ltmp.size() != 0) {
                        for (String a : AppComFun.ltmp) {
                            t = t + a + "\n";
                        }
                    }

                    _listreceive.setText(t + info);

                    AppComFun.ltmp.add(info);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_sendcanmsg);

        this._sendmsg = (Button) findViewById(R.id.btn_sendmsg);
        this._sendmsg.setOnClickListener(sendmsgceshi);

        this._curve = (Button) findViewById(R.id.btn_curve);
        this._curve.setOnClickListener(curveceshi);

//        this._listreceive = (TextView) findViewById(R.id.list_receive);

        this.ceshi.SetDataHandler(mHandler);


//        ReceiveData rd = new ReceiveData();
//        rd.start();

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

            Intent intent = new Intent();
//            intent.setClass(Ct_SendMessage.this, CanPhysicalCurve.class);
            startActivity(intent);
        }
    };


    private class ReceiveData extends Thread {
        @Override
        public void run() {

            while (true) {
                String temp = ceshi.GetSSS();

                Log.i("tag", temp);

                //_listreceive.setText(temp);
//
//                Message msg = new Message();
//                msg.obj = temp;
//                msg.what = AppComFun._STATUS_CONNECT;
//
//                mHandler.sendMessage(msg);
            }

        }
    }

}
