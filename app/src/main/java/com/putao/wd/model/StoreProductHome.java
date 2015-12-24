package com.putao.wd.model;

import java.util.List;

/**
 * 葡商城首页商品列表
 * Created by wango on 2015/12/15.
 */
public class StoreProductHome extends Page {
    private List<StoreProduct> data;

    public List<StoreProduct> getData() {
        return data;
    }

    public void setData(List<StoreProduct> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StoreProductHome{" +
                "data=" + data +
                '}';
    }
}
