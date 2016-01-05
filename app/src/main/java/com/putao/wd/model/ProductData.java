package com.putao.wd.model;

import java.io.Serializable;

/**
 * 活动列表
 * Created by guchenkai on 2015/12/18.
 */
public class ProductData implements Serializable {
    private String product_id;
    private int reason;
    private String image;
    private String description;
    private String quantity;
    private String sha1;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "product_id='" + product_id + '\'' +
                ", reason=" + reason +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
