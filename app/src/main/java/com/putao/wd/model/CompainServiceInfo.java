package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CompainServiceInfo implements Serializable {
    private String service_id;
    private String service_name;
    private String service_description;
    private String service_icon;
    private boolean is_relation;//已关注，0未关注
    private int service_type; // 1服务号 2订阅号
    private boolean is_unbunding;//是否可以解绑，0可以1不可以

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

    public boolean is_relation() {
        return is_relation;
    }

    public void setIs_relation(boolean is_relation) {
        this.is_relation = is_relation;
    }

    public int getService_type() {
        return service_type;
    }

    public void setService_type(int service_type) {
        this.service_type = service_type;
    }

    public boolean is_unbunding() {
        return is_unbunding;
    }

    public void setIs_unbunding(boolean is_unbunding) {
        this.is_unbunding = is_unbunding;
    }

    @Override
    public String toString() {
        return "CompainServiceInfo{" +
                "service_id='" + service_id + '\'' +
                ", service_name='" + service_name + '\'' +
                ", service_description='" + service_description + '\'' +
                ", service_icon='" + service_icon + '\'' +
                ", is_relation=" + is_relation +
                ", service_type=" + service_type +
                ", is_unbunding=" + is_unbunding +
                '}';
    }
}
