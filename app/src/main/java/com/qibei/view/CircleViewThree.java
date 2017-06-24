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

/**
 * Created by Administrator on 2017/6/23.
 * 环形渐变
 */

public class CircleViewThree extends View {

    private Paint mPaintCircle;
    private float degree;

    public CircleViewThree(Context context) {
        super(context);
    }

    public CircleViewThree(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleViewThree(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.rgb(77, 83, 97));//设置颜色
        mPaintCircle.setStrokeWidth(80);//设置线宽
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
        mPaintCircle.setColor(Color.rgb(77, 83, 97));
        canvas.drawArc(rectF, 180f, 180f, true, mPaintCircle);

        RectF rectF01 = new RectF(-radius + 40, -radius - 10 + 40, radius - 40, radius - 10 - 40);
        //画内边界
        mPaintCircle.setColor(Color.rgb(148, 159, 181));
        canvas.drawArc(rectF01, 180f, 180f, true, mPaintCircle);

        //画转动环
        mPaintCircle.setStrokeWidth(80);//设置线宽
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setColor(Color.rgb(243, 75, 125));
        canvas.drawArc(rectF01, 180f, 180f / 100 * degree, false, mPaintCircle);
        //画外边界
        mPaintCircle.setStrokeWidth(5);//设置线宽
        RectF rectf = new RectF(-width / 2, -height, width / 2, 0);
        canvas.drawRoundRect(rectf, 10, 10, mPaintCircle);


        String text = degree + "%";
        float v = mPaintCircle.measureText(text);
        Log.d("va", "va=" + v);
        mPaintCircle.setStrokeWidth(2);
        canvas.drawText(text, -v / 2, -80, mPaintCircle);

    }

    public void setDegree(float degree) {
        this.degree = degree;
        invalidate();
    }
}
