package com.example.administrator.canol;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import android.widget.RadioGroup;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.canol.blue.AppComFun;
import com.example.administrator.canol.dataRead.SGRead;
import com.example.administrator.canol.entity.FileName;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;
import com.example.administrator.canol.parse.Parse;
import com.example.administrator.canol.treeview.bean.Bean;
import com.example.administrator.canol.treeview.bean.FileBean;
import com.example.administrator.canol.treeview.tree.Node;
import com.example.administrator.canol.treeview.tree.TreeListViewAdapter;
import com.example.administrator.canol.treeview.tree_view.SimpleTreeAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jiazai extends AppCompatActivity {
    //用一个字符串数组来保存一些城市
    private String[] citise = {"成都", "上海", "北京", "重庆"};
    private Button loadDatabaseButton;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    //真正的字符串数据将保存在这个list中
    private List<String> all;
    String current = "";

    private List<Bean> mDatas = new ArrayList<Bean>();
    private ListView mTree;
    private TreeListViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiazai);
        int firstId=1;
        int secondId = 1000;
        int thirdId = 1000000;
        //将字符数组中的对象导入到list中，才能进行动态控制
        all = new ArrayList<String>();
        File file = new File("storage/sdcard1/db/");
        if (file.isDirectory()) {
            String[] fileList = file.list();
            for (int i = 0; i < fileList.length; i++) {
                //一级DB
                mDatas.add(new Bean(firstId, 0, fileList[i]));
                all.add(fileList[i]);
                File fileReader = new File("storage/sdcard1/db/" + fileList[i]);
                try {
                    BufferedReader br = new BufferedReader(new FileReader(fileReader));//构造一个BufferedReader类来读取文件
                    String s = null;
                    while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                        Pattern p = Pattern.compile("BO_ " + ".*");
                        Matcher m = p.matcher(s);
                        if (m.matches()) {//找到了一条BO
                            String[] BO_Array = s.split(" ");
                            String BO_id = BO_Array[1];
                            String MessageName = BO_Array[2].substring(0, BO_Array[2].length() - 1);
                            int DLC = Integer.parseInt(BO_Array[3]);
                            String NodeName = BO_Array[4];
                            boolean isTypeTrue = true;
                            Message message = new Message(BO_id, MessageName, DLC, NodeName);
                            //二级BO
                            mDatas.add(new Bean(secondId, firstId, message.getBO_() + message.getId() + message.getMessageName()
                                    + message.getSeporator() + message.getNodeName()));


                            ArrayList<Signal> signalArrayList = SGRead.readSG(BO_id, fileList[i]);
                            for (int j = 0; j < signalArrayList.size(); j++) {
                                Signal signal = signalArrayList.get(j);
                                //三级SG
                                mDatas.add(new Bean(thirdId, secondId, signal.getSG_() + signal.getSignalName() + signal.getSeporator() + signal.getStartBit() + " " + signal.getDataLength()
                                        + " " + signal.getArrangeType() + " " + signal.getA() + " " + signal.getB() + " " + signal.getC() + " " + signal.getD() + " " + signal.getUnit() + " " + signal.getNodeName()));
                                thirdId++;

                            }
                            secondId++;

                        }
                    }
                    firstId++;
                    br.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            mTree = (ListView) findViewById(R.id.list_db);
            try {
                mAdapter = new SimpleTreeAdapter<Bean>(mTree, this, mDatas, 0);
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


        adapter = new ArrayAdapter<String>(this, R.layout.myspinner, this.all);
        adapter.setDropDownViewResource(R.layout.myspinner);
        spinner = (Spinner) findViewById(R.id.loadDatabaseSpinner);
        spinner.setAdapter(this.adapter);
        loadDatabaseButton = (Button) findViewById(R.id.loadDatabaseButton);
        loadDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current != "") {
                    FileName.setFilename(current);
                    Toast.makeText(Jiazai.this, "数据库转换成功", Toast.LENGTH_SHORT).show();
                }

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //将textview中这信息变为选择的内容
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                current = arg0.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }
}
