package com.putao.wd.map;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.MapInfo;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ToastUtils;

import butterknife.Bind;

/**
 * 地图
 * Created by guchenkai on 2015/12/14.
 */
@Deprecated
public class MapActivity extends PTWDActivity {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
   /* public static final String KEY_ACTION_ID = "action_id";

    @Bind(R.id.mv_map)
    MapView mv_map;
    private BaiduMap mBaiduMap;

    private String action_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mBaiduMap = mv_map.getMap();

        action_id = args.getString(KEY_ACTION_ID);
        networkRequest(StartApi.getMap(action_id),
                new SimpleFastJsonCallback<MapInfo>(MapInfo.class, loading) {
                    @Override
                    public void onSuccess(String url, final MapInfo result) {
                        MapUtils.searchAddress(mContext, "", result.getVenue(), new MapUtils.OnSearchMapCallback() {
                            @Override
                            public void onSearchMap(LatLng latLng) {

                                try {
                                    mBaiduMap.setMapStatus(MapUtils.setCenter(latLng, 18));
                                    mBaiduMap.addOverlay(MapUtils.addOverlay(latLng, result.getVenue(), R.drawable.icon_mappin));
                                    mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {
                                            ToastUtils.showToastLong(mContext, marker.getTitle());
                                            return true;
                                        }
                                    });
                                } catch (Exception e) {
                                    finish();
                                } finally {
                                    loading.dismiss();
                                }
                            }
                        });
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[]{StartApi.URL_MAP};
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
    }*/
}
