package com.putao.wd.model;

/**
 * V1.3.0评论列表
 * Created by zhanghao on 2016/04/07.
 */
public class CommentLists extends Page {
    private String user_id;//用户id
    private String user_name;//用户名
    private String user_img;//用户图片
    private String comment_time;//评论时间戳
    private String comment;//评论内容

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentLists{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_img='" + user_img + '\'' +
                ", comment_time='" + comment_time + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
