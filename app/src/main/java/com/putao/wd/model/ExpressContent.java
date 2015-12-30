package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ExpressContent implements Serializable{
    private String time;
    private String context;
    private String ftime;
    private String areaCode;
    private String areaName;
    private String status;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ExpressContent{" +
                "time='" + time + '\'' +
                ", context='" + context + '\'' +
                ", ftime='" + ftime + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
