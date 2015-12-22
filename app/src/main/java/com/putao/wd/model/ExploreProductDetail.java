package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 全局数据--数据的图片地址--探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProductDetail implements Serializable {
    private String html;
    private String icon;
    private List<ExploreProductDataDaily> data;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ExploreProductDataDaily> getData() {
        return data;
    }

    public void setData(List<ExploreProductDataDaily> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ExploreProductDetail{" +
                "html='" + html + '\'' +
                ", icon='" + icon + '\'' +
                ", data=" + data +
                '}';
    }
}
