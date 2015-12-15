package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 商城首页
 * Created by guchenkai on 2015/12/8.
 */
public class StoreHome implements Serializable {
    private List<StoreBanner> banner;//Banner列表
    private StoreProductHome product_home;//商品列表

    public List<StoreBanner> getBanner() {
        return banner;
    }

    public void setBanner(List<StoreBanner> banner) {
        this.banner = banner;
    }

    public StoreProductHome getProduct_home() {
        return product_home;
    }

    public void setProduct_home(StoreProductHome product_home) {
        this.product_home = product_home;
    }

    @Override
    public String toString() {
        return "StoreHome{" +
                "banner=" + banner +
                ", product_home=" + product_home +
                '}';
    }
}
