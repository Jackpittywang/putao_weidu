package com.putao.wd.model;

import java.io.Serializable;

/**
 * 剧情理念详情（查询）
 * Created by guchenkai on 2015/12/8.
 */
public class PlotDetail implements Serializable {
    private int plot_id;//剧情理念详情的id
    private int product_id;//产品id
    private String background_img;//背景图片地址
    private String score;//当前分数
    private String content;//剧情理念简介

    public int getPlot_id() {
        return plot_id;
    }

    public void setPlot_id(int plot_id) {
        this.plot_id = plot_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getBackground_img() {
        return background_img;
    }

    public void setBackground_img(String background_img) {
        this.background_img = background_img;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PlotDetail{" +
                "plot_id=" + plot_id +
                ", product_id=" + product_id +
                ", background_img='" + background_img + '\'' +
                ", score='" + score + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
