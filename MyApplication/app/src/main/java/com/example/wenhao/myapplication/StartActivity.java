package com.example.wenhao.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by wenhao on 2017/10/11.
 */

public class StartActivity extends Activity {
    private Button bt1;
    private Button bt2;
    private TextView tv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);
        bt1=(Button) findViewById(R.id.button3);
        bt2=findViewById(R.id.button);
        tv1=findViewById(R.id.textView);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StartActivity.this,SecondActivity.class);
                startActivity(i);

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StartActivity.this,SecondActivity.class);
                startActivityForResult(i,1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1){
            tv1.setText(data.getStringExtra("data1"));
        }
    }
}
