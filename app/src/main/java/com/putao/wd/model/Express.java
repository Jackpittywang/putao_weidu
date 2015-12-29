package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/29.
 */
public class Express implements Serializable {
    private String id;
    private String express_status;//监控状态
    private String express_message;
    private String express_name;//快递公司
    private String express_code;//快递单号
    private String isCheck;
    private int state;//快递单当前签收状态
    private ArrayList<ExpressContent> content;
    private String update_time;//快递单当前签收状态
    private String salt;//快递单当前签收状态
    private ArrayList<ExpressContent> real_content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpress_status() {
        return express_status;
    }

    public void setExpress_status(String express_status) {
        this.express_status = express_status;
    }

    public String getExpress_message() {
        return express_message;
    }

    public void setExpress_message(String express_message) {
        this.express_message = express_message;
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

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<ExpressContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<ExpressContent> content) {
        this.content = content;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public ArrayList<ExpressContent> getReal_content() {
        return real_content;
    }

    public void setReal_content(ArrayList<ExpressContent> real_content) {
        this.real_content = real_content;
    }

    @Override
    public String toString() {
        return "Express{" +
                "id='" + id + '\'' +
                ", express_status='" + express_status + '\'' +
                ", express_message='" + express_message + '\'' +
                ", express_name='" + express_name + '\'' +
                ", express_code='" + express_code + '\'' +
                ", isCheck='" + isCheck + '\'' +
                ", state='" + state + '\'' +
                ", content=" + content +
                ", update_time='" + update_time + '\'' +
                ", salt='" + salt + '\'' +
                ", real_content=" + real_content +
                '}';
    }
}
