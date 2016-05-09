package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/8.
 */
public class SubscribeList implements Serializable {
    private String service_id; //订阅号id
    private String service_name;//订阅号名称
    private String service_description;//副标题
    private String service_icon; //订阅号图标
    private int service_type;//共建号类型， 1为服务号，2为订阅号
    private boolean is_unbunding;//是否可以解绑，0可以 1不可以
    private int sort; //排序，由大到小
    private boolean is_relation;//是否关注，0未关注 1已关注

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
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

    public String getService_icon() {
        return service_icon;
    }

    public void setService_icon(String service_icon) {
        this.service_icon = service_icon;
    }

    public int getService_type() {
        return service_type;
    }

    public void setService_type(int service_type) {
        this.service_type = service_type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean is_relation() {
        return is_relation;
    }

    public void setIs_relation(boolean is_relation) {
        this.is_relation = is_relation;
    }

    public boolean is_unbunding() {
        return is_unbunding;
    }

    public void setIs_unbunding(boolean is_unbunding) {
        this.is_unbunding = is_unbunding;
    }

    @Override
    public String toString() {
        return "SubscribeList{" +
                "service_id='" + service_id + '\'' +
                ", service_name='" + service_name + '\'' +
                ", service_description='" + service_description + '\'' +
                ", service_icon='" + service_icon + '\'' +
                ", service_type=" + service_type +
                ", is_unbunding=" + is_unbunding +
                ", sort=" + sort +
                ", is_relation=" + is_relation +
                '}';
    }
}
