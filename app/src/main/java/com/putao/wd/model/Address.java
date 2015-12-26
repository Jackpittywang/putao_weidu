package com.putao.wd.model;

import java.io.Serializable;

/**
 * 收货地址
 * Created by guchenkai on 2015/12/9.
 */
public class Address implements Serializable {
    private String id;//地址ID
    private String uid;//用户id
    private String realname;//姓名
    private String city_id;//城市id
    private String province_id;//省份id
    private String area_id;//城区id
    private String address;//地址
    private String mobile;//手机号码
    private String postcode;//邮编
    private String status;//1是默认地址
    private String created_time;//创建时间
    private String updated_time;//更新时间
    private String addressName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", realname='" + realname + '\'' +
                ", city_id='" + city_id + '\'' +
                ", province_id='" + province_id + '\'' +
                ", area_id='" + area_id + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", postcode='" + postcode + '\'' +
                ", status='" + status + '\'' +
                ", created_time='" + created_time + '\'' +
                ", updated_time='" + updated_time + '\'' +
                ", addressName='" + addressName + '\'' +
                '}';
    }
}
