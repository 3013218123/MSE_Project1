package com.mse8.teamwe.cantoolapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
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
    public BluetoothAdapter _btAdapter;
    //设备，蓝牙
    public BluetoothDevice _btDevice;

    //服务器名称
    public static final String PROTOCOL_SCHEME_RFCOMM = "cantoolpp";
    //UUID
    public static final String _UUID = "00001101-0000-1000-8000-00805F9B34FB";
    //标志号
    private static final int STATUS_CONNECT = 0x11;

    //服务端线程
    public CtServerThread _btServerThread;
    //客户端线程
    public CtClientThread _btClientThread;
    //数据线程
    private DataThread _btReadThread;

    public Ct_BtSocket() {

        this._btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    //发送数据
    public void CtSendMessage(String msg) {
        if (_btClientSocket == null) {
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
        this._btServerThread = new CtServerThread();
        this._btServerThread.start();
    }

    //开启客户端
    public void StartClient() {
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


    private class CtServerThread extends Thread {
        @Override
        public void run() {
            try {

                //创建蓝牙服务端Socket，参数为服务器名称、UUID
                _btServerSocket = _btAdapter.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM, UUID.fromString(_UUID));

                Message msg = new Message();
                msg.obj = "请稍候，正在等待客户端的连接...";
                msg.what = STATUS_CONNECT;

                //mHandler.sendMessage(msg);

                //接受客户端的连接请求
                _btClientSocket = _btServerSocket.accept();

                msg = new Message();
                msg.obj = "客户端已经连接上！可以发送信息。";
                msg.what = STATUS_CONNECT;

                //mHandler.sendMessage(msg);

                //启动数据线程
                _btReadThread = new DataThread();
                _btReadThread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private class CtClientThread extends Thread {
        @Override
        public void run() {
            try {

                _btClientSocket = _btDevice.createRfcommSocketToServiceRecord(UUID.fromString(_UUID));

                Message msg = new Message();
                msg.obj = "请稍候，正在连接服务器:";
                msg.what = STATUS_CONNECT;

                //mHandler.sendMessage(msg);

                //连接服务端
                _btClientSocket.connect();

                msg = new Message();
                msg.obj = "已经连接上服务端！可以发送信息。";
                msg.what = STATUS_CONNECT;

                //mHandler.sendMessage(msg);

                //启动数据线程
                _btReadThread = new DataThread();
                _btReadThread.start();

            } catch (IOException e) {
                Message msg = new Message();
                msg.obj = "连接服务端异常！断开连接重新试一试。";
                msg.what = STATUS_CONNECT;

                //mHandler.sendMessage(msg);
            }
        }
    }


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

                        //数据在这里
                        String s = new String(buf_data);

                        Message msg = new Message();
                        msg.obj = s;
                        msg.what = 1;

                        //mHandler.sendMessage(msg);
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
