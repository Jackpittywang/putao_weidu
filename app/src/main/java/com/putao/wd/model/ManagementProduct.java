package com.putao.wd.model;

import java.io.Serializable;

/**
 * 产品--管理产品
 * Created by guchenkai on 2015/12/8.
 */
public class ManagementProduct implements Serializable {
    private String product_id;//应用ID号
    private String product_name;//应用名称
    private String product_icon;//应用图标地址
    private int status;//产品是否已受控，1受控0未受控

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_icon() {
        return product_icon;
    }

    public void setProduct_icon(String product_icon) {
        this.product_icon = product_icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManagementProduct{" +
                "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_icon='" + product_icon + '\'' +
                ", status=" + status +
                '}';
    }
}
