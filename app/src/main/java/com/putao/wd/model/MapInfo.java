package com.putao.wd.model;

import java.io.Serializable;

/**
 * 地图信息
 * Created by guchenkai on 2015/12/8.
 */
public class MapInfo implements Serializable {
    private String latitude;//纬度
    private String longitude;//经度
    private String venue;//地名

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "MapInfo{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", venue='" + venue + '\'' +
                '}';
    }
}
