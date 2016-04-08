package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 回复详情
 * Created by Administrator on 2015/12/25.
 */
public class ReplyDetail implements Serializable {
    private String replay_content;//评论内容
    private String head_img;//用户头像
    private String nick_name;//用户名
    private String modified_time;//评论时间
    private String user_id;//用户id
    private String parent_content;//被评论内容
    private String parent_comment_id;//被评论用户id
//    private int comment_id;//评论id
//    private int uid;//用户id
//    private String nick_name;//用户昵称
//    private String head_img;//用户头像
//    private String content;//回复内容
//    private String release_time;//回复时间
//    private List<Parent> parent_content;


//    comment_id	Int	是	未确定是否为空	评论id
//    uid	Int	是	未确定是否为空	用户id
//    nick_name	String	 是	 未确定是否为空	用户昵称
//    head_img	String	 是	 未确定是否为空	用户头像
//    content	String	 是	 未确定是否为空	回复内容
//    release_time	String	 是	 未确定是否为空	回复时间
//    parent_content	List		参照7.3.4-3	被回复的内容

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getReplay_content() {
        return replay_content;
    }

    public void setReplay_content(String replay_content) {
        this.replay_content = replay_content;
    }

    public String getParent_comment_id() {
        return parent_comment_id;
    }

    public void setParent_comment_id(String parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }

    public String getParent_content() {
        return parent_content;
    }

    public void setParent_content(String parent_content) {
        this.parent_content = parent_content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getModified_time() {
        return modified_time;
    }

    public void setModified_time(String modified_time) {
        this.modified_time = modified_time;
    }

    @Override
    public String toString() {
        return "ReplyDetail{" +
                "replay_content='" + replay_content + '\'' +
                ", head_img='" + head_img + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", modified_time='" + modified_time + '\'' +
                ", user_id='" + user_id + '\'' +
                ", parent_content='" + parent_content + '\'' +
                ", parent_comment_id='" + parent_comment_id + '\'' +
                '}';
    }
}
