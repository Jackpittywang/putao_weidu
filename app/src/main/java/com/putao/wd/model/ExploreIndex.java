package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 首页7条
 * Created by zhanghao on 2015/1/21
 */
public class ExploreIndex extends CacheTime implements Serializable {
    private String article_id;//文章id
    private String title;//文章标题
    private String description;//文章描述
    private String explanation;//网页
    private int send_time;//更新时间
    private int count_likes;//点赞数量
    private int count_comments;//评论数量
    private boolean is_like;//评论数量
    private List<ExploreBanner> banner;//详情头
    private String share_url;//分享链接

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

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getSend_time() {
        return send_time;
    }

    public void setSend_time(int send_time) {
        this.send_time = send_time;
    }

    public int getCount_likes() {
        return count_likes;
    }

    public void setCount_likes(int count_likes) {
        this.count_likes = count_likes;
    }

    public int getCount_comments() {
        return count_comments;
    }

    public void setCount_comments(int count_comments) {
        this.count_comments = count_comments;
    }

    public List<ExploreBanner> getBanner() {
        return banner;
    }

    public void setBanner(List<ExploreBanner> banner) {
        this.banner = banner;
    }

    public boolean is_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    @Override
    public String toString() {
        return "ExploreIndex{" +
                "article_id='" + article_id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", explanation='" + explanation + '\'' +
                ", send_time=" + send_time +
                ", count_likes=" + count_likes +
                ", count_comments=" + count_comments +
                ", is_like=" + is_like +
                ", banner=" + banner +
                ", share_url='" + share_url + '\'' +
                '}';
    }
}

