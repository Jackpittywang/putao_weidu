package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 活动详情
 * Created by guchenkai on 2015/12/8.
 */
public class ActionDetail implements Serializable {

    private String article_id;//活动ID
    private String article_title;//活动标题
    private String article_tag;//活动标签
    private String article_contents;//活动内容
    private String type;//文章类型
    private int attend_count;//参与人数
    private int sub_status;//是否收藏0未收藏，1已收藏

    private List<ActionDetailComment> attend_list;

    public String getArticle_contents() {
        return article_contents;
    }

    public void setArticle_contents(String article_contents) {
        this.article_contents = article_contents;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getArticle_tag() {
        return article_tag;
    }

    public void setArticle_tag(String article_tag) {
        this.article_tag = article_tag;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public int getAttend_count() {
        return attend_count;
    }

    public void setAttend_count(int attend_count) {
        this.attend_count = attend_count;
    }

    public List<ActionDetailComment> getAttend_list() {
        return attend_list;
    }

    public void setAttend_list(List<ActionDetailComment> attend_list) {
        this.attend_list = attend_list;
    }

    public int getSub_status() {
        return sub_status;
    }

    public void setSub_status(int sub_status) {
        this.sub_status = sub_status;
    }





















    private String id;//活动ID
    private String banner_url;//Banner_url
    private String html_url;//Html5的地址
    private String title;//活动标题
    private String label;//活动标签
    private String start_time;//活动开始时间
    private String end_time;//活动结束时间
    private String location;//线上或线下
    private String description;//活动描述
    private String status;//活动状态
    private int registration_number;//注册号
    //   private String type;//类型
    private String explanation;//活动说明
    private String site;//活动现场
    private String wonderful_review;//精彩回顾
    private int count_cool;//赞的数量
    private int count_comment;//评论的数量
    private boolean isParticipate;//是否报名参加
    private boolean is_user_like;//是否点过赞
    private List<RegUser> reg_user;//报名用户


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
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

    public int getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(int registration_number) {
        this.registration_number = registration_number;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getWonderful_review() {
        return wonderful_review;
    }

    public void setWonderful_review(String wonderful_review) {
        this.wonderful_review = wonderful_review;
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

    public void setParticipate(boolean participate) {
        isParticipate = participate;
    }

    public List<RegUser> getReg_user() {
        return reg_user;
    }

    public void setReg_user(List<RegUser> reg_user) {
        this.reg_user = reg_user;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public boolean is_user_like() {
        return is_user_like;
    }

    public void setIs_user_like(boolean is_user_like) {
        this.is_user_like = is_user_like;
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

    public boolean isParticipate() {
        return isParticipate;
    }

    public void setIsParticipate(boolean isParticipate) {
        this.isParticipate = isParticipate;
    }

    @Override
    public String toString() {
        return "ActionDetail{" +
                "id='" + id + '\'' +
                ", banner_url='" + banner_url + '\'' +
                ", html_url='" + html_url + '\'' +
                ", title='" + title + '\'' +
                ", label='" + label + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", registration_number=" + registration_number +
                ", type='" + type + '\'' +
                ", explanation='" + explanation + '\'' +
                ", site='" + site + '\'' +
                ", wonderful_review='" + wonderful_review + '\'' +
                ", count_cool=" + count_cool +
                ", count_comment=" + count_comment +
                ", isParticipate=" + isParticipate +
                ", is_user_like=" + is_user_like +
                ", reg_user=" + reg_user +
                '}';
    }
}
