package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 数据V1.1版本陪伴首页
 * Created by zhanghao on 2016/1/14.
 */
public class DiaryApp implements Serializable {
    private String product_id;//应用id
    private String mall_product_id;//商品id
    private String product_name;//当前游戏名称
    private String product_icon;//游戏图标

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

    public String getMall_product_id() {
        return mall_product_id;
    }

    public void setMall_product_id(String mall_product_id) {
        this.mall_product_id = mall_product_id;
    }

    @Override
    public String toString() {
        return "DiaryApp{" +
                "product_id='" + product_id + '\'' +
                ", mall_product_id='" + mall_product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_icon='" + product_icon + '\'' +
                '}';
    }
}
