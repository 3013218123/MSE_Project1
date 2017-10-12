package com.example.wenhao.myapplication;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

/**
 * Created by wenhao on 2017/10/11.
 */

public class myActivity extends Activity {
    private EditText testEt;
    private Button testBt;
    private TextView queryTV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3);
        /*
        //打开SQLiteStudio
       SQLiteStudioService.instance().start(this);
        //创建数据库
        SQLiteDatabase db = openOrCreateDatabase("message.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists CANInformation (BO_ text not null, id integer primary key not null, " +
                "MessageName text not null, Separator text not null, DLC integer not null, NodeName text not null)");
    */
    //绑定一波id
        testEt=findViewById(R.id.testEditText);
        testBt=findViewById(R.id.testButton);
        queryTV=findViewById(R.id.textViewqueryResult);


        //点击事件
        testBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String totalStr=testEt.getText().toString();
                if(totalStr.length()!=0){
                    if(totalStr.charAt(0)=='T'){ //扩展型
                        if(totalStr.length()<10) queryTV.setText("输入不合法");
                        int dataLength=0;
                        String id=totalStr.substring(1,9);
                        try{


                            dataLength=Integer.parseInt(totalStr.substring(9,10));
                            String a2=dataLength+"";
                            Log.i("tag",a2);
                        }catch (Exception e){
                            queryTV.setText("输入不合法");
                        }
                        String [] data=new String [dataLength];
                        if(dataLength*2+10==totalStr.length()){//位数输入合法
                            for(int i=10;i<totalStr.length();i=i+2){
                                data[(i-10)/2]=hexString2binaryString(totalStr.substring(i,i+2));
                            }
                            String m="";
                            for(int i=0;i<dataLength;i++){
                                m=m+data[i]+"\n";
                                queryTV.setText(m);
                            }



                        }else {
                            queryTV.setText("输入不合法");
                        }
                    }else if(totalStr.charAt(0)=='t'){//标准型

                    }else{//输入错误
                        queryTV.setText("输入不合法");
                    }
                }else{
                    queryTV.setText("请输入待解析字符串");
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        SQLiteStudioService.instance().stop();
        super.onDestroy();
    }
    public static String hexString2binaryString(String hexString)
    {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++)
        {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(hexString
                    .substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }
}
