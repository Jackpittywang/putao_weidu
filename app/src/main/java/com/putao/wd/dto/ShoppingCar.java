package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 购物车
 * Created by guchenkai on 2015/12/4.
 */
public class ShoppingCar implements Serializable {
    private String id;
    private String title;
    private String color;
    private String size;
    private String money;
    private String count;
    private String imgUrl;
    private boolean isNull;
    private boolean isSelect;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "ShoppingCar{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", money='" + money + '\'' +
                ", count='" + count + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", isNull=" + isNull +
                ", isSelect=" + isSelect +
                '}';
    }
}
