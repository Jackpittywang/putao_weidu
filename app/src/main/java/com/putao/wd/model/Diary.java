package com.putao.wd.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * 成长日记首页
 * Created by zhanghao on 2015/12/8.
 */
public class Diary implements Serializable {
    private int tag_type;
    private String config_id;
    private String tag_name;
    private String title;
    private String ask;
    private String option;
    private String answer;
    private int option_type;
    private String device_name;
    private int create_time;

    //-----------本地字段
    private boolean isFinish;//是否答过
    private boolean isTrue;//是否正确
    private String falseAnswer;//错误答案



    public int getTag_type() {
        return tag_type;
    }

    public void setTag_type(int tag_type) {
        this.tag_type = tag_type;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getOption_type() {
        return option_type;
    }

    public void setOption_type(int option_type) {
        this.option_type = option_type;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setIsTrue(boolean isTrue) {
        this.isTrue = isTrue;
    }

    public String getFalseAnswer() {
        return falseAnswer;
    }

    public void setFalseAnswer(String falseAnswer) {
        this.falseAnswer = falseAnswer;
    }

    public String getConfig_id() {
        return config_id;
    }

    public void setConfig_id(String config_id) {
        this.config_id = config_id;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "tag_type=" + tag_type +
                ", tag_name='" + tag_name + '\'' +
                ", title='" + title + '\'' +
                ", ask='" + ask + '\'' +
                ", option='" + option + '\'' +
                ", answer='" + answer + '\'' +
                ", option_type=" + option_type +
                ", device_name='" + device_name + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
