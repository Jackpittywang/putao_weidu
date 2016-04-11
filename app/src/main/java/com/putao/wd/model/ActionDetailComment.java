package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ActionDetailComment implements Serializable {

    private String attend_id;//参与id
    private int type;//类型 1最新2精选
    private String pics;//用户图片
    private String uid;//用户id
    private String nick_name;//用户名
    private String head_img;//用户图片
    private String time;//参与时间戳
    private String comment;//参与内容
    private int like_count;//点赞数
    private int comments_count;//评论数
    private int is_like;//1已点赞0未点赞
    private String share_url;//分享链接

    public String getAttend_id() {
        return attend_id;
    }

    public void setAttend_id(String attend_id) {
        this.attend_id = attend_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
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

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "ActionDetailComment{" +
                "attend_id='" + attend_id + '\'' +
                ", type=" + type +
                ", pics='" + pics + '\'' +
                ", uid='" + uid + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", head_img='" + head_img + '\'' +
                ", time='" + time + '\'' +
                ", comment='" + comment + '\'' +
                ", like_count=" + like_count +
                ", comments_count=" + comments_count +
                ", is_like=" + is_like +
                ", share_url='" + share_url + '\'' +
                '}';
    }
}
