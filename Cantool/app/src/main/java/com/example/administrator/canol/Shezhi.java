package com.example.administrator.canol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.canol.blue.AppComFun;
import com.example.administrator.canol.data.ControlData;

public class Shezhi extends Activity {
    private Spinner shezhi_xiala;
    private Button button;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            if(ControlData.returnData!=""){
                String gangR=asciiToString("13");
                if(ControlData.returnData.equals(gangR)){
                    Toast.makeText(Shezhi.this, "设置成功", Toast.LENGTH_LONG).show();
                }else if(ControlData.returnData.equals("\\BEL")){
                    Toast.makeText(Shezhi.this, "设置失败", Toast.LENGTH_LONG).show();
                }
                ControlData.returnData="";
            }


            handler.postDelayed(this, 1000 * 3);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);


        shezhi_xiala = (Spinner) this.findViewById(R.id.shezhi_xiala);
        button = (Button) this.findViewById(R.id.shezhi_fasong);

        shezhi_xiala.getSelectedItem();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String num1 = shezhi_xiala.getSelectedItem().toString();
                String gangR = asciiToString("13");
                num1 = num1 + gangR;

                AppComFun.ct_btSocket.CtSendMessage(num1);
            }
        });

        handler.postDelayed(runnable, 1000 * 3);
    }
    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
}
