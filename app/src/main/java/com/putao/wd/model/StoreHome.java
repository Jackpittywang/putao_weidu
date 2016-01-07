package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 商城首页
 * Created by guchenkai on 2015/12/8.
 */
@Deprecated
public class StoreHome implements Serializable {
    private List<StoreBanner> banner;//Banner列表
    private StoreProductHome product;//商品列表

    public List<StoreBanner> getBanner() {
        return banner;
    }

    public void setBanner(List<StoreBanner> banner) {
        this.banner = banner;
    }

    public StoreProductHome getProduct() {
        return product;
    }

    public void setProduct(StoreProductHome product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "StoreHome{" +
                "banner=" + banner +
                ", product=" + product +
                '}';
    }
}
