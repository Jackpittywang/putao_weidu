package com.putao.wd.model;

import java.io.Serializable;

/**
 * 售后申请的商品数据--售后数据--售后
 * Created by guchenkai on 2015/12/9.
 */
public class ServiceProduct implements Serializable {

    private String id;//
    private String sale_id;//售后id
    private String sale_order_id;//订单id
    private String sale_product_id;//产品id
    private String sale_order_product_id;//关联订单里的产品id
    private String sale_quantity;//售后数量
    private String sale_product_number;//售后商品号
    private String sale_reason;//售后理由
    private String sale_status;//售后状态
    private String sale_image;//申请图片
    private String sale_description;//申请描述
    private String sale_price;//售后金额
    private String sale_product_name;//售后产品名称
    private String sale_icon;//售后产品icon
    private String sale_sku;//售后产品sku
    private String real_sale_icon;
    private String real_sale_image;
    private String order_id;
    private String product_id;
    private String product_number;
    private String icon;
    private String sku;
    private String price;
    private String product_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }

    public String getSale_order_id() {
        return sale_order_id;
    }

    public void setSale_order_id(String sale_order_id) {
        this.sale_order_id = sale_order_id;
    }

    public String getSale_product_id() {
        return sale_product_id;
    }

    public void setSale_product_id(String sale_product_id) {
        this.sale_product_id = sale_product_id;
    }

    public String getSale_order_product_id() {
        return sale_order_product_id;
    }

    public void setSale_order_product_id(String sale_order_product_id) {
        this.sale_order_product_id = sale_order_product_id;
    }

    public String getSale_quantity() {
        return sale_quantity;
    }

    public void setSale_quantity(String sale_quantity) {
        this.sale_quantity = sale_quantity;
    }

    public String getSale_product_number() {
        return sale_product_number;
    }

    public void setSale_product_number(String sale_product_number) {
        this.sale_product_number = sale_product_number;
    }

    public String getSale_reason() {
        return sale_reason;
    }

    public void setSale_reason(String sale_reason) {
        this.sale_reason = sale_reason;
    }

    public String getSale_status() {
        return sale_status;
    }

    public void setSale_status(String sale_status) {
        this.sale_status = sale_status;
    }

    public String getSale_image() {
        return sale_image;
    }

    public void setSale_image(String sale_image) {
        this.sale_image = sale_image;
    }

    public String getSale_description() {
        return sale_description;
    }

    public void setSale_description(String sale_description) {
        this.sale_description = sale_description;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getSale_product_name() {
        return sale_product_name;
    }

    public void setSale_product_name(String sale_product_name) {
        this.sale_product_name = sale_product_name;
    }

    public String getSale_icon() {
        return sale_icon;
    }

    public void setSale_icon(String sale_icon) {
        this.sale_icon = sale_icon;
    }

    public String getSale_sku() {
        return sale_sku;
    }

    public void setSale_sku(String sale_sku) {
        this.sale_sku = sale_sku;
    }

    public String getReal_sale_icon() {
        return real_sale_icon;
    }

    public void setReal_sale_icon(String real_sale_icon) {
        this.real_sale_icon = real_sale_icon;
    }

    public String getReal_sale_image() {
        return real_sale_image;
    }

    public void setReal_sale_image(String real_sale_image) {
        this.real_sale_image = real_sale_image;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Override
    public String toString() {
        return "ServiceProduct{" +
                "id='" + id + '\'' +
                ", sale_id='" + sale_id + '\'' +
                ", sale_order_id='" + sale_order_id + '\'' +
                ", sale_product_id='" + sale_product_id + '\'' +
                ", sale_order_product_id='" + sale_order_product_id + '\'' +
                ", sale_quantity='" + sale_quantity + '\'' +
                ", sale_product_number='" + sale_product_number + '\'' +
                ", sale_reason='" + sale_reason + '\'' +
                ", sale_status='" + sale_status + '\'' +
                ", sale_image='" + sale_image + '\'' +
                ", sale_description='" + sale_description + '\'' +
                ", sale_price='" + sale_price + '\'' +
                ", sale_product_name='" + sale_product_name + '\'' +
                ", sale_icon='" + sale_icon + '\'' +
                ", sale_sku='" + sale_sku + '\'' +
                ", real_sale_icon='" + real_sale_icon + '\'' +
                ", real_sale_image='" + real_sale_image + '\'' +
                ", order_id='" + order_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_number='" + product_number + '\'' +
                ", icon='" + icon + '\'' +
                ", sku='" + sku + '\'' +
                ", price='" + price + '\'' +
                ", product_name='" + product_name + '\'' +
                '}';
    }
}
