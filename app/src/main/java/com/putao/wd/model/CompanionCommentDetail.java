package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 评论详情
 * Created by Administrator on 2016/4/11.
 */
public class CompanionCommentDetail implements Serializable{

    private int is_comment  ;
    private int is_pic  ;
    private int is_becommented  ;
    private CommentReply comment;
    public List<ReplyLists> reply_lists;

    public CommentReply getComment() {
        return comment;
    }

    public void setComment(CommentReply comment) {
        this.comment = comment;
    }

    public int getIs_becommented() {
        return is_becommented;
    }

    public void setIs_becommented(int is_becommented) {
        this.is_becommented = is_becommented;
    }

    public int getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(int is_comment) {
        this.is_comment = is_comment;
    }

    public int getIs_pic() {
        return is_pic;
    }

    public void setIs_pic(int is_pic) {
        this.is_pic = is_pic;
    }

    public List<ReplyLists> getReply_lists() {
        return reply_lists;
    }

    public void setReply_lists(List<ReplyLists> reply_lists) {
        this.reply_lists = reply_lists;
    }

    @Override
    public String toString() {
        return "CompanionCommentDetail{" +
                "comment=" + comment +
                ", is_comment=" + is_comment +
                ", is_pic=" + is_pic +
                ", is_becommented=" + is_becommented +
                ", reply_lists=" + reply_lists +
                '}';
    }
}
