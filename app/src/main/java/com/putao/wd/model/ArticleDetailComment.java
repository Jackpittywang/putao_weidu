package com.putao.wd.model;

import java.io.Serializable;

/**
 * 陪伴评论V1.3.0
 * Created by zhanghao on 2015/04/10.
 */
public class ArticleDetailComment implements Serializable {
    private String comment_id;//评论ID
    private String uid;//评论归属用户
    private String nick_name;//归属用户昵称
    private String head_img;//头像
    private String comment_time;//评论时间戳
    private String comment;//评论内容
    private String content;//当评论为父评论时的评论内容
    private boolean is_like;//赞
    private int like_count;//点赞数
    private int comment_count;//评论数
    private ArticleDetailComment parent_comment;//父评论，可为空

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParent_comment(ArticleDetailComment parent_comment) {
        this.parent_comment = parent_comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean is_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public ArticleDetailComment getParent_comment() {
        return parent_comment;
    }

    @Override
    public String toString() {
        return "ArticleDetailComment{" +
                "comment_id='" + comment_id + '\'' +
                ", uid='" + uid + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", head_img='" + head_img + '\'' +
                ", comment_time='" + comment_time + '\'' +
                ", comment='" + comment + '\'' +
                ", content='" + content + '\'' +
                ", is_like=" + is_like +
                ", like_count=" + like_count +
                ", comment_count=" + comment_count +
                ", parent_comment=" + parent_comment +
                '}';
    }
}
