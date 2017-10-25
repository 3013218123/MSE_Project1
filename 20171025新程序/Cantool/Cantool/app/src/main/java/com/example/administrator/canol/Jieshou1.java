package com.example.administrator.canol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.administrator.canol.blue.AppComFun;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;
import com.example.administrator.canol.parse.Parse;
import com.example.administrator.canol.treeview.bean.Bean;
import com.example.administrator.canol.treeview.bean.FileBean;
import com.example.administrator.canol.treeview.tree.Node;
import com.example.administrator.canol.treeview.tree.TreeListViewAdapter;
import com.example.administrator.canol.treeview.tree_view.SimpleTreeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017\10\24 0024.
 */

public class Jieshou1 extends Activity {
    private List<Bean> mDatas = new ArrayList<Bean>();
    private List<FileBean> mDatas2 = new ArrayList<FileBean>();
    private ListView mTree;
    private TreeListViewAdapter mAdapter;
    private Button variousBt;

    private List<String> strings = AppComFun.ltmp;
    //String[] strings =new String[] {"t320880478C2F05A1D29A","t31880300000000000000","t31D80200000000000000","t320880478C2F05A1D29A"};
    String filename = "canmsg-sample.dbc";
    int sonId = 1001;
    int parentId = 1;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            initDatas();
            handler.postDelayed(this, 1000 * 5);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jieshou1);

        variousBt = findViewById(R.id.variousShowButton);
        variousBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Jieshou1.this, Jieshou.class);
                startActivity(intent);
            }
        });
        handler.postDelayed(runnable, 1000 * 5);
        initDatas();
    }

    private void initDatas() {

        mDatas.clear();
        //循环开始
        for (int i = strings.size() - 1; i >= 0; i--) {
            //判断当前字符串的BO类型未出现过
            boolean isOnly = true;
            for (int k = strings.size() - 1; k > i; k--) {
                if (strings.get(i).charAt(0) == 'T') {
                    if (strings.get(i).substring(0, 9).equals(strings.get(k).substring(0, 9)))
                        isOnly = false;
                } else {
                    if (strings.get(i).substring(0, 4).equals(strings.get(k).substring(0, 4)))
                        isOnly = false;
                }

            }
            if (isOnly) {
                String dataStr = strings.get(i);
                ParseData parsedate = Parse.parse(strings.get(i), filename);
                Message message = parsedate.getBO_Mse();
                ArrayList<Signal> signalArrayList = parsedate.getSignals();
                double[] phyArray = parsedate.getPhyArray();
                String type = dataStr.substring(0, 1);//传来的数据类型
                String id = "";
                String totalDD = "";
                int DLC = 0;
                if (type.equals("T")) {//T是扩展型
                    id = dataStr.substring(1, 9);
                    DLC = Integer.parseInt(dataStr.substring(9, 10));
                    totalDD = dataStr.substring(10, 10 + DLC * 2);

                } else if (type.equals("t")) {//t是标准型
                    id = dataStr.substring(1, 4);
                    DLC = Integer.parseInt(dataStr.substring(4, 5));
                    totalDD = dataStr.substring(5, 5 + DLC * 2);
                }
                mDatas.add(new Bean(parentId, 0, "ID: " + id + " Name: " + message.getMessageName() + " DLC: " + DLC + " Data: " + totalDD));
                for (int k = 0; k < signalArrayList.size(); k++) {
                    mDatas.add(new Bean(sonId, parentId, signalArrayList.get(k).getSignalName() + " " + String.valueOf(phyArray[k]) + signalArrayList.get(k).getUnit()));
                    sonId++;

                }
                parentId++;
            }


        }

        mTree = (ListView) findViewById(R.id.jieshou_1);
        try {
            mAdapter = new SimpleTreeAdapter<Bean>(mTree, this, mDatas, 30);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
                        Toast.makeText(getApplicationContext(), node.getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        mTree.setAdapter(mAdapter);

    }

}
