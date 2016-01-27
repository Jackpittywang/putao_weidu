package com.putao.wd.model;

import java.util.ArrayList;

/**
 * 成长日记首页
 * Created by zhanghao on 2015/12/8.
 */
public class Diarys extends Page {
   ArrayList<Diary> data;

    public ArrayList<Diary> getData() {
        return data;
    }

    public void setData(ArrayList<Diary> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Diarys{" +
                "data=" + data +
                '}';
    }
}
