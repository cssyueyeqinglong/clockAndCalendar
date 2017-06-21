package com.qibei;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.qibei.act.BlueToothAct;
import com.qibei.databinding.ActivityListBinding;

import java.util.ArrayList;
import java.util.List;

import static com.qibei.TestAdapter.TEST_TYPE_ONE;
import static com.qibei.TestAdapter.TEST_TYPE_TWO;

public class ListActivity extends AppCompatActivity {

    private ActivityListBinding binding;
    private TestAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.rcv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestAdapter(this);
        List<TestBean> datas = new ArrayList<>();
        datas.add(new TestBean("张三", TEST_TYPE_ONE, 15));
        datas.add(new TestBean("张三sh", TEST_TYPE_ONE, 16));
        datas.add(new TestBean("张三sdfh", TEST_TYPE_ONE, 50));
        datas.add(new TestBean("张三sdhsdjhds", TEST_TYPE_TWO, 15));
        mAdapter.addAll(datas);
        binding.rcv.setAdapter(mAdapter);
        binding.setPresenter(new Presenter());
        binding.setImgUrl("http://img.car-house.cn/Upload/category/thumb/20161219161348395.jpg");
        binding.setBean(new TestBean("caocao", TEST_TYPE_TWO, 20));
        binding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup view = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(view);
                return true;
            }
        });
    }

    public class Presenter {
        public void addItem(View view) {
            mAdapter.addItem();
        }

        public void removeItem(View view) {
            mAdapter.removeItem();
        }

        public void onCheckChangedData(View view, boolean checked) {
            binding.setIsShowImg(checked);
        }

        public void onNextAct(View view) {
            startActivity(new Intent(ListActivity.this, BlueToothAct.class));
        }
    }
}
