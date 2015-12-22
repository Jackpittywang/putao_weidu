package com.putao.wd.model;

import java.io.Serializable;

/**
 * 剧情+教育--数据的图片地址--探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProductDataPlot implements Serializable {
    private String content;//一句话介绍
    private String img_url;//背景图
    private String img_list;//

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImg_list() {
        return img_list;
    }

    public void setImg_list(String img_list) {
        this.img_list = img_list;
    }

    @Override
    public String toString() {
        return "ExploreProductDataPlot{" +
                "content='" + content + '\'' +
                ", img_url='" + img_url + '\'' +
                ", img_list='" + img_list + '\'' +
                '}';
    }
}
