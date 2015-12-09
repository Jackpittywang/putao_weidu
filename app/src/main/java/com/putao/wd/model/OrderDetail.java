package com.putao.wd.model;

import java.io.Serializable;

/**
 * 订单详情
 * Created by guchenkai on 2015/12/9.
 */
public class OrderDetail implements Serializable {
    private String express_status;//监控状态
    private String express_name;//快递公司
    private String express_code;//快递单号
    private String state;//快递单当前签收状态
    private String content;//物流信息

    public String getExpress_status() {
        return express_status;
    }

    public void setExpress_status(String express_status) {
        this.express_status = express_status;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getExpress_code() {
        return express_code;
    }

    public void setExpress_code(String express_code) {
        this.express_code = express_code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "express_status='" + express_status + '\'' +
                ", express_name='" + express_name + '\'' +
                ", express_code='" + express_code + '\'' +
                ", state='" + state + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
