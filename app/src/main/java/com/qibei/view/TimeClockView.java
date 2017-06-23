package com.qibei.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.qibei.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/15.
 * 定时闹钟
 */

public class TimeClockView extends View {
    private int mWidth;
    private int screenWidth;
    private Paint bgPaint;
    private Paint linePaint;
    private Paint secondPaint;
    private Paint textPaint;
    private Paint circlePaint;
    private int radius;
    private int degree = 6;
    private boolean isStart;
    private int totalSeconds = 0;
    public static final int NEED_INVALIDATE = 0X23;
    private Calendar mCalendar;
    private long startTime;
    private int pauseCount = 0;
    private int roundTime = 0;

    public TimeClockView(Context context) {
        super(context);
        initView(context);
    }

    public TimeClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TimeClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        getScreenWidth();

        //背景圆盘
        bgPaint = new Paint();
        bgPaint.setColor(context.getResources().getColor(R.color.eeeeee));
        bgPaint.setStrokeWidth(dip2px(12));
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.STROKE);
        //刻度
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(getResources().getColor(R.color.ffffff));
        linePaint.setStrokeWidth(dip2px(2));
        //秒针
        secondPaint = new Paint();
        secondPaint.setColor(getResources().getColor(R.color.ffffff));
        secondPaint.setStrokeWidth(dip2px(3));
        //设置为圆角
        secondPaint.setStrokeCap(Paint.Cap.ROUND);
        //添加阴影
        secondPaint.setShadowLayer(8, 3, 0, Color.DKGRAY);

        //完成度小圆
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);

        //已完成
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getResources().getColor(R.color.c333333));
        textPaint.setStrokeWidth(dip2px(2));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(dip2px(24));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int totalWidth = screenWidth;
        setMeasuredDimension(totalWidth, totalWidth + dip2px(80));//使控件是一个正方形
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth();
        //将画布移到中央
        canvas.translate(mWidth / 2, mWidth / 2);
        drawBg(canvas);
        drawKeDu(canvas);
        drawSecondLine(canvas);
        drawText(canvas);
        drawBottomCircles(canvas);
    }

    //画闹钟的背景
    private void drawBg(Canvas canvas) {
        canvas.save();

        //画外部圆圈背景
        bgPaint.setMaskFilter(new BlurMaskFilter(dip2px(20), BlurMaskFilter.Blur.OUTER));
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(getResources().getColor(R.color.eeeeee));
        radius = mWidth / 2 - dip2px(40);
        canvas.drawCircle(0, 0, radius, bgPaint);
        //画内部圆圈
        bgPaint.setColor(getContext().getResources().getColor(R.color.c00FFFF));
        bgPaint.setStyle(Paint.Style.FILL);
        RectF recf = new RectF(-radius + dip2px(6), -radius + dip2px(6), radius - dip2px(6), radius - dip2px(6));
        canvas.drawArc(recf, 90f, 180f, true, bgPaint);
        bgPaint.setColor(getContext().getResources().getColor(R.color.D0E17D));
        canvas.drawArc(recf, 270f, 180f, true, bgPaint);
        //画圆心附近两个圈
        bgPaint.setColor(getContext().getResources().getColor(R.color.c333333));
        canvas.drawCircle(0, 0, dip2px(6), bgPaint);
        bgPaint.setColor(getContext().getResources().getColor(R.color.ffffff));
        canvas.drawCircle(0, 0, dip2px(4), bgPaint);
        canvas.restore();
    }

    //画刻度
    private void drawKeDu(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(0, radius - dip2px(25), 0, radius - dip2px(8), linePaint);
            } else {
                canvas.drawLine(0, radius - dip2px(14), 0, radius - dip2px(8), linePaint);
            }
            canvas.rotate(degree);
        }
        canvas.restore();
    }

    //画秒针
    private void drawSecondLine(Canvas canvas) {
        int length = mWidth / 2;
        canvas.save();
        canvas.rotate(degree * roundTime);
        canvas.drawLine(0, length / 5, 0, -length * 3f / 5, secondPaint);
        canvas.restore();

    }

    private void drawText(Canvas canvas) {
        canvas.drawText("已完成", -radius, radius + dip2px(40), textPaint);

    }


    //画底部圆圈
    private void drawBottomCircles(Canvas canvas) {
        canvas.save();
        int circleRadius = (mWidth - dip2px(80) - dip2px(15) * 5) / 12;
        for (int i = 0; i < 6; i++) {
            circlePaint.setColor(getResources().getColor(R.color.eeeeee));
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeWidth(dip2px(2));
            canvas.drawCircle(-radius + 40 + i * (dip2px(15) + circleRadius * 2) + 3, radius + dip2px(80) + 1, circleRadius, circlePaint);
            circlePaint.setStrokeWidth(0);
            circlePaint.setColor(Color.WHITE);
            circlePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(-radius + 40 + i * (dip2px(15) + circleRadius * 2), radius + dip2px(80), circleRadius, circlePaint);
            int mColor;
            if (roundTime >= 30 * (i + 1)) {
                if (i % 2 == 0) {
                    mColor = R.color.D0E17D;
                } else {
                    mColor = R.color.c00FFFF;
                }
            } else {
                mColor = R.color.eeeeee;
            }
            circlePaint.setStrokeWidth(0);
            circlePaint.setColor(getResources().getColor(mColor));
            circlePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(-radius + 40 + i * (dip2px(15) + circleRadius * 2), radius + dip2px(80), circleRadius - 2, circlePaint);
        }
        if (roundTime % 30 == 0) {
            if (mLisenter != null) {
                mLisenter.timeHalf(roundTime / 30);
            }
            if (roundTime / 30 == 6) {
                isStart = false;
                totalSeconds = 0;
                pauseCount = 0;
                roundTime = 0;
            }
        }
        canvas.restore();
    }

    public void start() {
        if (isStart) {
            return;
        }
        isStart = true;
        mCalendar = Calendar.getInstance();
        startTime = mCalendar.getTimeInMillis();
        Log.d("time", "startTime===" + startTime);
        mHandler.sendEmptyMessageDelayed(NEED_INVALIDATE, 1000);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NEED_INVALIDATE:
                    if (isStart) {
                        Date date = new Date();
                        long time = date.getTime();
                        totalSeconds = (int) ((time - startTime) / 1000);
                        roundTime = totalSeconds + pauseCount;
                        Log.d("count", "roundTime==" + roundTime);
                        invalidate();//告诉UI主线程重新绘制
                        mHandler.sendEmptyMessageDelayed(NEED_INVALIDATE, 1000);
                    } else {
                        pauseCount = roundTime;
                    }
                    break;
            }
        }
    };

    public void stop() {
        if (!isStart) {
            return;
        }
        isStart = false;
    }

    private void getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
    }

    private int dip2px(int value) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (value * density + 0.5f);
    }

    private OnTimeHalfArrivedLisenter mLisenter;

    public void setOnTimeHalfArrivedLisenter(OnTimeHalfArrivedLisenter lisenter) {
        this.mLisenter = lisenter;
    }

    public interface OnTimeHalfArrivedLisenter {
        void timeHalf(int time);
    }
}
