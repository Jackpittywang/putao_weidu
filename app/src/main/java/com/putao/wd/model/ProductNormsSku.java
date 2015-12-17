package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 规格对应的商品信息--商品规格
 * Created by guchenkai on 2015/12/9.
 */
public class ProductNormsSku implements Serializable {
    private String pid;//商品id
    private String quantity;//剩余库存数量
    private String price;//价格
    private String icon;//商品icon
    private List<String> params;//规格值名称ID
    private List<String> picture;//图片集

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "ProductNormsSku{" +
                "pid='" + pid + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", icon='" + icon + '\'' +
                ", params=" + params +
                ", picture=" + picture +
                '}';
    }
}
