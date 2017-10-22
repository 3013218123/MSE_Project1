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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Shezhi extends Activity {
    private Spinner shezhi_xiala;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);

        shezhi_xiala=(Spinner) this.findViewById(R.id.shezhi_xiala);
        button=(Button) this.findViewById(R.id.shezhi_fasong);

        shezhi_xiala.getSelectedItem();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String num1=shezhi_xiala.getSelectedItem().toString();
                num1=num1+"\r";
                Toast toast=Toast.makeText(getApplicationContext(),num1,Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
