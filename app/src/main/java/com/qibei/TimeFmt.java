package com.qibei;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qibei.act.TimeClockActivity;
import com.qibei.view.TimeClockView;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/2.
 */

public class TimeFmt extends Fragment {

    private TimeClockView mTimeView;
    private int states;
    private int count;
    private SoundPool mSoundPool;
    private HashMap<Integer, Integer> soundPoolMap;
    public static final int KEY_SOUND_A1 = 1;
    public static final int KEY_SOUND_A2 = 2;
    private String TAG = this.getClass().getSimpleName();
    TimeClockActivity mContext;
    private boolean isSucceed = false;

    public static TimeFmt newInstance() {

        Bundle args = new Bundle();
        TimeFmt fragment = new TimeFmt();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fmt_time, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = (TimeClockActivity) getContext();
        mTimeView = (TimeClockView) view.findViewById(R.id.time_view);
        mTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (states == 0) {//待机中
                    mTimeView.start();
                    states = 1;
                } else if (states == 1) {//计时进行中
                    mTimeView.stop();
                    states = 0;
                } else {

                }
            }
        });

        mTimeView.setOnTimeHalfArrivedLisenter(new TimeClockView.OnTimeHalfArrivedLisenter() {
            @Override
            public void timeHalf(int time) {
                Log.d("time", "times=" + time);
                if (time == 6) {
                    count += 1;
                    states = 0;
                    if (count >= 2) {
                        upDateTime();
                    }
                }
                if (time != 0) {
                    if (time % 2 == 0) {
                        mSoundPool.play(soundPoolMap.get(KEY_SOUND_A1), 1, 1, 0, 0, 1);
                    } else {
                        mSoundPool.play(soundPoolMap.get(KEY_SOUND_A2), 1, 1, 0, 0, 1);
                    }
                }
            }
        });

        mSoundPool = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            }
        });
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(KEY_SOUND_A1, mSoundPool.load(getContext(), R.raw.a3, 1));
        soundPoolMap.put(KEY_SOUND_A2, mSoundPool.load(getContext(), R.raw.a4, 1));
    }

    private void upDateTime() {
        if (!isSucceed) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int today = calendar.get(Calendar.DAY_OF_MONTH);
            DateQueryUtils.upDateTime(mContext, year, month, today, 2);
            mContext.changeCurrentPage();
            isSucceed = !isSucceed;
        }

    }

    public void stopTime() {
        states = 0;
        mTimeView.stop();
    }

}
