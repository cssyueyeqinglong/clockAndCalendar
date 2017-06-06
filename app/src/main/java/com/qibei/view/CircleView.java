package com.qibei.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.qibei.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/5/17.
 */

public class CircleView extends View {
    private Paint mPaintCircle;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintCircle = new Paint();
        mPaintCircle.setColor(getResources().getColor(R.color.c9999FF));//设置颜色
        mPaintCircle.setStrokeWidth(dip2dx(2));//设置线宽
        mPaintCircle.setAntiAlias(true);//设置是否抗锯齿
        mPaintCircle.setStyle(Paint.Style.FILL);//设置绘制风格
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(meatureSize(widthMeasureSpec), meatureSize(heightMeasureSpec));
    }

    private int meatureSize(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        int value = dip2dx(30);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                result = value;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(value, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthTotal = width - getPaddingLeft() - getPaddingRight();
        int heightTotal = height - getPaddingTop() - getPaddingBottom();
        int radius = Math.min(widthTotal, heightTotal) / 2;
        //画出大圆
        canvas.drawCircle(width / 2, height / 2, radius, mPaintCircle);
    }

    public int dip2dx(int value) {
        return (int) (getContext().getResources().getDisplayMetrics().density * value + 0.5f);
    }


    /**
     * 设置成打卡记录
     */
    public void setCardPaint() {
        mPaintCircle.setStyle(Paint.Style.STROKE);//设置绘制风格
        invalidate();
    }

    /**
     * 设置成今日日历
     */
    public void setTodayPaint() {
        mPaintCircle.setStyle(Paint.Style.FILL);//设置绘制风格
        invalidate();
    }
}
