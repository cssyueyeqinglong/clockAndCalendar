package com.qibei.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.qibei.R;

/**
 * Created by Administrator on 2017/6/23.
 * 半圆形渐变view
 */

public class CircleViewTwo extends View {

    private Paint mPaintCircle;
    private float degree;

    public CircleViewTwo(Context context) {
        super(context);
    }

    public CircleViewTwo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.GRAY);//设置颜色
//        mPaintCircle.setStrokeWidth(dip2dx(2));//设置线宽
        mPaintCircle.setAntiAlias(true);//设置是否抗锯齿
        mPaintCircle.setStyle(Paint.Style.FILL);//设置绘制风格
        mPaintCircle.setTextSize(25);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int radius = Math.min(height, width) * 2 / 3;
        canvas.translate(width / 2, height);
        //画背景半圆
        RectF rectF = new RectF(-radius, -radius - 10, radius, radius - 10);
        mPaintCircle.setStyle(Paint.Style.FILL);//设置绘制风格
        mPaintCircle.setColor(Color.GRAY);
        canvas.drawArc(rectF, 180f, 180f, true, mPaintCircle);
        //画转动圆
        mPaintCircle.setColor(Color.BLUE);
        canvas.drawArc(rectF, 180f, 180f / 100 * degree, true, mPaintCircle);

        //画外边界
        mPaintCircle.setStyle(Paint.Style.STROKE);
        RectF rectf = new RectF(-width / 2, -height, width / 2, 0);
        canvas.drawRoundRect(rectf, 10, 10, mPaintCircle);


        String text = degree + "%";
        float v = mPaintCircle.measureText(text);
        Log.d("va", "va=" + v);
        canvas.drawText(text, -v / 2, -80, mPaintCircle);

    }

    public void setDegree(float degree) {
        this.degree = degree;
        invalidate();
    }
}
