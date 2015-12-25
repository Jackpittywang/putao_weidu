package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 *孩子信息
 * Created by zhanghao on 2015/12/25.
 */
public class ChildInfo implements Serializable {
    //宝宝id
    private int baby_id;
    //昵称
    private String baby_name;
    //关系
    private String relation;
    //性别
    private String sex;
    //生日
    private String birthday;

    public int getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(int baby_id) {
        this.baby_id = baby_id;
    }

    public String getBaby_name() {
        return baby_name;
    }

    public void setBaby_name(String baby_name) {
        this.baby_name = baby_name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "ChildInfo{" +
                "baby_id=" + baby_id +
                ", baby_name=" + baby_name +
                ", relation='" + relation + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
