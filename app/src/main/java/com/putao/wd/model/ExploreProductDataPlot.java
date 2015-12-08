package com.putao.wd.model;

import java.io.Serializable;

/**
 * 剧情+教育--数据的图片地址--探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProductDataPlot implements Serializable {
    private String content;//一句话介绍
    private String background_img;//背景图

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBackground_img() {
        return background_img;
    }

    public void setBackground_img(String background_img) {
        this.background_img = background_img;
    }

    @Override
    public String toString() {
        return "ExlporeProductDataPlot{" +
                "content='" + content + '\'' +
                ", background_img='" + background_img + '\'' +
                '}';
    }
}
