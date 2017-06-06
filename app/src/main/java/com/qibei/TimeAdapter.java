package com.qibei;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qibei.view.CircleView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    private int[] colors = new int[]{R.color.ffffff, R.color.ffdf25, R.color.ff6600, R.color.d0e17d};
    private Context mContext;
    private List<DateBean> mDatas;

    public TimeAdapter(Context context, List<DateBean> datas) {
        this.mContext = context;
        mDatas = datas;
    }

    @Override
    public TimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_day, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TimeViewHolder holder, final int position) {
        final DateBean item = mDatas.get(position);
        int day = mDatas.get(position).day;
        holder.cv.setVisibility(View.GONE);
        if (day == 0) {
            holder.tv.setText("");
            if (!TextUtils.isEmpty(item.weekTime)) {
                holder.tv.setText(item.weekTime);
            }
        } else {
            holder.tv.setText("" + day);
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int today = calendar.get(Calendar.DAY_OF_MONTH);
            if (year == item.year && month + 1 == item.month && day == today) {
                holder.cv.setVisibility(View.VISIBLE);
                holder.cv.setTodayPaint();

            }
            if (item.cardTime == 2) {//打卡完成
                holder.cv.setVisibility(View.VISIBLE);
                holder.cv.setCardPaint();
            }
        }
        holder.flContent.setBackgroundColor(mContext.getResources().getColor(colors[item.featureId]));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.day == 0) {//点击了空白格
                    return;
                }
                if (mOnItemClickLisenter != null) {
                    mOnItemClickLisenter.onItemClick(holder.tv, position, mDatas.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class TimeViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private CircleView cv;
        private FrameLayout flContent;

        public TimeViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            cv = (CircleView) itemView.findViewById(R.id.cv);
            flContent = (FrameLayout) itemView.findViewById(R.id.fl_content);
        }
    }

    public void clear() {
        if (this.mDatas != null) {
            this.mDatas.clear();
            notifyDataSetChanged();
        }
    }

    public void addAll(List<DateBean> datas) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<DateBean>();
        }
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public interface OnItemClickLisenter<T> {
        void onItemClick(View itemView, int position, T item);
    }

    private OnItemClickLisenter mOnItemClickLisenter;

    public void setOnItemClickLisenter(OnItemClickLisenter itemClickLisenter) {
        this.mOnItemClickLisenter = itemClickLisenter;
    }

    public List<DateBean> getData() {
        return mDatas;
    }


}
