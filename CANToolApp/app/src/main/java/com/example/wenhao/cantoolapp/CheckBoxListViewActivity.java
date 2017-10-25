package com.example.wenhao.cantoolapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import trylistviewcheckbox.DataHolder;
import trylistviewcheckbox.ListitemAdapter;

/**
 * Created by wenhao on 2017/10/25.
 */

public class CheckBoxListViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yibiaopanactivity);
        //构造数据
        final List<DataHolder> dataList = new ArrayList<DataHolder>();
        for(int i=0;i<10;i++){
            dataList.add(new DataHolder(" hehe的blog---"+i,""));
        }
        //构造Adapter
        ListitemAdapter adapter = new ListitemAdapter(CheckBoxListViewActivity.this, dataList);
        final ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("tag",""+position+" "+id);
                if(listView.isItemChecked(position)){
                    Log.i("tag","true");
                }else {
                    Log.i("tag","false");
                }
            }
        });
//        //全部选中按钮的处理
//        Button all_sel = (Button)findViewById(R.id.all_sel);
//        Button all_unsel = (Button)findViewById(R.id.all_unsel);
//        all_sel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                for(int i=0;i<dataList.size();i++){
//                    listView.setItemChecked(i, true);
//                }
//            }
//        });
//
//        //全部取消按钮处理
//        all_unsel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                for(int i=0;i<dataList.size();i++){
//                    listView.setItemChecked(i, false);
//                }
//            }
//        });
    }
}
