package com.putao.wd.model;

import java.util.List;

/**
 * Created by yanghx on 2015/12/20.
 */
public class CommentList extends Page {
    private List<Comment> comment;

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentList{" +
                "comment=" + comment +
                '}';
    }
}
