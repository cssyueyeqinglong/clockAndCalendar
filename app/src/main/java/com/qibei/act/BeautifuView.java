package com.qibei.act;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.qibei.R;
import com.qibei.view.CircleViewThree;
import com.qibei.view.CircleViewTwo;
import com.qibei.view.HalfCircleView;

/**
 * Created by Administrator on 2017/6/23.
 */

public class BeautifuView extends AppCompatActivity {

    private HalfCircleView mHcv;
    private SeekBar mSb;
    private CircleViewTwo mCv2;
    private CircleViewThree mCv3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_view);
        mHcv = (HalfCircleView) findViewById(R.id.hcv);
        mCv2 = (CircleViewTwo) findViewById(R.id.cv2);
        mCv3 = (CircleViewThree) findViewById(R.id.cv3);
        mSb = (SeekBar) findViewById(R.id.sb);
        mSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mHcv.changePointLine(progress);
                mCv2.setDegree(progress);
                mCv3.setDegree(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
