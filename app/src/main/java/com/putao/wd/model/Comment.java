package com.putao.wd.model;

import java.io.Serializable;

/**
 * 活动评论
 * Created by guchenkai on 2015/12/8.
 */
public class Comment implements Serializable {
    private int comment_id;//评论ID
    private String title;//评论标题
    private String content;//内容
    private int count_coll;//赞
    private int count_reply;//回复数
    private int user_id;//评论归属用户
    private String user_name;//用户昵称
    private String user_profile_photo;//头像

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount_coll() {
        return count_coll;
    }

    public void setCount_coll(int count_coll) {
        this.count_coll = count_coll;
    }

    public int getCount_reply() {
        return count_reply;
    }

    public void setCount_reply(int count_reply) {
        this.count_reply = count_reply;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_profile_photo() {
        return user_profile_photo;
    }

    public void setUser_profile_photo(String user_profile_photo) {
        this.user_profile_photo = user_profile_photo;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment_id=" + comment_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", count_coll=" + count_coll +
                ", count_reply=" + count_reply +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_profile_photo='" + user_profile_photo + '\'' +
                '}';
    }
}
