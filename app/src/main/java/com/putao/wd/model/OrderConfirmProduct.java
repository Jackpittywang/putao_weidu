package com.putao.wd.model;

import java.io.Serializable;

/**
 * 填写订单--商品
 * Created by yanghx on 2015/12/28.
 */
public class OrderConfirmProduct implements Serializable {

    private String pid;
    private String qt;
    private int use;
    private String price;
    private String title;
    private String subtitle;
    private String product_number;
    private String sku;
    private String icon;
    private String totalPrice;
    private int totalQt;

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

    public int getUse() {
        return use;
    }

    public void setUse(int use) {
        this.use = use;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQt() {
        return totalQt;
    }

    public void setTotalQt(int totalQt) {
        this.totalQt = totalQt;
    }

    @Override
    public String toString() {
        return "OrderConfirmProduct{" +
                "pid='" + pid + '\'' +
                ", qt='" + qt + '\'' +
                ", use=" + use +
                ", price='" + price + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", product_number='" + product_number + '\'' +
                ", sku='" + sku + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
