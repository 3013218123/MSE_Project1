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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Lianjie extends Activity {

    private RadioGroup radioGroup;
   private Button button;
    private List<String> list=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lianjie);
        button=(Button) findViewById(R.id.lianjie_lianjie);
        radioGroup = (RadioGroup) findViewById(R.id.lianjie_rg);

        list.add("蓝牙1");
          list.add("蓝牙2");
        list.add("蓝牙3");
        list.add("蓝牙4");
        list.add("蓝牙5");
        for(int i=0;i<list.size();i++) {
            RadioButton radioButton = new RadioButton(this);
            final   String num=(String) list.get(i);
            radioButton.setText(num);
            radioGroup.addView(radioButton);
            radioButton.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {
                    Toast.makeText(Lianjie.this,num, Toast.LENGTH_SHORT).show();
                }

            });


        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Lianjie.this, "你点击了按钮1",Toast.LENGTH_LONG).show();

            }
        });
    }
}
