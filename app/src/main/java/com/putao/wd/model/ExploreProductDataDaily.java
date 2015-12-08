package com.putao.wd.model;

import java.io.Serializable;

/**
 * 日常数据--数据的图片地址--探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProductDataDaily implements Serializable {
    private String content_head;//顶部文字
    private String content_center;//中部文字
    private String content_footer;//底部文字
    private String icon;//节日成就显示图标

    public String getContent_head() {
        return content_head;
    }

    public void setContent_head(String content_head) {
        this.content_head = content_head;
    }

    public String getContent_center() {
        return content_center;
    }

    public void setContent_center(String content_center) {
        this.content_center = content_center;
    }

    public String getContent_footer() {
        return content_footer;
    }

    public void setContent_footer(String content_footer) {
        this.content_footer = content_footer;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ExploreProductDataDaily{" +
                "content_head='" + content_head + '\'' +
                ", content_center='" + content_center + '\'' +
                ", content_footer='" + content_footer + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
