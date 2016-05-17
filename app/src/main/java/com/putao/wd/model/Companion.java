package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 陪伴首页数据
 * Created by zhanghao on 2015/12/8.
 */
public class Companion implements Serializable {
    private String service_id;//id
    private String service_icon;//游戏ICON
    private String service_name;//
    private String service_description;//游戏介绍
    private String last_pull_id; //最后拉取此服务号数据的id
    private int service_type;// //产品类型  0葡萄订阅 1服务号 2订阅号
    private ArrayList<Companion> second_level_lists;//是否为订阅号  0不是 1是
    private int relation_time;//更新时间
    private int is_relation;//是否关注
    private boolean is_unbunding;//是否可以解绑
    private String sort;//排序，由大到小
    private boolean isShowRed;
    private ServiceMessage auto_reply;//自动回复内容
    private String substr;
//    private ArrayList<String> notDownloadIds = new ArrayList<>(); //一个还没有下载文章的集合，在推送新的文章和初始化的时候会添加数据,请求数据完成之后清空数据
    private int notDownloadCount;
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


    public boolean is_unbunding() {
        return is_unbunding;
    }

    public void setIs_unbunding(boolean is_unbunding) {
        this.is_unbunding = is_unbunding;
    }

    public int getNotDownloadCount() {
        return notDownloadCount;
    }

    public void setNotDownloadCount(int notDownloadCount) {
        this.notDownloadCount = notDownloadCount;
    }

    public ServiceMessage getAuto_reply() {
        return auto_reply;
    }

    public void setAuto_reply(ServiceMessage auto_reply) {
        this.auto_reply = auto_reply;
    }

    public String getLast_pull_id() {
        return last_pull_id;
    }

    public void setLast_pull_id(String last_pull_id) {
        this.last_pull_id = last_pull_id;
    }

    public int getService_type() {
        return service_type;
    }

    public void setService_type(int service_type) {
        this.service_type = service_type;
    }

    public ArrayList<Companion> getSecond_level_lists() {
        return second_level_lists;
    }

    public void setSecond_level_lists(ArrayList<Companion> second_level_lists) {
        this.second_level_lists = second_level_lists;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSubstr() {
        return substr;
    }

    public void setSubstr(String substr) {
        this.substr = substr;
    }
}
