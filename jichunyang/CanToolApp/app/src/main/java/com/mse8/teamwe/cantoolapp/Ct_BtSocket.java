package com.mse8.teamwe.cantoolapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread;
import java.util.UUID;

/**
 * Created by JIChunYang on 2017/10/18.
 */

public class Ct_BtSocket {

    //蓝牙服务接收端Socket
    private BluetoothServerSocket _btServerSocket;
    //蓝牙客户发送端Socket
    private BluetoothSocket _btClientSocket;

    //设备，蓝牙适配器
    private BluetoothAdapter _btAdapter;
    //设备，蓝牙
    private BluetoothDevice _btDevice;


    //服务端线程
    public CtServerThread _btServerThread;
    //客户端线程
    public CtClientThread _btClientThread;
    //数据线程
    private DataThread _btReadThread;


    //提示消息处理
    private Handler _handler;

    //发送信息到信息处理的类

    //构造函数
    public Ct_BtSocket() {

        this._btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    //获取蓝牙适配器
    public BluetoothAdapter GetBtAdapter() {
        if (this._btAdapter != null)
            return this._btAdapter;
        else
            return null;
    }

    //设置蓝牙设备
    public void SetBtDevice(String address) {

        if (address == "" || address == null) {
            throw new NullPointerException("address is null.");
        }

        if (_btAdapter == null) {
            throw new NullPointerException("BluetoothAdapter is null.");
        }

        this._btDevice = this._btAdapter.getRemoteDevice(address);
    }

    //设置提示消息处理器
    public void SetPromptHandler(Handler handler) {

        if (handler == null) {
            throw new NullPointerException("Handler is null.");
        }

        this._handler = handler;
    }

    //发送数据
    public void CtSendMessage(String msg) {
        if (_btClientSocket == null) {

            Message _msg = new Message();
            _msg.obj = "请开启客户端线程...";
            _msg.what = AppComFun._STATUS_SENDMSG;

            _handler.sendMessage(_msg);

            return;
        }
        try {
            OutputStream os = _btClientSocket.getOutputStream();
            os.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //开启服务端
    public void StartServer() {

        if (this._btServerThread != null) {

            Message _msg = new Message();
            _msg.obj = "服务端线程已开启...";
            _msg.what = AppComFun._STATUS_ACCEPT;

            _handler.sendMessage(_msg);

            return;
        }

        this._btServerThread = new CtServerThread();
        this._btServerThread.start();
    }

    //开启客户端
    public void StartClient() {

        if (this._btClientThread != null) {

            Message _msg = new Message();
            _msg.obj = "客户端线程已开启，若想重新连接，请先断开连接...";
            _msg.what = AppComFun._STATUS_CONNECT;

            _handler.sendMessage(_msg);

            return;
        }

        this._btClientThread = new CtClientThread();
        this._btClientThread.start();
    }

    //停止服务端
    public void StopServer() {
        new Thread() {
            public void run() {
                if (_btServerThread != null) {
                    _btServerThread.interrupt();
                    _btServerThread = null;
                }
                if (_btReadThread != null) {
                    _btReadThread.interrupt();
                    _btReadThread = null;
                }
                try {
                    if (_btClientSocket != null) {
                        _btClientSocket.close();
                        _btClientSocket = null;
                    }
                    if (_btServerSocket != null) {
                        _btServerSocket.close();
                        _btServerSocket = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //停止客户端
    public void StopClient() {
        new Thread() {
            public void run() {
                if (_btClientThread != null) {
                    _btClientThread.interrupt();
                    _btClientThread = null;
                }
                if (_btReadThread != null) {
                    _btReadThread.interrupt();
                    _btReadThread = null;
                }
                if (_btClientSocket != null) {
                    try {
                        _btClientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    _btClientSocket = null;
                }
            }
        }.start();
    }


    //服务端线程
    private class CtServerThread extends Thread {
        @Override
        public void run() {
            try {

                //创建蓝牙服务端Socket，参数为服务器名称、UUID
                _btServerSocket = _btAdapter.listenUsingRfcommWithServiceRecord(AppComFun._PROTOCOL_SCHEME_RFCOMM, UUID.fromString(AppComFun._UUID));


                Message msg = new Message();
                msg.obj = "请稍候，正在等待客户端的连接...";
                msg.what = AppComFun._STATUS_ACCEPT;

                _handler.sendMessage(msg);

                //接受客户端的连接请求
                _btClientSocket = _btServerSocket.accept();

                msg = new Message();
                msg.obj = "客户端已经连接上，可以发送信息...";
                msg.what = AppComFun._STATUS_ACCEPT;

                _handler.sendMessage(msg);

                //启动数据线程
                _btReadThread = new DataThread();
                _btReadThread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //客户端线程
    private class CtClientThread extends Thread {
        @Override
        public void run() {
            try {

                _btClientSocket = _btDevice.createRfcommSocketToServiceRecord(UUID.fromString(AppComFun._UUID));

                Message msg = new Message();
                msg.obj = "请稍候，正在连接服务器...";
                msg.what = AppComFun._STATUS_CONNECT;

                _handler.sendMessage(msg);

                //连接服务端
                _btClientSocket.connect();

                msg = new Message();
                msg.obj = "已经连接上服务端，可以发送信息...";
                msg.what = AppComFun._STATUS_CONNECT;

                _handler.sendMessage(msg);


            } catch (IOException e) {

                Message msg = new Message();
                msg.obj = "连接服务端异常！断开连接重新试一试。";
                msg.what = AppComFun._STATUS_CONNECT;

                _handler.sendMessage(msg);
            }
        }
    }

    //数据线程
    private class DataThread extends Thread {
        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            InputStream is = null;

            try {
                is = _btClientSocket.getInputStream();

                while (true) {
                    if ((bytes = is.read(buffer)) > 0) {
                        byte[] buf_data = new byte[bytes];

                        for (int i = 0; i < bytes; i++) {
                            buf_data[i] = buffer[i];
                        }

                        //将byte数据转化为字符串
                        String s = new String(buf_data);

                        //将接收到的数据存储在链表中,传输到数据处理模块

                    }
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

}
