package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 活动详情
 * Created by guchenkai on 2015/12/8.
 */
public class ActionDetail implements Serializable {
    private int id;//活动ID
    private String banner_url;//Banner_url
    private String html_url;//Html5的地址
    private String title;//活动标题
    private int start_time;//活动开始时间
    private int end_time;//活动结束时间
    private String location;//活动地址
    private String description;//活动描述
    private String status;//活动状态
    private int registration_number;//注册号
    private String type;//类型
    private String explanation;//活动说明
    private String site;//活动现场
    private List<WonderfulReview> wonderful_review;//精彩回顾
    private List<RegUser> reg_user;//报名用户
    private int count_cool;//赞的数量
    private int count_comment;//评论的数量

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        switch (status) {
            case "ONGOING":
                return "进行中";
            case "CLOSE":
                return "截止";
            case "LOOKBACK":
                return "回顾";
        }
        return "";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(int registration_number) {
        this.registration_number = registration_number;
    }

    public String getType() {
        switch (status) {
            case "TEXT":
                return "文字活动";
            case "NEWS":
                return "新闻活动";
        }
        return "";
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<WonderfulReview> getWonderful_review() {
        return wonderful_review;
    }

    public void setWonderful_review(List<WonderfulReview> wonderful_review) {
        this.wonderful_review = wonderful_review;
    }

    public List<RegUser> getReg_user() {
        return reg_user;
    }

    public void setReg_user(List<RegUser> reg_user) {
        this.reg_user = reg_user;
    }

    public int getCount_cool() {
        return count_cool;
    }

    public void setCount_cool(int count_cool) {
        this.count_cool = count_cool;
    }

    public int getCount_comment() {
        return count_comment;
    }

    public void setCount_comment(int count_comment) {
        this.count_comment = count_comment;
    }

    @Override
    public String toString() {
        return "ActionDetail{" +
                "id=" + id +
                ", banner_url='" + banner_url + '\'' +
                ", html_url='" + html_url + '\'' +
                ", title='" + title + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", registration_number=" + registration_number +
                ", type='" + type + '\'' +
                ", explanation='" + explanation + '\'' +
                ", site='" + site + '\'' +
                ", wonderful_review=" + wonderful_review +
                ", reg_user=" + reg_user +
                ", count_cool=" + count_cool +
                ", count_comment=" + count_comment +
                '}';
    }
}
