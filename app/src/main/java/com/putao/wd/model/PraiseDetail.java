package com.putao.wd.model;

import java.io.Serializable;

/**
 * 赞详情
 * Created by Administrator on 2015/12/25.
 */
public class PraiseDetail implements Serializable {
    private String content;//被赞内容
    private String comment_id;//内容ID

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    @Override
    public String toString() {
        return "PraiseDetail{" +
                "content='" + content + '\'' +
                ", comment_id='" + comment_id + '\'' +
                '}';
    }
}
