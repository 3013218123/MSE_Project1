package com.example.administrator.canol.tableview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.canol.blue.AppComFun;

/**
 * Created by JIChunYang on 2017/10/24.
 */

public class TableView extends ViewGroup {
    private static final int STARTX = 0;// 起始X坐标
    private static final int STARTY = 0;// 起始Y坐标
    private static final int BORDER = 2;// 表格边框宽度

    private int mRow;// 行数
    private int mCol;// 列数

    private int[][] pdata;

    private TextView[][] tv=new TextView[8][8];

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int[] key = (int[]) msg.obj;

            switch (msg.what) {
                case AppComFun._STATUS_CONNECT:
                    CanSignalLayout(key);
                    break;
            }
        }
    };

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRow = 9;// 默认行数为3
        this.mCol = 9;// 默认列数为3

        this.pdata = new int[mRow][mCol];
        // 添加子控件
        this.addOtherView(context);
    }

    public TableView(Context context, int row, int col) {
        super(context);
        if (row > 20 || col > 20) {
            this.mRow = 20;// 大于20行时，设置行数为20行
            this.mCol = 20;// 大于20列时，设置列数为20列
        } else if (row == 0 || col == 0) {
            this.mRow = 8;
            this.mCol = 8;
        } else {
            this.mRow = row;
            this.mCol = col;
        }

        this.pdata = new int[mRow][mCol];
        // 添加子控件
        this.addOtherView(context);
    }

    public void addOtherView(Context context) {
        Calculation();

        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mCol; j++) {
                TextView view = new TextView(context);

                if (i == 0 && j == 0) {
                    view.setText("");
                } else {
                    view.setText(String.valueOf(pdata[i][j]));
                }

                if(i>=1&&j>=1) {
                    tv[i - 1][j - 1] = view;
                }

                view.setTextColor(Color.rgb(0, 0, 0));
                view.setGravity(Gravity.CENTER);

                view.setBackgroundColor(Color.rgb(255, 255, 255));

                this.addView(view);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(BORDER);
        paint.setColor(Color.rgb(79, 129, 189));
        paint.setStyle(Paint.Style.STROKE);
        // 绘制外部边框
        canvas.drawRect(STARTX, STARTY, getWidth() - STARTX, getHeight() - STARTY, paint);
        // 画列分割线
        for (int i = 1; i < mCol; i++) {
            canvas.drawLine((getWidth() / mCol) * i, STARTY, (getWidth() / mCol) * i, getHeight() - STARTY, paint);
        }
        // 画行分割线
        for (int j = 1; j < mRow; j++) {
            canvas.drawLine(STARTX, (getHeight() / mRow) * j, getWidth() - STARTX, (getHeight() / mRow) * j, paint);
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x = STARTX + BORDER;
        int y = STARTY + BORDER;
        int i = 0;
        int count = getChildCount();
        for (int j = 0; j < count; j++) {
            View child = getChildAt(j);
            child.layout(x, y, x + getWidth() / mCol - BORDER * 2, y + getHeight() / mRow - BORDER * 2);
            if (i >= (mCol - 1)) {
                i = 0;
                x = STARTX + BORDER;
                y += getHeight() / mRow;
            } else {
                i++;
                x += getWidth() / mCol;
            }
        }
    }

    public void CanSignalLayout(int[] key) {

        for (int k = 0; k < key.length; k++) {
            int cc = key[k];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    TextView child = tv[i][j];
                    String tmp = child.getText().toString();
                    int tp = Integer.parseInt(tmp);
                    if (tp == cc) {
                        child.setBackgroundColor(Color.rgb(255, 0, 0));
                        break;
                    }
                }
            }
        }

    }

    private int[][] Calculation() {
        int[][] temp = new int[mRow][mCol];

        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mCol; j++) {
                temp[i][j] = (i + 1) * mRow - j - 1;
            }
        }

        pdata = temp;

        for (int i = 1; i < mRow; i++) {
            for (int j = 1; j < mCol; j++) {
                pdata[i][j] = temp[i][j] - 9 - (i - 1);
            }
        }

        for (int i = 1; i < mCol; i++) {
            pdata[i][0] = i - 1;
        }

        return pdata;
    }

}
