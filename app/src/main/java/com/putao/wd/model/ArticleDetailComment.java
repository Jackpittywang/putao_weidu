package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 陪伴评论V1.3.0
 * Created by zhanghao on 2015/04/10.
 */
public class ArticleDetailComment implements Serializable {
    private String comment_id;//评论ID
    private String content;//评论内容
    private String uid;//评论归属用户
    private String release_time;//评论时间戳
    private String nick_name;//归属用户昵称
    private String head_img;//头像
    private boolean is_like;//赞
    private int count_likes;//点赞数
    private int count_comments;//评论数
//    private List<String> pics;//图片
    private List<ArticleDetailComment> parent_comment;//父评论，可为空

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ArticleDetailComment> getParent_comment() {
        return parent_comment;
    }

    public void setParent_comment(List<ArticleDetailComment> parent_comment) {
        this.parent_comment = parent_comment;
    }

    public boolean is_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public int getCount_likes() {
        return count_likes;
    }

    public void setCount_likes(int count_likes) {
        this.count_likes = count_likes;
    }

    public int getComment_count() {
        return count_comments;
    }

    public void setComment_count(int count_comments) {
        this.count_comments = count_comments;
    }
//
//    public List<String> getPics() {
//        return pics;
//    }
//
//    public void setPics(List<String> pics) {
//        this.pics = pics;
//    }

    @Override
    public String toString() {
        return "ArticleDetailComment{" +
                "comment_id='" + comment_id + '\'' +
                ", uid='" + uid + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", head_img='" + head_img + '\'' +
                ", release_time='" + release_time + '\'' +
                ", content='" + content + '\'' +
                ", is_like=" + is_like +
                ", count_likes=" + count_likes +
                ", count_comments=" + count_comments +
                ", parent_comment=" + parent_comment +
                '}';
    }
}
