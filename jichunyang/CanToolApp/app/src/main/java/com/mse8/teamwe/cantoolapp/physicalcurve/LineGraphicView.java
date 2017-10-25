package com.mse8.teamwe.cantoolapp.physicalcurve;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


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

	private int maxValue;

	private int averageValue;
	private int marginTop = 35;
	private int marginBottom = 70;

	//点集
	private Point[] mPoints;
	//多个点集
	private List<Point[]> tmPoints;
	//Y轴数据
	private ArrayList<Double> yRawData;
	//多个Y轴数据
	private ArrayList<ArrayList<Double>> tyRawData;
	//X轴数据
	private ArrayList<String> xRawDatas;

	private ArrayList<Integer> xList = new ArrayList<Integer>();
	//多个x坐标
	private ArrayList<ArrayList<Integer>> txList=new ArrayList<ArrayList<Integer>>();

	private int spacingHeight;

	public LineGraphicView(Context context)
	{
		this(context, null);
	}

	public LineGraphicView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	private void initView()
	{
		this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		if (isMeasure)
		{
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



		mPaint.setColor(Color.rgb(255, 255, 255));

		drawAllXLine(canvas);
		drawAllYLine(canvas);

		//得到单个点集合
		//mPoints = getPoints();

		//得到多个点集合
		tmPoints = GetPoints();

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

		for (int i = 0; i < tmPoints.size(); i++)
		{
			Point[] points = tmPoints.get(i);
			for (int j = 0; j < points.length; j++)
			{
				canvas.drawCircle(points[j].x, points[j].y, CIRCLE_SIZE / 2, mPaint);
			}
		}

		//单条曲线
//		for (int i = 0; i < mPoints.length; i++)
//		{
//			canvas.drawCircle(mPoints[i].x, mPoints[i].y, CIRCLE_SIZE / 2, mPaint);
//		}
	}

	private void drawAllXLine(Canvas canvas)
	{
//		for (int i = 0; i < spacingHeight + 1; i++) {
//			canvas.drawLine(blwidh, bheight - (bheight / spacingHeight) * i + marginTop, canvasWidth,
//					bheight - (bheight / spacingHeight) * i + marginTop, mPaint);
//			drawText(String.valueOf(averageValue * i), blwidh / 2, bheight - (bheight / spacingHeight) * i + marginTop,
//					canvas);
//		}
	}

	private void drawAllYLine(Canvas canvas) {
//		for (int i = 0; i < yRawData.size(); i++) {
//			xList.add(blwidh + (canvasWidth - blwidh) / yRawData.size() * i);
//			canvas.drawLine(blwidh + (canvasWidth - blwidh) / yRawData.size() * i, marginTop, blwidh
//					+ (canvasWidth - blwidh) / yRawData.size() * i, bheight + marginTop, mPaint);
//			drawText(xRawDatas.get(i), blwidh / 2 + (canvasWidth - blwidh) / yRawData.size() * i, bheight + dip2px(30),
//					canvas);
//		}

		for (int i = 0; i < tyRawData.size(); i++) {

			List<Double> temp = tyRawData.get(i);
			ArrayList<Integer> pList = new ArrayList<Integer>();

			for (int j = 0; j < temp.size(); j++) {

				pList.add(blwidh + (canvasWidth - blwidh) / temp.size() * j);
			}
			txList.add(pList);

		}

	}

	private void drawScrollLine(Canvas canvas)
	{
		Point startp = new Point();
		Point endp = new Point();
		for (int i = 0; i < mPoints.length - 1; i++)
		{
			startp = mPoints[i];
			endp = mPoints[i + 1];
			int wt = (startp.x + endp.x) / 2;
			Point p3 = new Point();
			Point p4 = new Point();
			p3.y = startp.y;
			p3.x = wt;
			p4.y = endp.y;
			p4.x = wt;

			Path path = new Path();
			path.moveTo(startp.x, startp.y);
			path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
			canvas.drawPath(path, mPaint);
		}
	}

	private void drawLine(Canvas canvas)
	{
		for(int j=0;j<tmPoints.size();j++)
		{
			Point[] tmp=tmPoints.get(j);

			Point startp = new Point();
			Point endp = new Point();
			for (int i = 0; i < tmp.length - 1; i++)
			{
				startp = tmp[i];
				endp = tmp[i + 1];
				canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
			}
		}
	}

	private void drawText(String text, int x, int y, Canvas canvas)
	{
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(dip2px(12));
		p.setColor(Color.RED);
		p.setTextAlign(Paint.Align.LEFT);
		canvas.drawText(text, x, y, p);
	}

	private Point[] getPoints()
	{
		Point[] points = new Point[yRawData.size()];
		for (int i = 0; i < yRawData.size(); i++)
		{
			int ph = bheight - (int) (bheight * (yRawData.get(i) / maxValue));

			points[i] = new Point(xList.get(i), ph + marginTop);
		}
		return points;
	}

	private List<Point[]> GetPoints() {

		List<Point[]> tmpoints = new ArrayList<Point[]>();

		for (int i = 0; i < tyRawData.size(); i++) {

			List<Double> temp = tyRawData.get(i);
			Point[] points = new Point[temp.size()];
			xList=txList.get(i);

			for (int j = 0; j < temp.size(); j++) {

				int ph = bheight - (int) (bheight * (temp.get(j) / maxValue));

				points[j] = new Point(xList.get(j), ph + marginTop);
			}
			tmpoints.add(points);

		}

		return tmpoints;
	}

	public void setData(ArrayList<Double> yRawData, ArrayList<String> xRawData, int maxValue, int averageValue)
	{
		this.maxValue = maxValue;
		this.averageValue = averageValue;
		this.mPoints = new Point[yRawData.size()];
		this.xRawDatas = xRawData;
		this.yRawData = yRawData;
		this.spacingHeight = maxValue / averageValue;
	}

	public void SetData(ArrayList<ArrayList<Double>> _tyRawData,ArrayList<String> xRawData, int maxValue, int averageValue) {
		this.tyRawData = _tyRawData;
		this.maxValue = maxValue;
		this.averageValue = averageValue;
//		this.mPoints = new Point[yRawData.size()];
		this.xRawDatas = xRawData;
		this.spacingHeight = maxValue / averageValue;
		this.yRawData=_tyRawData.get(0);
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