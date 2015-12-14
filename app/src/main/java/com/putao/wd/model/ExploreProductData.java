package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 数据的图片地址--探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProductData implements Serializable {
    private String product_id;
    private String product_name;//班得瑞的奇幻花园
    private String product_icon;//http://api.putao.photo.io/a.jpg
    private String product_ummary;//班得瑞的奇幻花园好好玩
    private ExploreProductDataList data_list;//日常数据

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

    public String getProduct_ummary() {
        return product_ummary;
    }

    public void setProduct_ummary(String product_ummary) {
        this.product_ummary = product_ummary;
    }

    public ExploreProductDataList getData_list() {
        return data_list;
    }

    public void setData_list(ExploreProductDataList data_list) {
        this.data_list = data_list;
    }

    @Override
    public String toString() {
        return "ExploreProductData{" +
                "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_icon='" + product_icon + '\'' +
                ", product_ummary='" + product_ummary + '\'' +
                ", data_list=" + data_list +
                '}';
    }
}
