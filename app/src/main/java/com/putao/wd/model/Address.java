package com.putao.wd.model;

import java.io.Serializable;

/**
 * 收货地址
 * Created by guchenkai on 2015/12/9.
 */
public class Address implements Serializable {
    private int id;//地址ID
    private int uid;//用户id
    private String realname;//姓名
    private int city_id;//城市id
    private int province_id;//省份id
    private int area_id;//城区id
    private String address;//地址
    private String mobile;//手机号码
    private String postcode;//邮编
    private int status;//1是默认地址
    private int created_time;//创建时间
    private int updated_time;//更新时间
    private String tel;//电话

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreated_time() {
        return created_time;
    }

    public void setCreated_time(int created_time) {
        this.created_time = created_time;
    }

    public int getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(int updated_time) {
        this.updated_time = updated_time;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", uid=" + uid +
                ", realname='" + realname + '\'' +
                ", city_id=" + city_id +
                ", province_id=" + province_id +
                ", area_id=" + area_id +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", postcode='" + postcode + '\'' +
                ", status=" + status +
                ", created_time=" + created_time +
                ", updated_time=" + updated_time +
                ", tel='" + tel + '\'' +
                '}';
    }
}
