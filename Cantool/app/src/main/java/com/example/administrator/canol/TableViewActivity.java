package com.example.administrator.canol;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.canol.blue.AppComFun;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.ParseData;
import com.example.administrator.canol.entity.Signal;
import com.example.administrator.canol.parse.LocationMatric;
import com.example.administrator.canol.parse.Parse;
import com.example.administrator.canol.tableview.TableView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIChunYang on 2017/10/26.
 */

public class TableViewActivity extends Activity {

    private TableView _tableview;
    private List<String> strings= AppComFun.ltmp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ui_tableview);

        this._tableview = (TableView) findViewById(R.id.table_view);

        int[] tmp = {18, 17, 16, 31, 30, 29, 28};
        this._tableview.CanSignalLayout(tmp);
        String bo = (String) getIntent().getSerializableExtra("key");
        String filename = "canmsg-sample.dbc";

        for (int i = strings.size() - 1; i >= 0; i--) {
            ParseData parsedate;
            parsedate = Parse.parse(strings.get(i), filename);
            Message message = parsedate.getBO_Mse();
            String bo_current = message.getBO_() + message.getId() + message.getMessageName()
                    + message.getSeporator() + message.getNodeName();
            if (bo.equals(bo_current)) {
                String[] dataMatricStr=parsedate.getDataMatric();
                int[][] colorMatric= LocationMatric.returnColorMatric(message.getId(),filename);
                int [][] dataMatric=new int[8][8];
                for(int r=0;r<8;r++){
                    for (int c=0;c<8;c++){
                        dataMatric[r][c]=Integer.valueOf(dataMatricStr[r].substring(7-c,8-c));
                    }
                }





//if判断
                break;
            }
//for循环的结束
        }
    }
}
