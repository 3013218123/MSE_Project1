package com.example.administrator.canol.convert;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.canol.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhao on 2017/10/29.
 */

public class ConvertActivity extends Activity {
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    String current;//用来存当前选择的String
    //真正的字符串数据将保存在这个list中
    private List<String> all;
    private Button xmlButton;
    private Button jsonButton;
    private EditText convertEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convert);

        convertEditText = findViewById(R.id.convertEditText);
        all = new ArrayList<String>();
        File file = new File("storage/sdcard1/db/");
        if (file.isDirectory()) {
            String[] fileList = file.list();
            for (int i = 0; i < fileList.length; i++) {
                all.add(fileList[i]);
            }
        }
        File file2 = new File("storage/sdcard1/Json/");
        if (file2.isDirectory()) {
            String[] fileList = file2.list();
            for (int i = 0; i < fileList.length; i++) {
                all.add(fileList[i]);
            }
        }
        File file3 = new File("storage/sdcard1/Xml/");
        if (file3.isDirectory()) {
            String[] fileList = file3.list();
            for (int i = 0; i < fileList.length; i++) {
                all.add(fileList[i]);
            }
        }
        adapter = new ArrayAdapter<String>(this, R.layout.myspinner, this.all);
        adapter.setDropDownViewResource(R.layout.myspinner);
        spinner = (Spinner) findViewById(R.id.convertSpinner);
        spinner.setAdapter(this.adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //将textview中这信息变为选择的内容
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                current = arg0.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        xmlButton = findViewById(R.id.xmlConvertButton);
        jsonButton = findViewById(R.id.jsonConvertButton);
        xmlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = convertEditText.getText().toString();
                if (current.indexOf(".dbc") != -1) {  //需要将该dbc文件转换成xml
//                    boolean result = Convert.dbcToXml(current, newName);
//                    if (result) {
//                        Toast.makeText(ConvertActivity.this, "转换成功", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(ConvertActivity.this, "转换失败", Toast.LENGTH_LONG).show();
//                    }
//                    Log.i("tag", String.valueOf(result));
                } else if (current.indexOf(".xml") != -1) {  //需要将该xml文件转换成dbc
//                    boolean result = XmlConvrt.xmlToDbc(current, newName);
//                    if (result) {
//                        Toast.makeText(ConvertActivity.this, "转换成功", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(ConvertActivity.this, "转换失败", Toast.LENGTH_LONG).show();
//                    }
//                    Log.i("tag", String.valueOf(result));
                }
            }
        });

        jsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = convertEditText.getText().toString();
                if (current.indexOf(".dbc") != -1) {  //需要将该dbc文件转换成json
                    boolean result = Convert.dbcToJson(current, newName);
                    if (result) {
                        Toast.makeText(ConvertActivity.this, "转换成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ConvertActivity.this, "转换失败", Toast.LENGTH_LONG).show();
                    }
                    Log.i("tag", String.valueOf(result));
                } else if (current.indexOf(".txt") != -1) {  //需要将该json文件转换成json
                    boolean result = Convert.jsonTodbc(current, newName);
                    if (result) {
                        Toast.makeText(ConvertActivity.this, "转换成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ConvertActivity.this, "转换失败", Toast.LENGTH_LONG).show();
                    }
                    Log.i("tag", String.valueOf(result));
                }
            }
        });


    }
}
