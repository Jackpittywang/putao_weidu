package com.putao.wd.model;

import java.io.Serializable;

/**
 * 购物车
 * Created by guchenkai on 2015/12/9.
 */
public class Cart implements Serializable {
    private String pid;//商品id
    private String qt;//数量
    private String price;//价格
    private String icon;//商品图片
    private String sku;//规格
    private String title;//标题
    private String subtitle;//子标题

    //非接口返回字段
    private boolean isNull;
    private boolean isSelect;
    private boolean editable;
    private String color;
    private String goodCount;//保存编辑时的商品数量，供保存时使用

    public boolean isNull() {
        return isNull;
    }

    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public String getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "pid='" + pid + '\'' +
                ", qt='" + qt + '\'' +
                ", price='" + price + '\'' +
                ", icon='" + icon + '\'' +
                ", sku='" + sku + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }
}
