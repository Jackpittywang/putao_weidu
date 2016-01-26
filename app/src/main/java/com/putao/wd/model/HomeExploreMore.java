package com.putao.wd.model;

import java.io.Serializable;

/**
 * 更多内容
 * Created by yanghx on 2016/1/12.
 */
public class HomeExploreMore implements Serializable {

    private String article_id;
    private String title;
    private String description;
    private String send_time;
    private boolean is_like;
    private String count_likes;
    private String count_comments;
    private String type;
    private String url;
    private String cover_pic;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public boolean getIs_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public String getCount_likes() {
        return count_likes;
    }

    public void setCount_likes(String count_likes) {
        this.count_likes = count_likes;
    }

    public String getCount_comments() {
        return count_comments;
    }

    public void setCount_comments(String count_comments) {
        this.count_comments = count_comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    @Override
    public String toString() {
        return "HomeExploreMore{" +
                "article_id='" + article_id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", send_time='" + send_time + '\'' +
                ", is_like='" + is_like + '\'' +
                ", count_likes=" + count_likes +
                ", count_comments=" + count_comments +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", cover_pic='" + cover_pic + '\'' +
                '}';
    }
}
