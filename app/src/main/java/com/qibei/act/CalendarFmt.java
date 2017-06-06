package com.qibei.act;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qibei.DateBean;
import com.qibei.DateQueryUtils;
import com.qibei.R;
import com.qibei.RecyclerDiliver;
import com.qibei.TimeAdapter;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class CalendarFmt extends Fragment {
    private RecyclerView mRcv;
    private TextView mTvDate;
    private ImageView mTvLeft, mTvRight;
    private int mYear, mMonth;
    TimeAdapter mAdapter;
    private List<DateBean> mDatas;
    private boolean isShow = false;
    private ListView mFeatureList;
    private View mFlBg;
    private FrameLayout mFlBtn;
    private int[] drawables = new int[]{R.drawable.text_bg_ffdf25, R.drawable.text_bg_ff6600, R.drawable.text_bg_d0e17d};
    public static final int RESQUET_CODE = 0X24;
    public static final String ADD_YEAR = "ADD_YEAR";
    public static final String ADD_MONTH = "ADD_MONTH";
    private TimeClockActivity mContext;

    public static CalendarFmt newInstance() {

        Bundle args = new Bundle();

        CalendarFmt fragment = new CalendarFmt();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fmt_calendar, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = (TimeClockActivity)getContext();
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mRcv = (RecyclerView) view.findViewById(R.id.recycler_view);
        mFlBg = view.findViewById(R.id.fl_bg);
        mFlBtn = (FrameLayout) view.findViewById(R.id.fl_btn);
        mFeatureList = (ListView) view.findViewById(R.id.lv_feature);
        mTvLeft = (ImageView) view.findViewById(R.id.tv_left);
        mTvRight = (ImageView) view.findViewById(R.id.tv_right);

        setViewData();
        initData();
        initEvents();


    }

    private void setViewData() {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mTvDate.setText(mYear + "年" + (mMonth + 1) + "月");
        mRcv.setLayoutManager(new GridLayoutManager(mContext, 7));
        mRcv.addItemDecoration(new RecyclerDiliver(mContext));
        mAdapter = new TimeAdapter(mContext, null);
        mRcv.setAdapter(mAdapter);
        mFeatureList.setAdapter(new ArrayAdapter(mContext, R.layout.item_feature_text, getResources().getStringArray(R.array.features)) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_feature_text, parent, false);
                TextView btn = (TextView) view.findViewById(R.id.tv);
                btn.setBackgroundResource(drawables[position]);
                btn.setText((CharSequence) this.getItem(position));
                return view;
            }
        });
        mFeatureList.setVisibility(View.GONE);
    }

    public void initData() {
        if (mDatas != null) {
            mDatas.clear();
        }
        mDatas = DateQueryUtils.getDateList(mContext, mYear, mMonth);
        mAdapter.clear();
        mAdapter.addAll(mDatas);
    }


    private void initEvents() {
        //半透明背景点击事件
        mFlBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    disShowAnima();
                    isShow = !isShow;
                    mFlBg.setEnabled(false);
                }
            }
        });
        //牙齿点击事件
        mFlBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (isShow) {
                    disShowAnima();
                } else {
                    mFlBg.setEnabled(true);
                    mFlBg.setVisibility(View.VISIBLE);
                    mFeatureList.setVisibility(View.VISIBLE);
                    showBgAnima();
                }
                isShow = !isShow;
            }
        });
        //月份增加点击事件
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMonth == 11) {
                    mYear += 1;
                    mMonth = 0;
                } else {
                    mMonth += 1;
                }
                mTvDate.setText(mYear + "年" + (mMonth + 1) + "月");
                initData();
            }
        });
        //月份减少点击事件
        mTvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMonth == 0) {
                    mYear -= 1;
                    mMonth = 11;
                } else {
                    mMonth -= 1;
                }
                mTvDate.setText(mYear + "年" + (mMonth + 1) + "月");
                initData();
            }
        });
        //日历的点击事件
        mAdapter.setOnItemClickLisenter(new TimeAdapter.OnItemClickLisenter<DateBean>() {
            @Override
            public void onItemClick(View itemView, int position, DateBean item) {
                Log.d("tag", "itemClick:" + position);
                if (item.featureId == 0) {

                } else {
                    Intent intent = new Intent(mContext, FeatureAddAct.class);
                    intent.putExtra("position", item.featureId - 1).putExtra("itemData", item);
                    startActivity(intent);
                }

            }
        });
        //单个功能的点击事件
        mFeatureList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("tag", "itemLci");
                isShow = false;
                disShowAnima();
                Intent intent = new Intent();
                intent.setClass(mContext, FeatureAddAct.class).putExtra("position", position);
                startActivityForResult(intent, RESQUET_CODE);
            }
        });
    }

    private void showBgAnima() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mFlBg, "alpha", 0.0f, 0.3f, 0.6F, 1.0F);
        ObjectAnimator transtate = ObjectAnimator.ofFloat(mFeatureList, "translationX", -dip2dx(100), -100f, 0F);
        ObjectAnimator alphaList = ObjectAnimator.ofFloat(mFlBg, "alpha", 0.0f, 0.3f, 0.6F, 1.0F);
        AnimatorSet set = new AnimatorSet();
        set.play(transtate).with(alphaList).with(alpha);
        set.setDuration(200);
        set.start();
    }

    private void disShowAnima() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mFlBg, "alpha", 1.0f, 0.6f, 0.3F, 0.0F);
        ObjectAnimator transtate = ObjectAnimator.ofFloat(mFeatureList, "translationX", 0f, -100f, -dip2dx(100));
        ObjectAnimator alphaList = ObjectAnimator.ofFloat(mFlBg, "alpha", 1.0f, 0.6f, 0.3F, 0.0F);
        AnimatorSet set = new AnimatorSet();
        set.play(transtate).with(alphaList).with(alpha);
        set.setDuration(200);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFlBg.setVisibility(View.GONE);
                mFeatureList.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();

    }

    private int dip2dx(int value) {
        return (int) (getResources().getDisplayMetrics().density * value + 0.5f);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESQUET_CODE && resultCode == RESQUET_CODE) {
            int year = data.getIntExtra(ADD_YEAR, 0);
            int month = data.getIntExtra(ADD_MONTH, 0);
            if (mMonth == month && mYear == year) {
                initData();
            }
        }
    }
}
