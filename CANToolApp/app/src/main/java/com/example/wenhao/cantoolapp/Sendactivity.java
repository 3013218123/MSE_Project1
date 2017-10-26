package com.example.wenhao.cantoolapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import java.util.List;

import listvieweditview.Bean;

import listvieweditview.MyAdapter;

/**
 * Created by wenhao on 2017/10/26.
 */

public class Sendactivity extends AppCompatActivity {
    private static final String TAG = "zbw";
    private static final int DATA_CAPACITY = 10;

    private ListView mListView;
    //private List<String> mList = new ArrayList<String>(DATA_CAPACITY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendlayout);
        mListView = (ListView) findViewById(R.id.list_view);

        List<Bean> list=new ArrayList<>();
        for(int i=0;i<20;i++){
            Bean bean=new Bean(String.valueOf(i),"");
            list.add(bean);
        }
        MyAdapter mAdapter=new MyAdapter(this,list);
        mListView.setAdapter(mAdapter);

//        //填充数据
//        for(int i = 0; i < DATA_CAPACITY; i++) {
//            mList.add("56565656" + i);
//        }
//        for(int i = 0; i < 20; i++) {
//            mList.add("56" + i);
//        }
//        HashMap<Integer, String> hashMap=new HashMap<>();
//        for(int i=0;i<30;i++){
//            hashMap.put(i,null);
//        }
//        //设置Adapter
//        mAdapter = new ListviewEditviewAdapter(this, mList,hashMap);
//        mListView.setAdapter(mAdapter);
    }
}
