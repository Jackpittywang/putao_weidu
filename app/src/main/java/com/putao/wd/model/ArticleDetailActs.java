package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 活动文章下方列表
 * Created by zhanghao on 2015/12/8.
 */
public class ArticleDetailActs implements Serializable {
    private int article_id;//文章ID
    private int article_icon;//文章配图
    private int article_title;//文章标题
    private int article_contents;//文章内容H5
    private int type;//文章类型
    private int like_count;//点赞数
    private int comments_count;//评论数
    private int is_like;//是否点赞0未点赞，1已点赞
    private int sub_status;//是否收藏0未收藏，1已收藏
    private List<CommentLists> comment_list;//评论列表
    private int height;//view高度

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getArticle_icon() {
        return article_icon;
    }

    public void setArticle_icon(int article_icon) {
        this.article_icon = article_icon;
    }

    public int getArticle_title() {
        return article_title;
    }

    public void setArticle_title(int article_title) {
        this.article_title = article_title;
    }

    public int getArticle_contents() {
        return article_contents;
    }

    public void setArticle_contents(int article_contents) {
        this.article_contents = article_contents;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getIs_like() {
        return is_like;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    public int getSub_status() {
        return sub_status;
    }

    public void setSub_status(int sub_status) {
        this.sub_status = sub_status;
    }

    public List<CommentLists> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<CommentLists> comment_list) {
        this.comment_list = comment_list;
    }

    @Override
    public String toString() {
        return "ArticleDetailActs{" +
                "article_id=" + article_id +
                ", article_icon=" + article_icon +
                ", article_title=" + article_title +
                ", article_contents=" + article_contents +
                ", type=" + type +
                ", like_count=" + like_count +
                ", comments_count=" + comments_count +
                ", is_like=" + is_like +
                ", sub_status=" + sub_status +
                ", comment_list=" + comment_list +
                '}';
    }
}
