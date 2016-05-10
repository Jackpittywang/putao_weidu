package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ServiceMenu implements Serializable {
    private String type;//click表示请求平台的内容，此时key必填；view表示请求第三方链接，此时url必填
    private String name;//菜单名
    private String key;//表示要请求平台的内容，如历史推送消息id
    private String url;//跳转第三方链接
    private ArrayList<ServiceMenu> sub_button;//	二级菜单信息

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<ServiceMenu> getSub_button() {
        return sub_button;
    }

    public void setSub_button(ArrayList<ServiceMenu> sub_button) {
        this.sub_button = sub_button;
    }
}
