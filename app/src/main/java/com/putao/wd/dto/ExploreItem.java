package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 探索号bean文件
 * Created by yanghx on 2015/12/8.
 */
@Deprecated
public class ExploreItem implements Serializable {

    private String id;
    private String iconUrl;
    private int iconNum;//图文混排排时显示图片张数
    private boolean isMixed;//状态值 false表示全图片 true表示图文混排
    private String skill_name;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getIconNum() {
        return iconNum;
    }

    public void setIconNum(int iconNum) {
        this.iconNum = iconNum;
    }

    public boolean isMixed() {
        return isMixed;
    }

    public void setIsMixed(boolean isMixed) {
        this.isMixed = isMixed;
    }

    public String getSkill_name() {
        return skill_name;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ExploreItem{" +
                "id='" + id + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", iconNum='" + iconNum + '\'' +
                ", isMixed=" + isMixed +
                ", skill_name='" + skill_name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
