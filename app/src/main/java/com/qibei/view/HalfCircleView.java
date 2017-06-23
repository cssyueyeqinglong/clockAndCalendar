package com.qibei.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/6/23.
 */

public class HalfCircleView extends BasicView {

    private Paint mPaint;
    private Paint linePaint;
    private Paint pointPaint;
    private float degree = 0;

    public HalfCircleView(Context context) {
        super(context);
    }

    public HalfCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HalfCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(25);

        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL);

        pointPaint = new Paint();
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(5);
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int radius = Math.min(height, width) * 2 / 3;
        canvas.translate(width / 2, height - 15);

        RectF rectf = new RectF(-radius + 70, -radius + 70, radius - 70, radius - 70);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(rectf, 180f, 60f, true, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(rectf, 180f + 60F, 60f, true, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(rectf, 180f + 60F * 2, 60f, true, mPaint);


        RectF rectF = new RectF(-radius, -radius, radius, radius);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF, 180f, 180f, true, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("起始", -width / 2, 0, mPaint);
        canvas.drawText("安全", -radius+50,-radius*3/4+20, mPaint);
        canvas.drawText("警报", -25, -radius - 30, mPaint);
        canvas.drawText("危险",radius-100, -radius*3/4, mPaint);
        canvas.drawText("终止", radius + 10, 0, mPaint);
        for (int i = 0; i < 9; i++) {
            canvas.save();
            canvas.rotate(18f * (i + 1));
            canvas.drawLine(-radius + 40, 0, -radius, 0, linePaint);
            canvas.restore();
        }


        canvas.drawCircle(0, 0, 10, pointPaint);

        if (degree != 0) {
            canvas.save();
            canvas.rotate(-degree);
            canvas.drawLine(0, 0, radius - 30, 0, pointPaint);
            canvas.restore();
        } else {
            canvas.drawLine(0, 0, radius - 30, 0, pointPaint);
        }


    }

    public void changePointLine(float degree) {
        this.degree = 180f * degree / 100;
        invalidate();
    }
}
