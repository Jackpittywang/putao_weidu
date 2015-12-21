package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProduct implements Serializable {
    private String summary;
    private String time;
    private String product_name;
    private String product_icon;
    private int type;
    private String img_url;
    private List<ExploreProductData> data;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public List<ExploreProductData> getData() {
        return data;
    }

    public void setData(List<ExploreProductData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ExploreProduct{" +
                "summary='" + summary + '\'' +
                ", time='" + time + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_icon='" + product_icon + '\'' +
                ", type=" + type +
                ", img_url='" + img_url + '\'' +
                ", data=" + data +
                '}';
    }
}
