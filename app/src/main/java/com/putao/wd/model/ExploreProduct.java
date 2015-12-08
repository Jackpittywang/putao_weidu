package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProduct implements Serializable {
    private int product_id;//游戏id
    private String product_name;//当前游戏名称
    private String product_icon;//游戏图标
    private String product_summary;//当前游戏总结文案
    private List<ExploreProductData> data_list;//数据的图片地址

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
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

    public String getProduct_summary() {
        return product_summary;
    }

    public void setProduct_summary(String product_summary) {
        this.product_summary = product_summary;
    }

    public List<ExploreProductData> getData_list() {
        return data_list;
    }

    public void setData_list(List<ExploreProductData> data_list) {
        this.data_list = data_list;
    }

    @Override
    public String toString() {
        return "ExploreProduct{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", product_icon='" + product_icon + '\'' +
                ", product_summary='" + product_summary + '\'' +
                ", data_list=" + data_list +
                '}';
    }
}
