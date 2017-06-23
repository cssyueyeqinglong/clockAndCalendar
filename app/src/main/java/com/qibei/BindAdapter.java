package com.qibei;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/6/21.
 */

public class BindAdapter {
    @BindingAdapter({"app:loadurl"})
    public static void loadImgfromUrl(ImageView iv, String loadurl) {
        Glide.with(iv.getContext()).load(loadurl).into(iv);
    }
}

