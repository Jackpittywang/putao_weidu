package com.putao.wd.model;

import java.io.Serializable;

/**
 * 首页产品--首页产品列表
 * Created by guchenkai on 2015/12/8.
 */
public class StoreProduct implements Serializable {
    private String id;
    private String title;
    private String subtitle;
    private String Price;
    private String Icon;

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    @Override
    public String toString() {
        return "StoreProduct{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", Price='" + Price + '\'' +
                ", Icon='" + Icon + '\'' +
                '}';
    }
}
