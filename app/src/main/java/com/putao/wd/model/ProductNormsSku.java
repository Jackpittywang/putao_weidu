package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 规格对应的商品信息--商品规格
 * Created by guchenkai on 2015/12/9.
 */
public class ProductNormsSku implements Serializable {
    private String params;//规格值名称ID
    private String pid;//商品id
    private String quantity;//剩余库存数量
    private String price;//价格
    private String picture;//图片集

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "ProductNormsSku{" +
                "params='" + params + '\'' +
                ", pid='" + pid + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
