package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/12.
 */
public class ContentLists implements Serializable {
    private String article_id;//文章id
    private String title;//文章标题
    private String sub_title;//我是副标题
    private String cover_pic;//配图（可用于分享）
    private String link_url;//跳转地址
    private String release_time;//此篇文章的发布时间

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
