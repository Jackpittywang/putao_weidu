package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ParentContent implements Serializable {

    private int comment_id;
    private String content;
    private int opus_id;

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

    public int getOpus_id() {
        return opus_id;
    }

    public void setOpus_id(int opus_id) {
        this.opus_id = opus_id;
    }

    @Override
    public String toString() {
        return "ParentContent{" +
                "comment_id=" + comment_id +
                ", content='" + content + '\'' +
                ", opus_id=" + opus_id +
                '}';
    }
}
