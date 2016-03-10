package com.putao.wd.model;

import java.io.Serializable;

/**
 * 剧情+教育--数据的图片地址--探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProductPlot implements Serializable {
    private String content;//一句话介绍
    private String img_url;//背景图
    private String img_list;//
    private String plot_id;//情节id
    private String video_id;//视频id

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

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

    public String getPlot_id() {
        return plot_id;
    }

    public void setPlot_id(String plot_id) {
        this.plot_id = plot_id;
    }

    @Override
    public String toString() {
        return "ExploreProductPlot{" +
                "content='" + content + '\'' +
                ", img_url='" + img_url + '\'' +
                ", img_list='" + img_list + '\'' +
                ", plot_id='" + plot_id + '\'' +
                '}';
    }
}
