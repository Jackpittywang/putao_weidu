package com.putao.wd.model;

import java.io.Serializable;

/**
 * 活动评论
 * Created by guchenkai on 2015/12/8.
 */
public class Comment implements Serializable {
    private String comment_id;//评论ID
    private String content;//内容
    private String count_likes;//赞
    private boolean is_like;//赞
    private String modified_time;//创建时间
    private String user_id;//评论归属用户
    private String user_name;//归属用户昵称
    private String head_img;//头像
    private String reply;//Json被回复的用户信息
    // 待定   //评论回复内容


    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCount_likes() {
        return count_likes;
    }

    public void setCount_likes(String count_likes) {
        this.count_likes = count_likes;
    }

    public boolean is_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public String getModified_time() {
        return modified_time;
    }

    public void setModified_time(String modified_time) {
        this.modified_time = modified_time;
    }

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

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment_id='" + comment_id + '\'' +
                ", content='" + content + '\'' +
                ", count_likes='" + count_likes + '\'' +
                ", is_like=" + is_like +
                ", modified_time='" + modified_time + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", head_img='" + head_img + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }
}
