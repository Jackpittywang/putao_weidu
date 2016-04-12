package com.putao.wd.model;

import java.io.Serializable;

/**
 * 评论详情中的回复列表
 * Created by Administrator on 2016/4/11.
 */
public class ReplyLists implements Serializable{
    private String comment_id;
    private String content;
    private String uid;
    private String nick_name;
    private String head_img;
    private String release_time;
    private int is_essence;
    private String parent_comment;

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

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public int getIs_essence() {
        return is_essence;
    }

    public void setIs_essence(int is_essence) {
        this.is_essence = is_essence;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getParent_comment() {
        return parent_comment;
    }

    public void setParent_comment(String parent_comment) {
        this.parent_comment = parent_comment;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "ReplyLists{" +
                "comment_id='" + comment_id + '\'' +
                ", content='" + content + '\'' +
                ", uid='" + uid + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", head_img='" + head_img + '\'' +
                ", release_time='" + release_time + '\'' +
                ", is_essence=" + is_essence +
                ", parent_comment='" + parent_comment + '\'' +
                '}';
    }
}
