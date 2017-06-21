package com.qibei;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/20.
 */

public class TestAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<TestBean> mDatas;
    private LayoutInflater mInflater;
    public static final int TEST_TYPE_ONE = 1;
    public static final int TEST_TYPE_TWO = 2;
    Random random;

    public TestAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = new ArrayList<>();
        random = new Random();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        if (viewType == TEST_TYPE_ONE) {
            binding = DataBindingUtil.inflate(mInflater, R.layout.item_test_one, parent, false);
        } else {
            binding = DataBindingUtil.inflate(mInflater, R.layout.item_test_two, parent, false);
        }
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TestBean item = mDatas.get(position);
        holder.getmBinding().setVariable(BR.item, item);
        holder.getmBinding().executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).type;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addAll(List<TestBean> items) {
        mDatas.addAll(items);
    }

    public void removeItem() {
        int pos = random.nextInt(mDatas.size());
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

    public void addItem() {
        int pos = random.nextInt(mDatas.size());
        mDatas.add(pos, new TestBean("爱车小屋", "lallala", 50));
        notifyItemInserted(pos);
    }
}
