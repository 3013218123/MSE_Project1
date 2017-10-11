package com.example.wenhao.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by wenhao on 2017/10/11.
 */

public class SecondActivity extends Activity {
    private Button bt4;
    private String context="你好 浩哥";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        bt4=findViewById(R.id.button4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();
                data.putExtra("data1",context);
                setResult(1,data);
                finish();
            }
        });
    }
}
