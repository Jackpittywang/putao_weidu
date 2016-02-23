package com.putao.wd.model;

import java.io.Serializable;

/**
 * 首页产品--首页产品列表
 * Created by guchenkai on 2015/12/8.
 */
public class StoreProduct implements Serializable {
    private String id;
    private String title;
    private String image;
    private String mobile_url;
    private String subtitle;
    private String price;
    private String icon;
    private String stock;
    private String lock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    @Override
    public String toString() {
        return "StoreProduct{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", mobile_url='" + mobile_url + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", price='" + price + '\'' +
                ", icon='" + icon + '\'' +
                ", stock='" + stock + '\'' +
                ", lock='" + lock + '\'' +
                '}';
    }
}
