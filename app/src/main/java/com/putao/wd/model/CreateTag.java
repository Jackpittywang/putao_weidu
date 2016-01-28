package com.putao.wd.model;

import java.io.Serializable;

/**
 * 成长日记首页
 * Created by zhanghao on 2015/12/8.
 */
public class CreateTag implements Serializable {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CreateComment{" +
                "count=" + count +
                '}';
    }
}
