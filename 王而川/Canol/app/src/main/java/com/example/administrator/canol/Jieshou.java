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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Jieshou extends Activity {
    private  LinearLayout ll_jieshou;
    private RadioGroup radioGroup;
    private RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jieshou);
        LinearLayout ll_jieshou=(LinearLayout)findViewById(R.id.ll);
        radioGroup=(RadioGroup)findViewById(R.id.jieshou_radiogroup);
        





    }
}