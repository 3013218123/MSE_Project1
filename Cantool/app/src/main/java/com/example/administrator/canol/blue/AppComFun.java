package com.example.administrator.canol.blue;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIChunYang on 2017/10/21.
 */

public class AppComFun {

    public static final String _UUID = "00001101-0000-1000-8000-00805F9B34FB";

    public static final String _PROTOCOL_SCHEME_RFCOMM = "cantoolapp";

    public static final List<String> ltmp = new ArrayList<String>();

    public static final int _STATUS_CONNECT = 0x11;

    public static final int _STATUS_ACCEPT = 0x22;

    public static final int _STATUS_SENDMSG = 0x33;

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

                    AppComFun.ltmp.add(info);
                    break;
            }
        }
    };

}
