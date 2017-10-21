package com.mse8.jichunyang.cantoolapp_demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by JIChunYang on 2017/10/10.
 */

public class Demo_Text2 extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Button btnFinish=(Button) findViewById(R.id.btn_finish_self);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭自己

                Demo_Text2.this.finish();
            }
        });
    }
}
