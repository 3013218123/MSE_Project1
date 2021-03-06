package com.example.administrator.canol;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LEDView extends LinearLayout {

	public TextView timeView;
	public String string="nn";
	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}


	public TextView bgView;
	//字体 中间是占位符
	private static final String FONT_DIGITAL_7 = "fonts" + File.separator
			+ "digital-7.ttf";
//站位不满足2，填写0
	private static final String DATE_FORMAT = "%02d:%02d:%02d";
	//时间延迟
	private static final int REFRESH_DELAY = 500;
//现成
	public  Handler mHandler = new Handler();
	  Runnable mTimeRefresher = new Runnable() {
		@Override
		public void run() {
			Calendar calendar = Calendar.getInstance(TimeZone
					.getTimeZone("GMT+8"));
			final Date d = new Date();
			calendar.setTime(d);
			timeView.setText((String)string);

//					String.format(DATE_FORMAT,
//				calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
//					calendar.get(Calendar.SECOND)));
//		mHandler.postDelayed(this, REFRESH_DELAY);
		}
	};

	@SuppressLint("NewApi")
	public LEDView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public LEDView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LEDView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View view = layoutInflater.inflate(R.layout.ledview, this);
		timeView = (TextView) view.findViewById(R.id.ledview_clock_time);
		bgView = (TextView) view.findViewById(R.id.ledview_clock_bg);
		AssetManager assets = context.getAssets();
		final Typeface font = Typeface.createFromAsset(assets, FONT_DIGITAL_7);
		timeView.setTypeface(font);// 设置字体
		bgView.setTypeface(font);// 设置字体

	}

	public void start() {
		mHandler.post(mTimeRefresher);
	}

	public void stop() {
		mHandler.removeCallbacks(mTimeRefresher);
	}
}
