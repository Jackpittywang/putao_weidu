package com.putao.wd.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ReplyHeaderInfo {
    private String pic ;
    private String content ;
    private int count_comments ;
    private int count_likes;
    private boolean is_like;
    private List<String> likes_icon;

    public List<String> getLikes_icon() {
        return likes_icon;
    }

    public void setLikes_icon(List<String> likes_icon) {
        this.likes_icon = likes_icon;
    }

    public boolean getIs_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount_comments() {
        return count_comments;
    }

    public void setCount_comments(int count_comments) {
        this.count_comments = count_comments;
    }

    public int getCount_likes() {
        return count_likes;
    }

    public void setCount_likes(int count_likes) {
        this.count_likes = count_likes;
    }
}
