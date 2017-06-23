package com.qibei;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11.
 */

public class DateBean implements Serializable {
    public int id;
    public int timeId;
    public int year;
    public int month;
    public int day;
    public int week;

    public int featureId;
    public String timeDesc;
    public String place;
    public String desc;
    public String weekTime;//第一行的星期数据
    public int cardTime;

    @Override
    public String toString() {
        return "year=" + year + ",month=" + month + ",day=" + day +
                ",week=" + week + ",featureId=" + featureId
                + ",timeDesc=" + timeDesc + ",place=" + place + ",cartTime=" + cardTime;
    }
}
