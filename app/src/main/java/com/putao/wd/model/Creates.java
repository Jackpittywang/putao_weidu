package com.putao.wd.model;

import java.util.List;

/**
 * 成长日记首页
 * Created by zhanghao on 2015/12/8.
 */
public class Creates extends Page {
   List<Create> data;

    public List<Create> getData() {
        return data;
    }

    public void setData(List<Create> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Creates{" +
                "data=" + data +
                '}';
    }
}
