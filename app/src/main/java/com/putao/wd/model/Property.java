package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/12.
 */
public class Property implements Serializable {
    private int like_count;//点赞数
    private int comments_count;//评论数
    private boolean is_comment;//是否可评论
    private boolean is_like;//当前用户是否点过赞

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

    public boolean is_comment() {
        return is_comment;
    }

    public void setIs_comment(boolean is_comment) {
        this.is_comment = is_comment;
    }

    public boolean is_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }


    @Override
    public String toString() {
        return "Property{" +
                "like_count=" + like_count +
                ", comments_count=" + comments_count +
                ", is_comment=" + is_comment +
                ", is_like=" + is_like +
                '}';
    }
}
