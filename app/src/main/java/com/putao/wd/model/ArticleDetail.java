package com.putao.wd.model;
import java.io.Serializable;
import java.util.List;
public class ArticleDetail implements Serializable{

    private int article_id  ;//文章id
    private String article_icon  ;//文章配图
    private String article_title  ;//文章标题
    private String article_contents  ;//文章内容 H5
    private int type  ;//文章类型
    private int like_count  ;//点赞数
    private int comments_count;//评论数
    private int is_like  ;//是否点赞0未点赞，1已点赞
    private int sub_status  ;//是否收藏0未收藏，1已收藏

    private List<ArticleDetailComment> comment_list;

    public String getArticle_contents() {
        return article_contents;
    }

    public void setArticle_contents(String article_contents) {
        this.article_contents = article_contents;
    }

    public String getArticle_icon() {
        return article_icon;
    }

    public void setArticle_icon(String article_icon) {
        this.article_icon = article_icon;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public List<ArticleDetailComment> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<ArticleDetailComment> comment_list) {
        this.comment_list = comment_list;
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

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getSub_status() {
        return sub_status;
    }

    public void setSub_status(int sub_status) {
        this.sub_status = sub_status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ArticleDetail{" +
                "article_contents='" + article_contents + '\'' +
                ", article_id=" + article_id +
                ", article_icon='" + article_icon + '\'' +
                ", article_title='" + article_title + '\'' +
                ", type=" + type +
                ", like_count=" + like_count +
                ", comments_count=" + comments_count +
                ", is_like=" + is_like +
                ", sub_status=" + sub_status +
                ", comment_list=" + comment_list +
                '}';
    }
}