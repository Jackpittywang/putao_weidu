package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zhanghao on 2015/12/8.
 */
public class Companion implements Serializable {
    private String service_id;//id
    private String service_icon;//游戏ICON
    private String service_name;//
    private String service_description;//游戏介绍
    private int relation_time;//更新时间
    private int is_relation;//订阅状态，0未订阅，1已订阅
    private boolean is_unbunding;//是否可以解绑
    private int sort;//
    private boolean isShowRed;
    private ServiceMessage auto_reply;//
    private ArrayList<String> notDownloadIds = new ArrayList<>(); //一个还没有下载文章的集合，在推送新的文章和初始化的时候会添加数据,请求数据完成之后清空数据


    public boolean isShowRed() {
        return isShowRed;
    }

    public void setIsShowRed(boolean isShowRed) {
        this.isShowRed = isShowRed;
    }

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

    public int getRelation_time() {
        return relation_time;
    }

    public void setRelation_time(int relation_time) {
        this.relation_time = relation_time;
    }

    public int getIs_relation() {
        return is_relation;
    }

    public void setIs_relation(int is_relation) {
        this.is_relation = is_relation;
    }

    public ArrayList<String> getNotDownloadIds() {
        return notDownloadIds;
    }

    public boolean is_unbunding() {
        return is_unbunding;
    }

    public void setIs_unbunding(boolean is_unbunding) {
        this.is_unbunding = is_unbunding;
    }

    public void setNotDownloadIds(ArrayList<String> notDownloadIds) {
        this.notDownloadIds = notDownloadIds;
    }
    public ServiceMessage getAuto_reply() {
        return auto_reply;
    }

    public void setAuto_reply(ServiceMessage auto_reply) {
        this.auto_reply = auto_reply;
    }

}
