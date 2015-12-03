package com.putao.wd.dto;

import java.io.Serializable;

/**
 * Created by wango on 2015/12/3.
 */
public class MessagePraiseItem implements Serializable {
    private String id;//回复ID
    private String headIconUrl;//用户头像地址
    private String praiseUserNickname;//用户昵称
    private String date;//时间
    //private String comment;//评论
    private String praisedUserName;//被回复人用户名
    private String praisedcontent;//被评论内容



    public String getId() {
        return id;
    }

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.headIconUrl = headIconUrl;
    }

    public String getPraiseUserNickname() {
        return praiseUserNickname;
    }

    public void setPraiseUserNickname(String praiseUserNickname) {
        this.praiseUserNickname = praiseUserNickname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//    public String getComment() {
//        return comment;
//    }

//    public void setComment(String comment) {
//        this.comment = comment;
//    }

    public String getPraisedUserName() {
        return praisedUserName;
    }

    public void setPraisedUserName(String praisedUserName) {
        this.praisedUserName = praisedUserName;
    }

    public String getPraisedcontent() {
        return praisedcontent;
    }

    public void setPraisedcontent(String praisedcontent) {
        this.praisedcontent = praisedcontent;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "ProductItem{" +
                "id='" + id + '\'' +
                "headIconUrl='" + headIconUrl + '\'' +
                "headIconUrl='" + praiseUserNickname + '\'' +
                "headIconUrl='" + date + '\'' +
                //"headIconUrl='" + comment + '\'' +
                "headIconUrl='" + praisedUserName + '\'' +
                "headIconUrl='" + praisedcontent + '\'' +
                '}';
    }

}
