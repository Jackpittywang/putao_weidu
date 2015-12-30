package com.putao.wd.model;

import java.io.Serializable;

/**
 * 售后--订单信息--商品
 *
 * Created by yanghx on 2015/12/30.
 */
public class ServiceOrderInfoProduct implements Serializable {

    private String id;
    private String order_id;
    private String product_id;
    private String product_number;
    private String icon;
    private String product_name;
    private String quantity;
    private String price;
    private String sku;
    private String created_time;
    private String updated_time;
    private String real_icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getReal_icon() {
        return real_icon;
    }

    public void setReal_icon(String real_icon) {
        this.real_icon = real_icon;
    }

    @Override
    public String toString() {
        return "ServiceOrderInfoProduct{" +
                "id='" + id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_number='" + product_number + '\'' +
                ", icon='" + icon + '\'' +
                ", product_name='" + product_name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", sku='" + sku + '\'' +
                ", created_time='" + created_time + '\'' +
                ", updated_time='" + updated_time + '\'' +
                ", real_icon='" + real_icon + '\'' +
                '}';
    }
}
