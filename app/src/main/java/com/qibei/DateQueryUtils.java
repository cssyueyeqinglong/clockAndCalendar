package com.qibei;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class DateQueryUtils {
    static Uri uri = Uri.parse("content://com.qibei.timeeventprovider/timeTable");
    private static String[] weeks = new String[]{"一", "二", "三", "四", "五", "六", "七"};

    //获取月份天数
    public static List<DateBean> getDateList(Context context, int year, int month) {
        ContentResolver resolver = context.getContentResolver();
        List<DateBean> mDatas = new ArrayList<DateBean>();
        //先查询是否存储过当前月份日历
        Cursor cursor = resolver.query(uri, null, "timeId=?", new String[]{year + "" + (month + 1)}, null);
        while (cursor.moveToNext()) {//有数据
            DateBean user = new DateBean();
            user.id = cursor.getInt(1);
            user.year = cursor.getInt(2);
            user.month = cursor.getInt(3);
            user.day = cursor.getInt(4);
            user.week = cursor.getInt(5);
            user.featureId = cursor.getInt(6);
            user.timeDesc = cursor.getString(7);
            user.place = cursor.getString(8);
            user.desc = cursor.getString(9);
            user.cardTime = cursor.getInt(10);
            mDatas.add(user);
            Log.d("bean", "bean:" + user);
        }
        cursor.close();
        if (mDatas.size() <= 0) {
            mDatas.addAll(initMonthDay(year, month));
        }
        DateBean dateBean = mDatas.get(0);
        for (int i = 0; i < 7; i++) {
            DateBean bean = new DateBean();
            bean.weekTime = weeks[6 - i];
            mDatas.add(0, bean);
        }
        for (int i = 1; i < dateBean.week; i++) {
            mDatas.add(7, new DateBean());
        }
        return mDatas;
    }

    public static List<DateBean> initMonthDay(int year, int month) {
        List<DateBean> mDatas = new ArrayList<>();
        int days = getDaysByYearMonth(year, month);
        Calendar cd = Calendar.getInstance();
        for (int i = 0; i < days; i++) {
            cd.set(Calendar.YEAR, year);
            cd.set(Calendar.MONTH, month);
            cd.set(Calendar.DAY_OF_MONTH, i);
            int week = cd.get(Calendar.DAY_OF_WEEK);
            Log.d("calendar", "canlendar:day=" + i + ",week=" + week);
            DateBean item = new DateBean();
            item.year = year;
            item.month = month + 1;
            item.day = i + 1;
            item.week = week;
            mDatas.add(item);
        }
        return mDatas;

    }

    //获取某个月有多少天
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 查询某个月是否存在数据库中,然后更新数据库，注意，因为涉及到查询修改数据库，可能会耗时，需要开启子线程运行该方法
     *
     * @param context
     * @param uri
     * @param year
     * @param month
     * @param time
     */
    public static void upDateTime(Context context, Uri uri, int year, int month, int day, int position, String time, String content01, String content02) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"timeId"}, "timeId=?", new String[]{year + "" + (month + 1)}, null);
        if (cursor.moveToNext()) {
            ContentValues values = new ContentValues();
            values.put("featureId", position + 1);
            values.put("timeDesc", time);
            values.put("place", content01);
            values.put("desc", content02);
            resolver.update(uri, values, "timeId = ? and day = ?", new String[]{year + "" + (month + 1), day + ""});
        } else {//添加到数据库中整个月的数据
            addMonthToDb(resolver, year, month, day, position, time, content01, content02, 0);
        }
        cursor.close();
    }

    /**
     * 插入打卡记录
     *
     * @param context
     * @param year
     * @param month
     * @param day
     * @param cardTime
     */
    public static void upDateTime(Context context, int year, int month, int day, int cardTime) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"timeId"}, "timeId=?", new String[]{year + "" + (month + 1)}, null);
        if (cursor.moveToNext()) {
            ContentValues values = new ContentValues();
            values.put("cardTime", cardTime);
            resolver.update(uri, values, "timeId = ? and day = ?", new String[]{year + "" + (month + 1), day + ""});
        } else {//添加到数据库中整个月的数据
            addMonthToDb(resolver, year, month, day, -1, null, null, null, 2);
        }
        cursor.close();
    }

    public static void addMonthToDb(ContentResolver resolver, int year, int month, int day, int position, String time, String content01, String content02, int cardTime) {
        List<DateBean> datas = DateQueryUtils.initMonthDay(year, month);
        for (int i = 0; i < datas.size(); i++) {
            DateBean item = datas.get(i);
            ContentValues values = new ContentValues();
            values.put("timeId", Integer.valueOf(year + "" + (month + 1)));
            values.put("year", year);
            values.put("month", month + 1);
            values.put("day", i + 1);
            values.put("week", item.week);
            values.put("featureId", 0);
            values.put("timeDesc", "");
            values.put("place", "");
            values.put("desc", "");
            values.put("cardTime", 0);
            if (item.day == day) {
                values.put("featureId", position + 1);
                values.put("timeDesc", time);
                values.put("place", content01);
                values.put("desc", content02);
                values.put("cardTime", cardTime);
            }
            resolver.insert(uri, values);
        }
    }
}
