package com.putao.wd.model;

import java.io.Serializable;

/**
 * 订单里的商品数据--订单
 * Created by guchenkai on 2015/12/9.
 */
public class OrderProduct implements Serializable {
    private int id;//id
    private int order_id;//订单id
    private int product_id;//商品id
    private int product_number;//商品号
    private String icon;//商品图标
    private String product_name;//商品名称
    private int quantity;//商品数量
    private String price;//商品金额
    private String sku;//商品SKU
    private int created_time;//创建时间
    private int updated_time;//更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_number() {
        return product_number;
    }

    public void setProduct_number(int product_number) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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

    public int getCreated_time() {
        return created_time;
    }

    public void setCreated_time(int created_time) {
        this.created_time = created_time;
    }

    public int getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(int updated_time) {
        this.updated_time = updated_time;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", product_id=" + product_id +
                ", product_number=" + product_number +
                ", icon='" + icon + '\'' +
                ", product_name='" + product_name + '\'' +
                ", quantity=" + quantity +
                ", price='" + price + '\'' +
                ", sku='" + sku + '\'' +
                ", created_time=" + created_time +
                ", updated_time=" + updated_time +
                '}';
    }
}
