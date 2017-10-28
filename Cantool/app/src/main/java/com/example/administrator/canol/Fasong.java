package com.example.administrator.canol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.administrator.canol.dataRead.BOReadAll;
import com.example.administrator.canol.entity.FileName;
import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.listvieweditview.Sendactivity;

public class Fasong extends Activity {
    private Button selectBoButton;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fasong2);
        selectBoButton=findViewById(R.id.selectBoButton);


        String filename = FileName.filename;
        radioGroup = (RadioGroup) findViewById(R.id.fasongRadioGroup);
        radioGroup.removeAllViews();
        ArrayList<Message> messageArrayList= BOReadAll.readBoAll(filename);
        for(int i=0;i<messageArrayList.size();i++){
            Message mse=messageArrayList.get(i);
            String bo = mse.getBO_() + " "+mse.getId() + " "+mse.getMessageName()
                    + mse.getSeporator() + mse.getNodeName();
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(bo);
            radioGroup.addView(radioButton);
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButtonn;
                radioButtonn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                final String selectText = radioButtonn.getText().toString();
                selectBoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("key", selectText);
                        intent.setClass(Fasong.this, Sendactivity.class);
                        startActivity(intent);
                    }
                });



            }
        });
//    private List<String> list=new ArrayList<>();
//   // private List<String> list1=new ArrayList<>();
//    private Spinner fasong_xiala;
//    //private ArrayAdapter<String> fasong_arr;
//    private EditText fasong_mingling;
//    private EditText fasong_edi;
//    private EditText fasong_yitianjia;
//    private String yitianjia="star";
//    private Button button;
//    private Button button1;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fasong);
//       //1 list1.add("BO1");
//        //1list1.add("BO2");
//        //1list1.add("BO3");
//        fasong_xiala=(Spinner) this.findViewById(R.id.fasong_xiala);
//        fasong_xiala.setOnItemSelectedListener(new ProvOnItemSelectedListener());
//        fasong_mingling=(EditText) this.findViewById(R.id.fasong_mingling);
//        fasong_yitianjia=(EditText) this.findViewById(R.id.fasong_yitianjia);
//        fasong_edi=(EditText) this.findViewById(R.id.fasong_edi);
//        button=(Button) this.findViewById(R.id.fasong_button_tianjia);
//        button1=(Button)this.findViewById(R.id.fasong_button_fasong);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                String num2=fasong_mingling.getText().toString();
//                String num3=fasong_xiala.getSelectedItem().toString();
//                String num=num3+num2;
//                list.add(num);
//
//                Toast toast=Toast.makeText(getApplicationContext(),num,Toast.LENGTH_SHORT);
//                toast.show();
//
//
//                yitianjia=yitianjia+num;
//                yitianjia=yitianjia+"\n";
//
//
//
//                fasong_yitianjia.setText(yitianjia);
//            }
//        });
//        button1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                String fasong = fasong_yitianjia.getText().toString();
//                String num1 = fasong_edi.getText().toString();
//                fasong=fasong+num1;
//                Fasongshixian fasongshixian=new Fasongshixian(fasong);
//                String jieguo=fasongshixian.getFasong();
//
//                Toast toast=Toast.makeText(getApplicationContext(),jieguo,Toast.LENGTH_SHORT);
//                toast.show();
//
//
//            }
//        });
//
//        for(int i=0;i<list.size();i++)
//        {
//
//            yitianjia =yitianjia+(String)list.get(i);
//
//        }
//        yitianjia=yitianjia+"\n";
//        Bundle bundle=this.getIntent().getExtras();
//        String name=bundle.getString("name");
//        fasong_yitianjia.setText(yitianjia);
//       //1 fasong_arr= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
//        //设置样式
//       // 1fasong_arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //加载适配器
//       // 1fasong_xiala.setAdapter(fasong_arr);

    }


//    private class  ProvOnItemSelectedListener implements OnItemSelectedListener{
//        @Override
//        public void onItemSelected(AdapterView<?> adapter,View view,int position,long id) {
//            //获取选择的项的值
//            String sg;
//            String sInfo=adapter.getItemAtPosition(position).toString();
//            if(sInfo.equals("BO_61CDU_4:8CDU")){
//                sg="SG_CDU_HVACACCfg[0|3]:"+"\n"+"SG_CDU_HVACAirCirCfg[0|3]:"+"\n"+"SG_CDU_HVACComfortCfg[0|3]:";
//            }else if(sInfo.equals("BO2")){
//
//                sg="sg1:"+"\n"+"sg2:"+"\n"+"sg3:";
//            }else if(sInfo.equals("BO3")){
//
//                sg="sg1:"+"\n"+"sg2:"+"\n"+"sg3:"+"\n"+"sg4:";
//            }else{
//
//                sg="sg1:"+"\n"+"sg2:"+"\n"+"sg3:"+"\n"+"sg4:"+"\n"+"sg5:";
//            }
//
//
//            fasong_mingling.setText(sg);
//            Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> arg0) {
//            String sInfo="什么也没选！";
//            Toast.makeText(getApplicationContext(),sInfo, Toast.LENGTH_LONG).show();
//
//        }
//    }



}