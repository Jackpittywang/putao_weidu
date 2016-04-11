package com.putao.wd.model;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by zhanghao on 2015/12/8.
 */
public class Companion implements Serializable {
    private String service_id;//游戏id
    private String service_icon;//游戏ICON
    private String service_name;//
    private String service_description;//游戏介绍
    private int time;//更新时间
    private int is_relation;//订阅状态，0未订阅，1已订阅
    private int num = new Random().nextInt(200) + 1;//数字显示

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_icon() {
        return service_icon;
    }

    public void setService_icon(String service_icon) {
        this.service_icon = service_icon;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_description() {
        return service_description;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getIs_relation() {
        return is_relation;
    }

    public void setIs_relation(int is_relation) {
        this.is_relation = is_relation;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
