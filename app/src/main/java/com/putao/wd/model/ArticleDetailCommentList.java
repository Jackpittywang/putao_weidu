package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 陪伴评论V1.3.0
 * Created by zhanghao on 2015/04/10.
 */
public class ArticleDetailCommentList implements Serializable{
    private boolean is_pic;  //是否可以发表图片
    private boolean is_comment; //是否可以评论
    private boolean is_becommented;//是否可以对评论进行回复
    private List<ArticleDetailComment> comment_lists;//评论列表

    public boolean is_pic() {
        return is_pic;
    }

    public void setIs_pic(boolean is_pic) {
        this.is_pic = is_pic;
    }

    public boolean is_comment() {
        return is_comment;
    }

    public void setIs_comment(boolean is_comment) {
        this.is_comment = is_comment;
    }

    public boolean is_becommented() {
        return is_becommented;
    }

    public void setIs_becommented(boolean is_becommented) {
        this.is_becommented = is_becommented;
    }

    public List<ArticleDetailComment> getComment_lists() {
        return comment_lists;
    }

    public void setComment_lists(List<ArticleDetailComment> comment_lists) {
        this.comment_lists = comment_lists;
    }

    @Override
    public String toString() {
        return "ArticleDetailComment{" +
                "is_pic='" + is_pic + '\'' +
                ", is_comment='" + is_comment + '\'' +
                ", is_becommented='" + is_becommented + '\'' +
                ", comment_lists='" + comment_lists + '\'' +
                '}';
    }
}
