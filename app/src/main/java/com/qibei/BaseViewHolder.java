package com.qibei;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/6/20.
 */

public class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public T getmBinding() {
        return mBinding;
    }

    private T mBinding;

    public BaseViewHolder(T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }
}
