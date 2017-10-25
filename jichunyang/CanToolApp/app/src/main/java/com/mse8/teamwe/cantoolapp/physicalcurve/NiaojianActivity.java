package com.mse8.teamwe.cantoolapp.physicalcurve;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;

import com.mse8.teamwe.cantoolapp.R;

public class NiaojianActivity extends Activity {
	ArrayList<Double> yList;
	LineGraphicView tu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_cancurve);

		initView();
	}

	public void initView() {
		tu = (LineGraphicView) findViewById(R.id.can_curve);

		yList = new ArrayList<Double>();
		yList.add((double) 2.103);
		yList.add(4.05);
		yList.add(6.60);
		yList.add(3.08);
		yList.add(4.32);
		yList.add(2.0);
		yList.add(5.0);

		ArrayList<Double> yList1;

		yList1 = new ArrayList<Double>();
		yList1.add((double) 3.1);
		yList1.add(4.2);
		yList1.add(5.3);
		yList1.add(6.4);
		yList1.add(7.8);
		yList1.add(5.6);
		yList1.add(4.6);

		ArrayList<ArrayList<Double>> tp = new ArrayList<ArrayList<Double>>();
		tp.add(yList);
		tp.add(yList1);


		ArrayList<String> xRawDatas = new ArrayList<String>();
		xRawDatas.add("05-19");
		xRawDatas.add("05-20");
		xRawDatas.add("05-21");
		xRawDatas.add("05-22");
		xRawDatas.add("05-23");
		xRawDatas.add("05-24");
		xRawDatas.add("05-25");
		xRawDatas.add("05-26");
		tu.SetData(tp, xRawDatas, 8, 2);
	}

}
