package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 商城产品列表
 * Created by guchenkai on 2015/11/30.
 */
public class ProductItem implements Serializable {
    private String id;
    private String iconUrl;
    private String title;
    private String intro;
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "id='" + id + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
