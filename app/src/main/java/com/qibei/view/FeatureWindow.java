package com.qibei.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qibei.R;

/**
 * Created by Administrator on 2017/5/22.
 */

public class FeatureWindow extends PopupWindow {

    private Context mContext;
    private View mContentView;
    private RecyclerView mRcv;
    private String[] features;
    private int mWidth;
    private int mHeight;
    private int[] colors = new int[]{R.color.ffdf25, R.color.ff6600, R.color.d0e17d};

    public FeatureWindow(Context context) {
        super(context);
        this.mContext = context;
        mContentView = LayoutInflater.from(context).inflate(R.layout.pop_feature, null);
        caculateWidthAndHeight();
        setContentView(mContentView);
        setHeight(dip2dx(100));
        setWidth(dip2dx(100));

        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
//        setOnDismissListener(new OnDismissListener() {
//            @Override
//            public void onDismiss() {
////                lightsOnOrOff(1.0f);
//                onDismissToDo();
//            }
//        });
        initViews(context);
    }


    private void initViews(Context context) {
        features = context.getResources().getStringArray(R.array.features);
        mRcv = (RecyclerView) mContentView.findViewById(R.id.rcy);
        mRcv.setLayoutManager(new LinearLayoutManager(context));
        mRcv.setAdapter(new FeatureAdapter());
//        lightsOnOrOff(0.3f);
//        bgView.setAlpha(0.3F);
    }

    private class FeatureAdapter extends RecyclerView.Adapter<FeatureHolder> {

        @Override
        public FeatureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_feature_text, parent, false);
            return new FeatureHolder(view);
        }

        @Override
        public void onBindViewHolder(FeatureHolder holder, int position) {
            holder.tv.setText(features[position]);
            holder.tv.setBackgroundColor(mContext.getResources().getColor(colors[position % 3]));
        }

        @Override
        public int getItemCount() {
            return features.length;
        }
    }

    private class FeatureHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public FeatureHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    public int dip2dx(int value) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return (int) (dm.density * value + 0.5f);
    }

    public void lightsOnOrOff(float alpha) {
        WindowManager.LayoutParams attributes = ((Activity) mContext).getWindow().getAttributes();
        attributes.alpha = alpha;
        ((Activity) mContext).getWindow().setAttributes(attributes);
    }

    //获取状态栏高度
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //返回actionbar高度
    public int getActinBarHeight() {
        return ((AppCompatActivity) mContext).getSupportActionBar().getHeight();
    }


    private void caculateWidthAndHeight() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWidth = wm.getDefaultDisplay().getWidth();
        Log.d("size", "getActinBarHeight==" + getActinBarHeight());
        Log.d("size", "getStatusBarHeight==" + getStatusBarHeight());
        mHeight = wm.getDefaultDisplay().getHeight() - getActinBarHeight() - getStatusBarHeight();
    }

}
