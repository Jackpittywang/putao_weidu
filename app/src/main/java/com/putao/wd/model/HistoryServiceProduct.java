package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/30.
 */
public class HistoryServiceProduct implements Serializable{
    private String id;
    private String sale_id;
    private String sale_order_id;
    private String sale_product_id;
    private String sale_order_product_id;
    private int sale_quantity;
    private String sale_product_number;
    private String sale_reason;
    private String sale_status;
    private String sale_image;
    private String sale_description;
    private String sale_price;
    private String sale_product_name;
    private String sale_icon;
    private String sale_sku;
    private String real_sale_icon;
    private String real_sale_image;

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

    public int getSale_quantity() {
        return sale_quantity;
    }

    public void setSale_quantity(int sale_quantity) {
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

    @Override
    public String toString() {
        return "HistoryServiceProduct{" +
                "id='" + id + '\'' +
                ", sale_id='" + sale_id + '\'' +
                ", sale_order_id='" + sale_order_id + '\'' +
                ", sale_product_id='" + sale_product_id + '\'' +
                ", sale_order_product_id='" + sale_order_product_id + '\'' +
                ", sale_quantity=" + sale_quantity +
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
                '}';
    }
}
