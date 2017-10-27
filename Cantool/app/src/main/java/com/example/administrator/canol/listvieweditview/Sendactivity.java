package com.example.administrator.canol.listvieweditview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.canol.Fasong;
import com.example.administrator.canol.R;
import com.example.administrator.canol.blue.BlueToothDevices;
import com.example.administrator.canol.blue.Ct_BtSocket;
import com.example.administrator.canol.dataRead.SGRead;
import com.example.administrator.canol.entity.Signal;
import com.example.administrator.canol.parse.ReverseParse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhao on 2017/10/27.
 */

public class Sendactivity extends Activity {

    private Ct_BtSocket ceshi = BlueToothDevices.ct_btSocket;

    private ListView mListView;
    private Button fasongButton;
    double userPhyArray[];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendlayout);
        String bo = (String) getIntent().getSerializableExtra("key");
        String[] boInfArray = bo.split(" ");
        final String boId = boInfArray[1];
        final String fileName = "canmsg-sample.dbc";
        Log.i("tag", boId);
        mListView = (ListView) findViewById(R.id.list_view);
        final List<Bean> list = new ArrayList<>();
        ArrayList<Signal> signalArrayList = SGRead.readSG(boId, fileName);
        for (int i = 0; i < signalArrayList.size(); i++) {
            Signal signal = signalArrayList.get(i);
            String name = signal.getSignalName() + "\n范围：" + signal.getC() + "--" + signal.getD();
            Bean bean = new Bean(name, "");
            list.add(bean);
        }
        MyAdapter mAdapter = new MyAdapter(this, list);
        mListView.setAdapter(mAdapter);


        fasongButton = findViewById(R.id.fasongButton);
        fasongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhyArray = new double[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    Bean bean = list.get(i);
                    Log.i("tag", bean.getName() + " " + bean.getInput());
                    try {
                        userPhyArray[i] = Double.parseDouble(bean.getInput());
                    } catch (Exception e) {

                    }

                }

                String reverseParseStr = ReverseParse.reverseParse(boId, userPhyArray, fileName);
                Log.i("tag", reverseParseStr);

                ceshi.CtSendMessage(reverseParseStr);

                Intent intent = new Intent(Sendactivity.this, Fasong.class);
                startActivity(intent);

            }
        });
    }
}
