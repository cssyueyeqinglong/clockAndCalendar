package com.qibei;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Administrator on 2017/6/20.
 */

public class TestBean extends BaseObservable {


    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public TestBean(String title, String name, int age) {
        this.title = title;
        this.name = name;
        this.age = age;
    }

    private String title;
    private String name;

    @Bindable
    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    private int age;

    public int type;

    public TestBean(String name, int type, int age) {
        this.name = name;
        this.type = type;
        this.age = age;
    }
}
