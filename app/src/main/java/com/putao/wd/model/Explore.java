package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 探索号--成长日记首页
 * Created by yanghx on 2015/12/22.
 */
public class Explore extends Page {

    private List<ExploreProduct> data;

    public List<ExploreProduct> getData() {
        return data;
    }

    public void setData(List<ExploreProduct> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Explore{" +
                "data=" + data +
                '}';
    }
}

