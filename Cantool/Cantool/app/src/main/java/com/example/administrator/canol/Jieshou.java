package com.example.administrator.canol;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.RadioButton;

import android.widget.RadioGroup;

import com.example.administrator.canol.blue.AppComFun;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;
import com.example.administrator.canol.parse.Parse;

import java.util.ArrayList;
import java.util.List;

public class Jieshou extends AppCompatActivity {


    private Button button;
    private Button button1;
    private Button btn_canlayout;
    private List<String> list = new ArrayList<String>();
    private ArrayList<Signal> signals;
    private String selectText;
    private String datastrr;
    private String filename;
    //private String[] strings;
    private List<String> strings = AppComFun.ltmp;
    private RadioGroup radioGroup;
    int lastLength = 0;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            if (lastLength != strings.size()) Update();
            lastLength = strings.size();
            handler.postDelayed(this, 1000 * 5);// 间隔120秒
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jieshou);

        handler.postDelayed(runnable, 1000 * 5);
        Update();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable); //停止刷新
        super.onDestroy();
    }


    public void Update() {
        button = (Button) findViewById(R.id.jieshou_yibiaopan);
        button1 = (Button) findViewById(R.id.jieshou_wuli);
        btn_canlayout = (Button) findViewById(R.id.btn_can_layout);
        //strings =new String[] {"t320880478C2F05A1D29A","t31880300000000000000","t31D80200000000000000","t320880478C2F05A1D29A"};
        filename = "canmsg-sample.dbc";
        radioGroup = (RadioGroup) findViewById(R.id.jieshou_r1);
        radioGroup.removeAllViews();
        //Parse.parse("t31880300000000000000",filename);
        //循环开始
        for (int i = 0; i < strings.size(); i++) {
            ParseData parsedate;
            parsedate = Parse.parse(strings.get(i), filename);
            Message message = parsedate.getBO_Mse();
            String bo = message.getBO_() + message.getId() + message.getMessageName()
                    + message.getSeporator() + message.getNodeName();
            boolean isOnly = true;
            for (int k = 0; k < i; k++) {
                if (strings.get(i).charAt(0) == 'T') {
                    if (strings.get(i).substring(0, 9).equals(strings.get(k).substring(0, 9)))
                        isOnly = false;
                } else {
                    if (strings.get(i).substring(0, 4).equals(strings.get(k).substring(0, 4)))
                        isOnly = false;
                }

            }
            if (isOnly) {
                RadioButton radioButton = new RadioButton(this);

                radioButton.setText(bo);
                radioGroup.addView(radioButton);
            }


        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButtonn;
                radioButtonn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                selectText = radioButtonn.getText().toString();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("key", selectText);
                        intent.setClass(Jieshou.this, Yibiaopan.class);
                        startActivity(intent);
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("key", selectText);

                        intent.setClass(Jieshou.this, Qushi.class);
                        startActivity(intent);
                    }
                });
                btn_canlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("key", selectText);

                        intent.setClass(Jieshou.this, TableViewActivity.class);
                        startActivity(intent);
                    }
                });


            }
        });

    }

}

