package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 数据的图片地址--探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProduct implements Serializable {
    private String summary;
    private String time;
    private String product_name;
    private String product_icon;
    private int type;
    private String img_url;
    private String data;

    // 以下两个field用作data类型不确定时，分步解析保存
    private List<ExploreProductDetail> details;//保存array data
    private ExploreProductPlot plot;//保存object data

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ExploreProductDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ExploreProductDetail> details) {
        this.details = details;
    }

    public ExploreProductPlot getPlot() {
        return plot;
    }

    public void setPlot(ExploreProductPlot plot) {
        this.plot = plot;
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
                ", data='" + data + '\'' +
                ", details=" + details +
                ", plot=" + plot +
                '}';
    }
}
