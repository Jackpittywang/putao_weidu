package com.putao.wd.model;

import java.io.Serializable;

/**
 * 活动详情
 * Created by guchenkai on 2015/12/8.
 */
public class GpushMessageAccNumber implements Serializable {

    private String service_id;
    private String id;
    private String type;

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
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