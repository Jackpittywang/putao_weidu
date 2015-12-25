package com.putao.wd.dto;
import java.io.Serializable;

/**
 * Created by wango on 2015/12/3.
 */
@Deprecated
public class MessageReplyItem implements Serializable {
    private String id;//回复ID
    private String headIconUrl;//用户头像地址
    private String replyUserNickname;//用户昵称
    private String date;//时间
    private String comment;//评论
    private String repliedUserName;//被回复人用户名
    private String repliedcontent;//被评论内容



    public String getId() {
        return id;
    }

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.headIconUrl = headIconUrl;
    }

    public String getReplyUserNickname() {
        return replyUserNickname;
    }

    public void setReplyUserNickname(String replyUserNickname) {
        this.replyUserNickname = replyUserNickname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRepliedUserName() {
        return repliedUserName;
    }

    public void setRepliedUserName(String repliedUserName) {
        this.repliedUserName = repliedUserName;
    }

    public String getRepliedcontent() {
        return repliedcontent;
    }

    public void setRepliedcontent(String repliedcontent) {
        this.repliedcontent = repliedcontent;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "ProductItem{" +
                "id='" + id + '\'' +
                "headIconUrl='" + headIconUrl + '\'' +
                "headIconUrl='" + replyUserNickname + '\'' +
                "headIconUrl='" + date + '\'' +
                "headIconUrl='" + comment + '\'' +
                "headIconUrl='" + repliedUserName + '\'' +
                "headIconUrl='" + repliedcontent + '\'' +
                '}';
    }

}
