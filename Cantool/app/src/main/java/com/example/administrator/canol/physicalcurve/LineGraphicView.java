package com.example.administrator.canol.physicalcurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;


public class LineGraphicView extends View
{

	private static final int CIRCLE_SIZE = 10;

	private Context mContext;
	//画笔
	private Paint mPaint;
	//当前窗口的一些信息
	private DisplayMetrics dm;
	//线形状
	private LineStyle mStyle = LineStyle.Line;

	private int canvasHeight;
	private int canvasWidth;
	private int bheight = 0;
	private int blwidh;
	private boolean isMeasure = true;

	private int averageValue;
	private int marginTop = 35;
	private int marginBottom = 70;

	//单个曲线的最大值
	private int maxValue;
	//N个曲线的最大值
	private ArrayList<Double> tmaxValue;
	//N个点集
	private List<Point[]> tmPoints;
	//X轴数据
	private ArrayList<String> xRawDatas;
	//N个Y轴数据
	private ArrayList<ArrayList<Double>> tyRawData;
	//单个x坐标
	private ArrayList<Integer> xList = new ArrayList<Integer>();
	//N个x坐标
	private ArrayList<ArrayList<Integer>> txList=new ArrayList<ArrayList<Integer>>();

	public LineGraphicView(Context context) {
		super(context);
	}

	public LineGraphicView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	private void initView() {
		this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (isMeasure) {
			this.canvasHeight = getHeight();
			this.canvasWidth = getWidth();
			if (bheight == 0)
				bheight = (int) (canvasHeight - marginBottom);
			blwidh = dip2px(30);
			isMeasure = false;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.i("tag", 1 + "onDraw");
		//设置画笔的颜色
		mPaint.setColor(Color.rgb(255, 255, 255));
		//绘制X轴
		drawXLine(canvas);
		//绘制Y轴
		drawYLine(canvas);
		//绘制X轴坐标字
		drawxText(canvas);

		//得到多个点集合
		tmPoints = GetPoints();
		if (tmPoints == null)
			return;

		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(dip2px(2.5f));
		mPaint.setStyle(Style.STROKE);

		if (mStyle == LineStyle.Curve) {
			drawScrollLine(canvas);
		} else {
			drawLine(canvas);
		}

		mPaint.setStyle(Style.FILL);

		//画多个点集合
		for (int i = 0; i < tmPoints.size(); i++) {
			Point[] points = tmPoints.get(i);
			for (int j = 0; j < points.length; j++) {
				canvas.drawCircle(points[j].x, points[j].y, CIRCLE_SIZE / 2, mPaint);
			}
		}

		super.onDraw(canvas);

	}

	private void drawXLine(Canvas canvas) {
		canvas.drawLine(blwidh, bheight + marginTop, canvasWidth, bheight + marginTop, mPaint);
	}

	private void drawYLine(Canvas canvas) {
		canvas.drawLine(blwidh, marginTop, blwidh, bheight + marginTop, mPaint);

		if (this.tyRawData == null)
			return;
		if (this.txList.size() != 0)
			this.txList.clear();

		for (int i = 0; i < tyRawData.size(); i++) {
			List<Double> temp = tyRawData.get(i);
			ArrayList<Integer> pList = new ArrayList<Integer>();

			for (int j = 0; j < temp.size(); j++) {
				pList.add(blwidh + (canvasWidth - blwidh) / temp.size() * j);
			}
			txList.add(pList);
		}

	}

	private void drawxText(Canvas canvas) {
		if (this.xRawDatas == null || this.xRawDatas.size() == 0)
			return;
		for (int i = 0; i < xRawDatas.size(); i++) {
			drawText(xRawDatas.get(i), blwidh + (canvasWidth - blwidh) / xRawDatas.size() * i, bheight + dip2px(30), canvas);
		}
	}

	private void drawScrollLine(Canvas canvas) {
//		Point startp = new Point();
//		Point endp = new Point();
//		for (int i = 0; i < mPoints.length - 1; i++)
//		{
//			startp = mPoints[i];
//			endp = mPoints[i + 1];
//			int wt = (startp.x + endp.x) / 2;
//			Point p3 = new Point();
//			Point p4 = new Point();
//			p3.y = startp.y;
//			p3.x = wt;
//			p4.y = endp.y;
//			p4.x = wt;
//
//			Path path = new Path();
//			path.moveTo(startp.x, startp.y);
//			path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
//			canvas.drawPath(path, mPaint);
//		}
	}

	private void drawLine(Canvas canvas) {
		if (this.tmPoints == null || this.tmPoints.size() == 0)
			return;

		for (int j = 0; j < tmPoints.size(); j++) {

			Point[] tmp = tmPoints.get(j);
			Point startp = new Point();
			Point endp = new Point();
			for (int i = 0; i < tmp.length - 1; i++) {
				startp = tmp[i];
				endp = tmp[i + 1];

				canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
			}
		}
	}

	private void drawText(String text, int x, int y, Canvas canvas) {
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(dip2px(12));
		p.setColor(Color.RED);
		p.setTextAlign(Paint.Align.LEFT);
		canvas.drawText(text, x, y, p);
	}

	private List<Point[]> GetPoints() {

		if (this.tmaxValue == null || this.tmaxValue.size() == 0)
			return null;

		List<Point[]> tmpoints = new ArrayList<Point[]>();

		for (int i = 0; i < tyRawData.size(); i++) {

			List<Double> temp = tyRawData.get(i);
			Point[] points = new Point[temp.size()];
			xList = txList.get(i);

			for (int j = 0; j < temp.size(); j++) {

				int ph = bheight - (int) (bheight * (temp.get(j) / tmaxValue.get(i)));

				points[j] = new Point(xList.get(j), ph + marginTop);
			}
			tmpoints.add(points);

		}

		return tmpoints;
	}

	public void SetData(ArrayList<ArrayList<Double>> _tyRawData,ArrayList<String> _xRawData, ArrayList<Double> _tmaxValue , int averageValue) {
		this.xRawDatas = _xRawData;
		this.tyRawData = _tyRawData;
		this.tmaxValue = _tmaxValue;
		this.averageValue = averageValue;
	}

	public void Refresh() {
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						Log.i("tag", 1 + "Refresh");
						invalidate();
					}
				});
			}
		}).start();
	}

	public void setTotalvalue(int maxValue)
	{
		this.maxValue = maxValue;
	}

	public void setPjvalue(int averageValue)
	{
		this.averageValue = averageValue;
	}

	public void setMargint(int marginTop)
	{
		this.marginTop = marginTop;
	}

	public void setMarginb(int marginBottom)
	{
		this.marginBottom = marginBottom;
	}

	public void setMstyle(LineStyle mStyle)
	{
		this.mStyle = mStyle;
	}

	public void setBheight(int bheight)
	{
		this.bheight = bheight;
	}

	private int dip2px(float dpValue)
	{
		return (int) (dpValue * dm.density + 0.5f);
	}

}