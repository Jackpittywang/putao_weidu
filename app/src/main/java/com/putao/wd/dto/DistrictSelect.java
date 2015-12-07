package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 地区选择model
 * Created by guchenkai on 2015/12/7.
 */
public class DistrictSelect implements Serializable {
    private String provinceName;//省份
    private String cityName;//城市
    private String districtName;//城区

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public String toString() {
        return "DistrictSelect{" +
                "provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", districtName='" + districtName + '\'' +
                '}';
    }
}
