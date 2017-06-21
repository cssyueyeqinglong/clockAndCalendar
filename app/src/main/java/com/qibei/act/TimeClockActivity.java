package com.qibei.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.qibei.Base64;
import com.qibei.R;
import com.qibei.TimeFmt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class TimeClockActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private ViewPager mFlContent;
    private List<Fragment> datas = new ArrayList<>();
    private TextView mTvTitle;
    private TimeFmt timeFmt;
    private CalendarFmt calendarFmt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_clock);
        mToolBar = (Toolbar) findViewById(R.id.m_toolbar);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mFlContent = (ViewPager) findViewById(R.id.fl_content);
        timeFmt = TimeFmt.newInstance();
        calendarFmt = CalendarFmt.newInstance();
        datas.add(timeFmt);
        datas.add(calendarFmt);
        mFlContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return datas.get(position);
            }

            @Override
            public int getCount() {
                return datas.size();
            }
        });
        mFlContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 0) {
                    timeFmt.stopTime();
                }
                mTvTitle.setText(getResources().getString(position == 0 ? R.string.time_title : R.string.main_title));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    public void changeCurrentPage() {
        mFlContent.setCurrentItem(1);
        calendarFmt.initData();
    }
}
