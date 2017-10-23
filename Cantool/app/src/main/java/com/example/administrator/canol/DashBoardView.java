package com.example.administrator.canol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import java.math.BigDecimal;

import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by JIChunYang on 2017/10/13.
 */

public class DashBoardView extends View
{
    private int width;
    private int height;

    private Paint outerCirclePaint;// 外层圆的画笔
    private Paint outerCirclePaint1;//外层圆的画笔2
    private Paint outerCirclePaint2;//外层元的画笔2
    private Paint innerCirclePaint;//内层元的画笔

    private Paint linePaint;//线段画笔
    private Paint arrowPaint;//指针画笔

    private Paint textPaint;//标注文字
    private Paint textPaint2;//目标指针文字

    private Path outerCirclePath;//外层圆的Path
    private Path innerCirclePath;//内层圆的Path

    private Path linePath;//线段的Path
    private Path arrowPath;//指针的Path

    //private Path measureArrowPath;//arrowPath借助该Path来保持一定的长度

    private RectF outRectF;//绘制外层元，通过四个坐标参数来确定
    private RectF innerRectF;//用于绘制内层元

    private int count=10;//画10根线

    private static int outerR=100;//外部圆环的半径
    private static int innerR=(int)(outerR*0.9f);//内部圆环的半径

    private int shortageAngle=60;//缺失部分的角度
    private int startAngle;//开始的角度
    private int sweepAngle;//扫过的角度
    private int endAngle;//结束的角度

    private float[] leftEndPoint;//左侧边界的坐标
    private float[] rightEndPoint;//右侧边界的坐标
    private float leftEndTan;//左侧边界的tan值
    private float rightEndTan;//右侧边界的tan值

    private float nowX=0;//触摸位置的横坐标
    private float nowY=0;//触摸位置的纵坐标

    private static float percent=0.9f;//指针与内层圆的比值
    private float arrowLength=innerR*percent;//指针的长度

    private double aimSweepAngel=0;//起始角度，起点
    private float startValue=0;//默认起始值
    private float endValue=100;//默认终止值
    private int textCount=count;// 标注文字个数，默认

    //private PathMeasure arrowMeasure;//用于指针的测量

    private boolean isColorful=true;
    private double aimValue=startValue;


    public DashBoardView(Context context) {
        super(context);

        initPaint();
        initAngle();
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initAngle();
    }

    public DashBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initAngle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int odd, int old)
    {
        super.onSizeChanged(w, h, odd, old);

        width=w;
        height =h;

        //指针开始指向正上方

        nowX=0;
        nowY=-1;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.translate(width/2, height /2);

        drawOuterCircle(canvas);
        drawInnerCircle();
        drawLine(canvas);
        drawArrow(canvas);
        drawText(canvas);
        drawAimText(canvas);

        //下面绘制仪表盘的各个组件
    }

    //绘制外层圆圈
    private void drawOuterCircle(Canvas canvas)
    {
        outerCirclePath=new Path();
        if(outRectF==null)
        {
            outRectF=new RectF(-outerR,-outerR,outerR,outerR);
        }
        outerCirclePath.addArc(outRectF,startAngle,sweepAngle);

        canvas.drawArc(outRectF,startAngle,sweepAngle,false,outerCirclePaint);

        if(isColorful)
        {
            canvas.drawArc(outRectF,startAngle,sweepAngle/5,false,outerCirclePaint);
            canvas.drawArc(outRectF,startAngle+sweepAngle/5,3*sweepAngle/5,false,outerCirclePaint1);
            canvas.drawArc(outRectF,startAngle+sweepAngle/5+3*sweepAngle/5,sweepAngle/5,false,outerCirclePaint2);
        }
    }

    private void drawInnerCircle()
    {
        innerCirclePath=new Path();
        if(innerRectF==null)
        {
            innerRectF=new RectF(-innerR,-innerR,innerR,innerR);
        }
        innerCirclePath.addArc(innerRectF,startAngle,sweepAngle);
    }

    private void drawLine(Canvas canvas)
    {
        linePath=new Path();

        //外层圆的测量
        PathMeasure outMeasure=new PathMeasure(outerCirclePath,false);
        float outlength=outMeasure.getLength();
        float[] outPos=new float[2];

        //内侧圆的测量
        PathMeasure inMeasure=new PathMeasure(innerCirclePath,false);
        float inlength=inMeasure.getLength();
        float[] inPos=new float[2];

        if(leftEndPoint==null)
        {
            leftEndPoint=new float[2];

            inMeasure.getPosTan(0,leftEndPoint,null);

            leftEndPoint[0]=leftEndPoint[0]*percent;
            leftEndPoint[1]=leftEndPoint[1]*percent;

            leftEndTan=leftEndPoint[1]/leftEndPoint[0];
        }

        if(rightEndPoint==null)
        {
            rightEndPoint=new float[2];

            inMeasure.getPosTan(inlength,rightEndPoint,null);

            rightEndPoint[0]=rightEndPoint[0]*percent;
            rightEndPoint[1]=rightEndPoint[1]*percent;

            rightEndTan=rightEndPoint[1]/rightEndPoint[0];
        }

        for(int i=0;i<=count;i++)
        {
            float outNowLength=outlength/(count*1.0f)*i;
            outMeasure.getPosTan(outNowLength,outPos,null);

            float inNowLength=inlength/(count*1.0f)*i;
            inMeasure.getPosTan(inNowLength,inPos,null);

            linePath.moveTo(outPos[0],outPos[1]);
            linePath.lineTo(inPos[0],inPos[1]);

            if(isColorful)
            {
                if(i<=count/5)
                {
                    linePaint.setColor(Color.GREEN);
                }
                else if (i>count-count/5)
                {
                    linePaint.setColor(Color.RED);
                }
                else
                {
                    linePaint.setColor((Color.BLUE));
                }
            }

            canvas.drawLine(outPos[0],outPos[1],inPos[0],inPos[1],linePaint);
        }
    }

    //绘制指针
    private void drawArrow(Canvas canvas)
    {
        double f=Math.toRadians(aimSweepAngel+startAngle);
        nowX=(float)Math.cos(f)*arrowLength;
        nowY=(float)Math.sin(f)*arrowLength;

        arrowPath=new Path();
        arrowPath.reset();

        arrowPath.moveTo(0,0);
        arrowPath.lineTo(nowX,nowY);

        canvas.drawPath(arrowPath,arrowPaint);
    }

    //绘制指针指向的值
    private void drawAimText(Canvas canvas)
    {
        canvas.drawText(formatDouble(aimValue)+"",-10,outerR,textPaint2);
    }

    //绘制仪表盘刻度值
    private void drawText(Canvas canvas)
    {
        float angle = startAngle;
        double value = startValue;

        for(int i=0;i<=textCount;i++)
        {
            angle=startAngle+sweepAngle/textCount*i;
            value=startValue+formatFloat((endValue-startValue)/textCount)*i;

            if(isColorful)
            {
                if(i<=count/5)
                {
                    textPaint.setColor(Color.GREEN);
                }
                else if (i>count-count/5)
                {
                    textPaint.setColor(Color.RED);
                }
                else
                {
                    textPaint.setColor((Color.BLUE));
                }
            }

            daftest(canvas, angle, value + "");
        }
    }

    private void daftest(Canvas canvas, double angle, String value)
    {
        double f = Math.toRadians(angle);
        float x1 = (float) Math.cos(f) * (innerR - 5);
        if (x1 > 0) {
            x1 -= 26;
        } else if (x1 == 0) {
            x1 -= 10;
        }
        float y1 = (float) Math.sin(f) * (innerR - 5);
        canvas.drawText(value + "", x1, y1, textPaint);
    }


    //设置仪表盘刻度个数
    public void setTextCount(int n)
    {
        textCount=n;
    }

    //设置起始值
    public void setStartingValue(float startingValue)
    {
        this.startValue = startingValue;
    }

    //设置终止值
    public void setEndValue(float endValue)
    {
        this.endValue = endValue;
    }

    //根据值转换成角度 再转换成坐标
    public void setArrowData(double f)
    {
        aimValue = f;
        double percent = (aimValue - startValue) / (endValue - startValue);
        double angel = percent * sweepAngle;
        aimSweepAngel = angel;
    }

    //根据shortageAngle调整圆弧的角度
    private void initAngle()
    {
        sweepAngle = 360 - shortageAngle;
        startAngle = 90 + shortageAngle / 2;
        endAngle = 90 - shortageAngle / 2;
    }

    private float formatFloat(float f)
    {
        BigDecimal bigDecimal = new BigDecimal(f);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    private double formatDouble(double d)
    {
        BigDecimal bigDecimal = new BigDecimal(d);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //初始化画笔
    private void initPaint()
    {
        if (outerCirclePaint == null) {
            outerCirclePaint = new Paint();
            outerCirclePaint.setStyle(Paint.Style.STROKE);
            outerCirclePaint.setStrokeWidth(4);
            outerCirclePaint.setColor(Color.GREEN);
            outerCirclePaint.setAntiAlias(true);//抗锯齿
        }
        if (outerCirclePaint1 == null) {
            outerCirclePaint1 = new Paint();
            outerCirclePaint1.setStyle(Paint.Style.STROKE);
            outerCirclePaint1.setStrokeWidth(4);
            outerCirclePaint1.setColor(Color.BLUE);//blue
            outerCirclePaint1.setAntiAlias(true);//抗锯齿
        }
        if (outerCirclePaint2 == null) {
            outerCirclePaint2 = new Paint();
            outerCirclePaint2.setStyle(Paint.Style.STROKE);
            outerCirclePaint2.setStrokeWidth(4);
            outerCirclePaint2.setColor(Color.RED);//
            outerCirclePaint2.setAntiAlias(true);//抗锯齿
        }
        if (innerCirclePaint == null) {
            innerCirclePaint = new Paint();
            innerCirclePaint.setStyle(Paint.Style.STROKE);
//          outerCirclePaint.setColor(Color.BLACK);
            innerCirclePaint.setColor(Color.BLUE);
            innerCirclePaint.setAntiAlias(true);//抗锯齿
        }
        if (linePaint == null) {
            linePaint = new Paint();
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(2);
            linePaint.setColor(0xff1d8ffe);
            linePaint.setAntiAlias(true);
        }
        if (arrowPaint == null) {
            arrowPaint = new Paint();
            arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            arrowPaint.setColor(Color.BLUE);
            arrowPaint.setStrokeWidth(2);
            arrowPaint.setAntiAlias(true);
        }
        if (textPaint == null) {
            textPaint = new Paint();
            textPaint.setStyle(Paint.Style.STROKE);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(12);
            textPaint.setStrokeWidth(1);
            textPaint.setAntiAlias(true);
        }
        if (textPaint2 == null) {
            textPaint2 = new Paint();
            textPaint2.setStyle(Paint.Style.STROKE);
            textPaint2.setColor(Color.CYAN);
            textPaint2.setTextSize(18);
            textPaint2.setStrokeWidth(1);
            textPaint2.setAntiAlias(true);
        }
    }

}
