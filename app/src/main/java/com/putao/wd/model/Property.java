package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/12.
 */
public class Property implements Serializable {
    private boolean is_pic;//是否评论带图片
    private int like_count;//点赞数
    private int comments_count;//评论数
    private boolean is_comment;//是否可评论
    private boolean is_like;//当前用户是否点过赞
    private boolean is_collect;//当前用户是否收藏

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


    public boolean is_pic() {
        return is_pic;
    }

    public void setIs_pic(boolean is_pic) {
        this.is_pic = is_pic;
    }

    public boolean is_collect() {
        return is_collect;
    }

    public void setIs_collect(boolean is_collect) {
        this.is_collect = is_collect;
    }

    @Override
    public String toString() {
        return "Property{" +
                "is_pic=" + is_pic +
                ", like_count=" + like_count +
                ", comments_count=" + comments_count +
                ", is_comment=" + is_comment +
                ", is_like=" + is_like +
                '}';
    }
}
