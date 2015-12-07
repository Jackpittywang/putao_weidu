package com.putao.wd.db.entity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import com.sunnybear.library.model.db.DBEntity;

/**
 * Entity mapped to table "putao_wd_address".
 */
public class AddressDB extends DBEntity {

    private Long id;
    private String name;
    private String province;
    private String province_id;
    private String city;
    private String city_id;
    private String district;
    private String district_id;
    private String street;
    private String mobile;
    private Boolean isDefault;

    public AddressDB() {
    }

    public AddressDB(Long id) {
        this.id = id;
    }

    public AddressDB(Long id, String name, String province, String province_id, String city, String city_id, String district, String district_id, String street, String mobile, Boolean isDefault) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.province_id = province_id;
        this.city = city;
        this.city_id = city_id;
        this.district = district;
        this.district_id = district_id;
        this.street = street;
        this.mobile = mobile;
        this.isDefault = isDefault;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

}
