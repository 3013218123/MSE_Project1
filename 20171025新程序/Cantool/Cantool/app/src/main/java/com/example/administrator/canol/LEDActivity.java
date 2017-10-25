package com.example.administrator.canol;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LEDActivity extends Activity {
	private LEDView ledView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_led);
		ledView = (LEDView) findViewById(R.id.led);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ledView.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		ledView.stop();
	}
}