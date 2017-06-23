package com.qibei.act;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.qibei.DateBean;
import com.qibei.DateQueryUtils;
import com.qibei.ListActivity;
import com.qibei.R;
import com.qibei.TestBean;
import com.qibei.databinding.ActFeatureAddBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.qibei.act.CalendarFmt.ADD_MONTH;
import static com.qibei.act.CalendarFmt.ADD_YEAR;
import static com.qibei.act.CalendarFmt.RESQUET_CODE;


/**
 * Created by Administrator on 2017/5/24.
 */

public class FeatureAddAct extends AppCompatActivity {
    private int position = 0;
    private String[] titles = new String[]{"预约牙医", "症状记录", "护理"};
    private TextView mTvTitle;
    private TextView mTvFeature;
    private TextView mTv03;
    private TextView mTv04;
    private EditText mTvContent01, mTvContent02;
    private TextView mTvTime;
    private Button mBtnAdd;
    private int[] colors = new int[]{R.color.ffdf25, R.color.ff6600, R.color.d0e17d};
    private String[] features03 = new String[]{"地点", "症状", "护理事项"};
    private String[] features04 = new String[]{"注意事项", "备注", "备注"};
    private int year, month, day;
    private Uri uri;
    private ProgressDialog dialog;
    private DateBean mData;
    private Calendar calendar;
    private SparseArray<String> contents = new SparseArray<>();
    private TestBean tb = new TestBean("测试", "性格", 0);
    private ActFeatureAddBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.
                setContentView(this, R.layout.act_feature_add);
        position = getIntent().getIntExtra("position", 0);
        mData = (DateBean) getIntent().getSerializableExtra("itemData");
        binding.setPosition(position);
        binding.setTitles(titles);
        binding.setPresenter(new Presenter());
        binding.setUser(tb);
        setSupportActionBar(binding.mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        mTvTitle = (TextView) findViewById(R.id.tv_title);
//        if (mData == null) {
//            mTvTitle.setText("添加" + titles[position]);
//        } else {
//            mTvTitle.setText("查看" + titles[position]);
//        }

        //项目名
        mTvFeature = (TextView) findViewById(R.id.tv_feature);
        mTvFeature.setText(titles[position]);
        mTvFeature.setBackgroundColor(getResources().getColor(colors[position]));
        //第二个描述名
        mTv03 = (TextView) findViewById(R.id.tv_feature_03);
        mTv03.setText(features03[position]);
        //第三个描述名
        mTv04 = (TextView) findViewById(R.id.tv_feature_04);
        mTv04.setText(features04[position]);

        //时间选择器
        mTvTime = (TextView) findViewById(R.id.tv_time);
        calendar = Calendar.getInstance();
        mTvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(FeatureAddAct.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        mTvTime.setText(getTime(date));
                    }
                }).setType(new boolean[]{true, true, true, false, false, false})
                        .setSubmitText("确定").setCancelText("取消")
                        .setRange(calendar.get(Calendar.YEAR) - 10, calendar.get(Calendar.YEAR) + 10)
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
            }
        });

        mTvContent01 = (EditText) findViewById(R.id.tv_03);
        mTvContent02 = (EditText) findViewById(R.id.tv_04);
        mBtnAdd = (Button) findViewById(R.id.mBtn);
        if (mData != null) {
            mTvTime.setText(mData.timeDesc);
            mTvContent01.setText(mData.place);
            mTvContent02.setText(mData.desc);
            mTvTime.setEnabled(false);
            mTvContent01.setEnabled(false);
            mTvContent02.setEnabled(false);
            mBtnAdd.setVisibility(View.GONE);
        }
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        year = ca.get(Calendar.YEAR);
        month = ca.get(Calendar.MONTH);
        day = ca.get(Calendar.DAY_OF_MONTH);
        Log.d("time", "year=" + year + ",month=" + month + ",day=" + day);
        return format.format(date);
    }

    private boolean isClickable = true;

    public void commit(View view) {
        if (!isClickable) {
            return;
        }
        final String time = mTvTime.getText().toString().trim();
        final String content01 = mTvContent01.getText().toString().trim();
        final String content02 = mTvContent02.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(this, "请选择时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(content01)) {
            Toast.makeText(this, "请填写" + features03[position], Toast.LENGTH_SHORT).show();
            return;
        }
//        if (TextUtils.isEmpty(content02)) {
//            Toast.makeText(this, "请填写" + features04[position], Toast.LENGTH_SHORT).show();
//            return;
//        }
        dialog = ProgressDialog.show(this, null, "请稍候", true, false);
        isClickable = false;

        if (uri == null) {
            uri = Uri.parse("content://com.qibei.timeeventprovider/timeTable");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                DateQueryUtils.upDateTime(FeatureAddAct.this, uri, year, month, day, position, time, content01, content02);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        isClickable = true;
                        Toast.makeText(FeatureAddAct.this, "添加成功", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        intent.putExtra(ADD_YEAR, year);
                        intent.putExtra(ADD_MONTH, month);
                        setResult(RESQUET_CODE, intent);
                        finish();
                    }
                });
            }
        }).start();
    }

    private Toast toast;

    public class Presenter {


        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tb.setName(s.toString());
        }

        public void onClickRes(View v) {
//            if (toast == null) {
//                toast = Toast.makeText(FeatureAddAct.this, "点击了", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//            }
//            toast.setText("dianji l ");
//            toast.show();
//            Toast.makeText(FeatureAddAct.this, "点击了", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(FeatureAddAct.this, ListActivity.class));
        }

        public void onClickData(TestBean tb) {
            if (toast == null) {
                toast = Toast.makeText(FeatureAddAct.this, "点击了", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
            }
            toast.setText(tb.getName());
            toast.show();
        }
    }
}
