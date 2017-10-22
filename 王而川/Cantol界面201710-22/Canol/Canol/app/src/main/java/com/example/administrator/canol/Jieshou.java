package com.example.administrator.canol;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.RadioButton;

import android.widget.RadioGroup;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Jieshou extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button button;
    private Button button1;
    private List<String> list=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jieshou);
        button=(Button) findViewById(R.id.jieshou_yibiaopan);
        button1=(Button) findViewById(R.id.jieshou_wuli);
            radioGroup = (RadioGroup) findViewById(R.id.jieshou_r1);

        list.add("BOSG1211SG288SG388768");
        list.add("BOSG12928SG29898SG39879999900");
        for(int i=0;i<list.size();i++) {
            RadioButton radioButton=new RadioButton(this);
            final String num = (String) list.get(i);
            radioButton.setText(num);
            radioGroup.addView(radioButton);
            radioButton.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                    Toast.makeText(Jieshou.this, num, Toast.LENGTH_SHORT).show();

                }

            });
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Jieshou.this,Yibiaopan.class);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Jieshou.this, Qushi.class);
                startActivity(intent);
            }
        });
    }

}