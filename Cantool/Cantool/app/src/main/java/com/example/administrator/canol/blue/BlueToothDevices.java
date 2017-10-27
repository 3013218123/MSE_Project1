package com.example.administrator.canol.blue;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import com.example.administrator.canol.R;

/**
 * Created by JIChunYang on 2017/10/17.
 */

public class BlueToothDevices extends Activity {

    public static Ct_BtSocket ct_btSocket = new Ct_BtSocket();

    private BluetoothAdapter _blueToothAdapter;

    private BlueToothAdapter _mBTAdapter;

    private ArrayList<BlueToothMes> _blueToothList;

    private ListView _listView;

    private Button _strButton;

    private Button _serButton;

    private Button _stpButton;

    private Button _ceshiButton;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String info = (String) msg.obj;

            switch (msg.what) {
                case AppComFun._STATUS_ACCEPT:
                    Toast.makeText(BlueToothDevices.this, info, Toast.LENGTH_SHORT).show();
                    break;
                case AppComFun._STATUS_CONNECT:
                    Toast.makeText(BlueToothDevices.this, info, Toast.LENGTH_SHORT).show();
                    break;
                case AppComFun._STATUS_SENDMSG:
                    Toast.makeText(BlueToothDevices.this, info, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_bluetooth);

        Initialization();

        registerBroadcast();

        this.ct_btSocket.SetPromptHandler(mHandler);

    }

    //初始化
    private void Initialization() {
        this._blueToothAdapter = BluetoothAdapter.getDefaultAdapter();

        this._blueToothList = new ArrayList<BlueToothMes>();
        this._mBTAdapter = new BlueToothAdapter(this, this._blueToothList);

        //界面初始化
        this._listView = (ListView) findViewById(R.id.bluetooth_list);
        this._listView.setAdapter(this._mBTAdapter);
        this._listView.setFastScrollEnabled(true);

        this._listView.setOnItemClickListener(mDeviceClickListener);

        this._strButton = (Button) findViewById(R.id.btn_str_search);
        this._strButton.setOnClickListener(mSearchListener);

        this._serButton = (Button) findViewById(R.id.btn_str_service);
        this._serButton.setOnClickListener(mServiceListener);

        this._stpButton = (Button) findViewById(R.id.btn_stp_service);
        this._stpButton.setOnClickListener(mStopServiceListener);


        this._ceshiButton = (Button) findViewById(R.id.tiaozhuan);
        this._ceshiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BlueToothDevices.this, Ct_SendMessage.class);
                startActivity(intent);
            }
        });
//        mBtnService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                BluetoothActivity.mType = Type.SERVICE;
//                BluetoothActivity.mTabHost.setCurrentTab(1);
//            }
//        });


    }

    private View.OnClickListener mServiceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //开启服务
            ct_btSocket.StartServer();
        }
    };

    private View.OnClickListener mStopServiceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //断开连接
            ct_btSocket.StopClient();
        }
    };

    private View.OnClickListener mSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

            if (_blueToothAdapter.isDiscovering()) {
                _blueToothAdapter.cancelDiscovery();
                _strButton.setText("重新搜索");
            } else {
                _blueToothList.clear();
                _mBTAdapter.notifyDataSetChanged();

                //搜索蓝牙设备
                SearchBlueDevices();

                //开始搜索
                _blueToothAdapter.startDiscovery();
                _strButton.setText("ֹͣ停止搜索");
            }
        }
    };


    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            BlueToothMes bean = _blueToothList.get(position);
            String info = bean._message;
            final String address = info.substring(info.length() - 17);


            AlertDialog.Builder stopDialog = new AlertDialog.Builder(BlueToothDevices.this);

            stopDialog.setTitle("连接");
            stopDialog.setMessage(bean._message);

            stopDialog.setPositiveButton("连接", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    _blueToothAdapter.cancelDiscovery();
                    _strButton.setText("重新搜索");

                    ct_btSocket.SetBtDevice(address);
                    ct_btSocket.StartClient();

                    dialog.cancel();
                }
            });

            stopDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });


            stopDialog.show();
        }
    };

    //搜索蓝牙设备
    private void SearchBlueDevices() {

        Set<BluetoothDevice> deviceSet = this._blueToothAdapter.getBondedDevices();

        if (deviceSet.size() > 0) {
            for (BluetoothDevice device : deviceSet) {
                _blueToothList.add(new BlueToothMes(device.getName() + "\n" + device.getAddress(), true));
                _listView.setSelection(_blueToothList.size() - 1);
            }
        } else {
            _blueToothList.add(new BlueToothMes("没有找到配对的设备", true));
            _listView.setSelection(_blueToothList.size() - 1);
        }

    }

    //注册广播
    private void registerBroadcast() {

        //设备被发现广播
        IntentFilter discoveryFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, discoveryFilter);

        // 设备发现完成
        IntentFilter foundFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, foundFilter);
    }

    //发现设备广播
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 获得设备信息
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 如果绑定的状态不一样
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    _blueToothList.add(new BlueToothMes(device.getName() + "\n" + device.getAddress(), false));
                    _listView.setSelection(_blueToothList.size() - 1);
                }
                // 如果搜索完成了
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                if (_listView.getCount() == 0) {
                    _blueToothList.add(new BlueToothMes("没有发现蓝牙设备", false));
                    _listView.setSelection(_blueToothList.size() - 1);
                }
                _strButton.setText("重新搜索");
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        if (!_blueToothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 3);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (_blueToothAdapter != null) {
            _blueToothAdapter.cancelDiscovery();
        }

        ct_btSocket.StopServer();

        this.unregisterReceiver(mReceiver);
    }

}
