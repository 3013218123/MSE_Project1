package com.example.myserialtest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import android_serialport_api.SerialPort;

public class MainActivity extends Activity {


    private TextView tv_receive;
    private EditText et_send;
    private Button bt_send;


    private SerialPort mSerialPort;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private String sPort = "/dev/ttyS3";
    private int iBaudRate = 115200;
    private String receiveString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         /* 隐藏标题栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* 隐藏状态栏 */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* 设定屏幕显示为横屏 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_main);

        //初始化组件
        tv_receive = (TextView) findViewById(R.id.tv_receive);
        et_send = (EditText) findViewById(R.id.et_send);
        bt_send = (Button) findViewById(R.id.bt_send);


        //获取串口实例
        try {
            mSerialPort = new SerialPort(new File(sPort), iBaudRate, 0);
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //发送数据
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string=et_send.getText().toString();
                if(TextUtils.isEmpty(string)){
                    et_send.setError("发送的数据不能为空");
                }else {
                    send(string);
                }
            }
        });

    }

    /**
     * 读串口线程
     */
    private class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                if (mInputStream != null) {
                    byte[] buffer = new byte[512];
                    int size = 0;
                    try {
                        size = mInputStream.read(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (size > 0) {
                        byte[] buffer2 = new byte[size];
                        for (int i = 0; i < size; i++) {
                            buffer2[i] = buffer[i];
                        }
                        receiveString = SerialDataUtils.ByteArrToHex(buffer2).trim();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(receiveString != null){
                                tv_receive.append((new Date().toString())+" : "+receiveString+"\r\n");
                                }
                            }
                        });
                    }
                    try {
                        //延时50ms
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return;
        }
    }

    /**
     * 发串口数据
     */
    public void send(final String string) {
        try {
            //去掉空格
            String s = string;
            s = s.replace(" ", "");
            byte[] bytes = SerialDataUtils.HexToByteArr(s);
            mOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        //释放串口
        mSerialPort.close();
        super.onDestroy();
    }
}
