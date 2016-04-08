package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by zhanghao on 2015/12/8.
 */
public class Companion implements Serializable {
    private String game_id;//游戏id
    private String game_icon;//游戏ICON
    private String game_title;//
    private String game_subtitle;//游戏介绍
    private int time;//更新时间
    private String sub_status;//订阅状态，0未订阅，1已订阅

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getGame_icon() {
        return game_icon;
    }

    public void setGame_icon(String game_icon) {
        this.game_icon = game_icon;
    }

    public String getGame_subtitle() {
        return game_subtitle;
    }

    public void setGame_subtitle(String game_subtitle) {
        this.game_subtitle = game_subtitle;
    }

    public String getSub_status() {
        return sub_status;
    }

    public void setSub_status(String sub_status) {
        this.sub_status = sub_status;
    }

    public String getGame_title() {
        return game_title;
    }

    public void setGame_title(String game_title) {
        this.game_title = game_title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Companion{" +
                "game_id='" + game_id + '\'' +
                ", game_icon='" + game_icon + '\'' +
                ", game_subtitle='" + game_subtitle + '\'' +
                ", sub_status='" + sub_status + '\'' +
                '}';
    }
}
