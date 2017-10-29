package com.example.administrator.canol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.canol.blue.BlueToothDevices;
import com.example.administrator.canol.convert.ConvertActivity;
import com.example.administrator.canol.entity.FileName;

import java.io.File;


public class Main extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		Button button3 = (Button) findViewById(R.id.button3);
		Button button4 = (Button) findViewById(R.id.button4);
		Button button5 = (Button) findViewById(R.id.button5);
		Button button6 = (Button) findViewById(R.id.button6);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(Main.this, BlueToothDevices.class);
				startActivity(intent);

			}
		});
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(Main.this, Shezhi.class);
				startActivity(intent);

			}
		});
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(Main.this, Jieshou1.class);
				startActivity(intent);

			}
		});
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(Main.this, Fasong.class);
				Bundle bundle=new Bundle();
				bundle.putString("name","xinping");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(Main.this, Jiazai.class);
				startActivity(intent);

			}
		});
		button6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Main.this, ConvertActivity.class);
				startActivity(intent);
			}
		});
}}
