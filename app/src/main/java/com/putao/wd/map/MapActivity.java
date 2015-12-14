package com.putao.wd.map;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.ToastUtils;

import butterknife.Bind;

/**
 * 地图
 * Created by guchenkai on 2015/12/14.
 */
public class MapActivity extends PTWDActivity {
    @Bind(R.id.mv_map)
    MapView mv_map;
    private BaiduMap mBaiduMap;

    private String address;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        address = "上海徐汇区中国电子贝岭大厦";

        mBaiduMap = mv_map.getMap();
        loading.show();
        MapUtils.searchAddress(mContext, "", address,
                new MapUtils.OnSearchMapCallback() {
                    @Override
                    public void onSearchMap(LatLng latLng) {
                        mBaiduMap.setMapStatus(MapUtils.setCenter(latLng, 18));
                        mBaiduMap.addOverlay(MapUtils.addOverlay(latLng, address, R.drawable.icon_logistics_flow_latest));
                        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                ToastUtils.showToastLong(mContext, marker.getTitle());
                                return true;
                            }
                        });
                        loading.dismiss();
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onDestroy时执行mMapView.onResume()，实现地图生命周期管理
        mv_map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onDestroy时执行mMapView.onPause()，实现地图生命周期管理
        mv_map.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mv_map.onDestroy();
        MapUtils.destroy();
    }
}