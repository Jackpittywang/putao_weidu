package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 评论详情
 * Created by Administrator on 2016/4/11.
 */
public class CompanionCommentDetail implements Serializable{

    private boolean is_comment  ;
    private boolean is_pic  ;
    private boolean is_becommented  ;
    private CommentReply comment;
    public List<ReplyLists> reply_lists;
    public CommentReply getComment() {
        return comment;
    }
    public void setComment(CommentReply comment) {
        this.comment = comment;
    }
    public List<ReplyLists> getReply_lists() {
        return reply_lists;
    }
    public void setReply_lists(List<ReplyLists> reply_lists) {
        this.reply_lists = reply_lists;
    }

    public boolean is_becommented() {
        return is_becommented;
    }

    public void setIs_becommented(boolean is_becommented) {
        this.is_becommented = is_becommented;
    }

    public boolean is_comment() {
        return is_comment;
    }

    public void setIs_comment(boolean is_comment) {
        this.is_comment = is_comment;
    }

    public boolean is_pic() {
        return is_pic;
    }

    public void setIs_pic(boolean is_pic) {
        this.is_pic = is_pic;
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
