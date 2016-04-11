package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 公众号首页消息获取
 * Created by zhanghao on 2015/04/11.
 */
public class ServiceSendData implements Serializable {
    private String id;
    private String type = "article";

    public ServiceSendData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
