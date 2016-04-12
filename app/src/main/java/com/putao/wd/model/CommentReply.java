package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 评论详情中的 评论
 * Created by Administrator on 2016/4/11.
 */
public class CommentReply implements Serializable{
    private String comment_id;
    private String content;
    private List<String> pics;


    private String uid;
    private String nick_name;
    private String head_img;
    private String release_time;
    private int is_essence;
    private int is_like;
    private int count_likes;
    private int count_comments;
    private String parent_comment;

    private List<String> like_lists;

    public List<String> getLike_lists() {
        return like_lists;
    }

    public void setLike_lists(List<String> like_lists) {
        this.like_lists = like_lists;
    }

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

    public int getCount_comments() {
        return count_comments;
    }

    public void setCount_comments(int count_comments) {
        this.count_comments = count_comments;
    }

    public int getCount_likes() {
        return count_likes;
    }

    public void setCount_likes(int count_likes) {
        this.count_likes = count_likes;
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

    public int getIs_like() {
        return is_like;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
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

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
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
        return "CommentReply{" +
                "comment_id='" + comment_id + '\'' +
                ", content='" + content + '\'' +
                ", pics=" + pics +
                ", uid='" + uid + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", head_img='" + head_img + '\'' +
                ", release_time='" + release_time + '\'' +
                ", is_essence=" + is_essence +
                ", is_like=" + is_like +
                ", count_likes=" + count_likes +
                ", count_comments=" + count_comments +
                ", parent_comment='" + parent_comment + '\'' +
                '}';
    }
}
