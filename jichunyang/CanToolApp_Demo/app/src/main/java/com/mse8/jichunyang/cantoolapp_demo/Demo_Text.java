package com.mse8.jichunyang.cantoolapp_demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by JIChunYang on 2017/10/10.
 */

public class Demo_Text extends Activity {

    Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        btnStart=(Button) findViewById(R.id.btn_start_second);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动第二个Activity

                //第一种显示启动的方法
//                Intent intent=new Intent();
//                intent.setClass(Demo_Text.this,Demo_Text2.class);
//                startActivity(intent);

                //第二种显示启动的方法
//                Intent intent1=new Intent();
//                intent1.setClassName(Demo_Text.this,"com.mse8.jichunyang.cantoolapp_demo.Demo_Text2");
//                startActivity(intent1);

                //显示启动的第三种方法
 //               Intent intent2=new Intent();
 //               ComponentName componentName=new ComponentName(Demo_Text.this,Demo_Text2.class);
//                intent2.setComponent(componentName);
//                startActivity(intent2);

                //隐示启动的第一种方法
//                Intent intent3=new Intent("abcd.DemoText_2");
//                startActivity(intent3);

                //隐示启动的第二种方法
                Intent intent4=new Intent();
                intent4.setAction("abcd.DemoText_2");
                startActivity(intent4);
            }
        });

    }
}
