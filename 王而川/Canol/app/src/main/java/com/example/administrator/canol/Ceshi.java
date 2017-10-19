package com.example.administrator.canol;








        import android.os.Bundle;
        import android.app.Activity;
        import android.graphics.Color;
        import android.view.Menu;
        import android.widget.Button;
        import android.widget.RelativeLayout;
        import android.widget.RelativeLayout.LayoutParams;
        import android.widget.TextView;



public class Ceshi extends Activity {



    private Button btn1;
    private TextView txt1;
    private RelativeLayout re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//初始化
        init();

    }

    private void init(){



//实例控件
        btn1 = new Button(getApplicationContext());
//设置btn1显示内容
        btn1.setText("button");
//实例布局
        re = new RelativeLayout(getApplicationContext());
//设置布局的背景颜色
        re.setBackgroundColor(Color.BLUE);
//设置一个属性，在java布局中和xml不同的是，xml的属性是设置在控件中，而Java是事先设置，然后再把它添加到控件中去
//这几行代码的对应
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
        RelativeLayout.LayoutParams layte = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
//这行代码对应xml中的android:layout_centerHorizontal="true"
        layte.addRule(RelativeLayout.CENTER_HORIZONTAL);
/*
* 重要！button实例化好了，它的布局规则设置好了
* 这时把button和它的规则放入父布局中
*/
        re.addView(btn1, layte);
/*
* 重要，取消MainActivity中设置的布局文件
* （删除：setContentView(R.layout.activity_main);）
* 添加如下代码，不再显示activity_main，而是用纯java代码编写的re
*/
        setContentView(re);
    }



}