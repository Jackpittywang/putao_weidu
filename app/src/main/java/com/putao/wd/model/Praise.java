package com.putao.wd.model;

import java.util.List;

/**
 * 通知--赞
 * Created by Administrator on 2015/12/25.
 */
public class Praise extends Page {
//    private List<PraiseDetail> like;
//    private int total_page;
//    private int current_page;
//
//    public List<PraiseDetail> getLike() {
//        return like;
//    }
//
//    public void setLike(List<PraiseDetail> like) {
//        this.like = like;
//    }
//
//    @Override
//    public int getTotal_page() {
//        return total_page;
//    }
//
//    @Override
//    public void setTotal_page(int total_page) {
//        this.total_page = total_page;
//    }
//
//    @Override
//    public int getCurrent_page() {
//        return current_page;
//    }
//
//    @Override
//    public void setCurrent_page(int current_page) {
//        this.current_page = current_page;
//    }
//
//    @Override
//    public String toString() {
//        return "Praise{" +
//                "like=" + like +
//                ", total_page=" + total_page +
//                ", current_page=" + current_page +
//                '}';
//    }

    private int like_id;
    private int uid;
    private String nick_name;
    private String head_img;
    private ParentContent parent_content;
    private String release_time;

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public int getLike_id() {
        return like_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public ParentContent getParent_content() {
        return parent_content;
    }

    public void setParent_content(ParentContent parent_content) {
        this.parent_content = parent_content;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Praise{" +
                "head_img='" + head_img + '\'' +
                ", like_id=" + like_id +
                ", uid=" + uid +
                ", nick_name='" + nick_name + '\'' +
                ", parent_content=" + parent_content +
                ", release_time='" + release_time + '\'' +
                '}';
    }
}
