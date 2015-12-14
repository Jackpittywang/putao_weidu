package com.putao.wd.map;

import android.content.Context;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;

/**
 * Baidu地图工具
 * Created by guchenkai on 2015/12/14.
 */
public class MapUtils {
    private static GeoCoder mSearch;

    static {
        mSearch = GeoCoder.newInstance();
    }

    /**
     * 搜索地址
     *
     * @param context context
     * @param city    城市
     * @param address 地址
     */
    public static void searchAddress(final Context context, String city, String address, final OnSearchMapCallback onSearchMapCallback) {
        mSearch.geocode(new GeoCodeOption().city(city).address(address));
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
                    ToastUtils.showToastLong(context, "没有检索到结果");
                Logger.d(result.toString());
                if (onSearchMapCallback != null)
                    onSearchMapCallback.onSearchMap(result.getLocation());
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
                    ToastUtils.showToastLong(context, "没有检索到结果");
                Logger.d(result.toString());
            }
        });
    }

    /**
     * 设置地图中心
     *
     * @param latLng    中心坐标
     * @param zoomLevel 缩放等级
     */
    public static MapStatusUpdate setCenter(LatLng latLng, float zoomLevel) {
        MapStatus status = new MapStatus.Builder()
                .target(latLng)
                .zoom(zoomLevel).build();
        return MapStatusUpdateFactory.newMapStatus(status);
    }

    /**
     * 添加覆盖物
     *
     * @param latLng       覆盖物坐标
     * @param overlayResId 覆盖物图标
     */
    public static OverlayOptions addOverlay(LatLng latLng,String address, int overlayResId) {
        BitmapDescriptor overlay = BitmapDescriptorFactory.fromResource(overlayResId);
        return new MarkerOptions().position(latLng).icon(overlay).title(address);
    }

    /**
     * 释放搜索
     */
    public static void destroy() {
        mSearch.destroy();
    }

    /**
     *
     */
    public interface OnSearchMapCallback {

        void onSearchMap(LatLng latLng);
    }
}
