package com.putao.wd.model;

import java.io.Serializable;

/**
 * 活动评论
 * Created by guchenkai on 2015/12/8.
 */
public class Comment implements Serializable {
    private String comment_id;//评论ID
    private String content;//内容
    private String count_cool;//赞
    private String create_time;//创建时间
    private String user_id;//评论归属用户
    private String user_name;//归属用户昵称
    private String user_profile_photo;//头像
    //private CommentReply  reply;//评论回复内容

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

    public String getCount_cool() {
        return count_cool;
    }

    public void setCount_cool(String count_cool) {
        this.count_cool = count_cool;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public String getUser_profile_photo() {
        return user_profile_photo;
    }

    public void setUser_profile_photo(String user_profile_photo) {
        this.user_profile_photo = user_profile_photo;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment_id='" + comment_id + '\'' +
                ", content='" + content + '\'' +
                ", count_cool='" + count_cool + '\'' +
                ", create_time='" + create_time + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_profile_photo='" + user_profile_photo + '\'' +
                '}';
    }
}
