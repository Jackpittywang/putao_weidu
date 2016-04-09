package com.putao.wd.model;

import java.util.List;

/**
 * 通知--回复
 * Created by Administrator on 2015/12/25.
 */
public class Reply extends Page {
//    private List<ReplyDetail> reply;
//    private int total_page;
//    private int current_page;
//
//    public List<ReplyDetail> getReply() {
//        return reply;
//    }
//
//    public void setReply(List<ReplyDetail> reply) {
//        this.reply = reply;
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
//                "data=" + reply +
//                ", total_page=" + total_page +
//                ", current_page=" + current_page +
//                '}';
//    }
////
////    private List<ReplyDetail> commit_list;
////
////    public List<ReplyDetail> getCommit_list() {
////        return commit_list;
////    }
////
////    public void setCommit_list(List<ReplyDetail> commit_list) {
////        this.commit_list = commit_list;
////    }
////
////    @Override
////    public String toString() {
////        return "Reply{" +
////                "commit_list=" + commit_list +
////                '}';
////    }
    private int comment_id;
    private int uid;
    private String nick_name;
    private String head_img;
    private String content;
    private ParentContent parent_content;
    private String release_time;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
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
        return "Reply{" +
                "comment_id=" + comment_id +
                ", uid=" + uid +
                ", nick_name='" + nick_name + '\'' +
                ", head_img='" + head_img + '\'' +
                ", content='" + content + '\'' +
                ", parent_content=" + parent_content +
                ", release_time='" + release_time + '\'' +
                '}';
    }
}
