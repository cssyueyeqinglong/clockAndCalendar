package com.qibei.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/6/23.
 */

public class BasicView extends View {


    public BasicView(Context context) {
        super(context);
    }

    public BasicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(reInitValue(widthMeasureSpec),reInitValue(heightMeasureSpec));
    }

    private int reInitValue(int measureSpec) {
        int res = 100;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            res = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            res = Math.min(res, size);
        }
        return res;
    }
}
