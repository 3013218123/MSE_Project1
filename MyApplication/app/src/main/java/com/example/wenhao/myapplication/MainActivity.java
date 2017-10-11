package com.example.wenhao.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button dianButton;
    private EditText et;
    private AutoCompleteTextView autoCompleteTextView;
    String [] res=new String[]{"beijing1","beijing2","beijing3","shanghai1","shanghai2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dianButton=(Button)findViewById(R.id.button2);
        et=(EditText) findViewById(R.id.editText2);
        dianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setAlpha(0.1f);
                et.setText("瓜皮！姓名输了吗？");
            }
        });

        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,res);
        autoCompleteTextView.setAdapter(adapter);
    }



}
