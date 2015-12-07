package com.putao.wd.me.address;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.db.entity.CityDB;
import com.putao.wd.db.entity.DistrictDB;
import com.putao.wd.db.entity.ProvinceDB;
import com.sunnybear.library.controller.handler.WeakHandler;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ResourcesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 将城市插入数据库
 * Created by guchenkai on 2015/12/6.
 */
public class CityService extends Service {
    private JSONObject list;//城市列表
    private List<ProvinceDB> mProvinceDBs = new ArrayList<>();//省份列表
    private List<CityDB> mCityDBs = new ArrayList<>();//城区列表
    private List<DistrictDB> mDistrictDBs = new ArrayList<>();//城区列表

    private WeakHandler mHandler;
    private Runnable mRunnable;

    private GlobalApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = (GlobalApplication) getApplication();
        mHandler = new WeakHandler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                String result = ResourcesUtils.getAssetTextFile(GlobalApplication.getInstance(), "city.json");
                list = JSON.parseObject(result);//城市列表
                Logger.w("城市列表获取完成");
                insertProvince();
                Logger.w("省份插入数据库成功");
                insertCity();
                Logger.w("城市插入数据库成功");
                insertDistrict();
                Logger.w("城区插入数据库成功");
                stopSelf();
            }
        };
        new Thread() {
            @Override
            public void run() {
                mHandler.post(mRunnable);
            }
        }.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.w("数据库插入完成");
        mHandler.removeCallbacks(mRunnable);
    }

    /**
     * 将省份插入数据库
     */
    private void insertProvince() {
        JSONArray provinceList = list.getJSONArray("100000");
        for (Object object : provinceList) {
            JSONObject province = (JSONObject) object;
            ProvinceDB provinceDB = new ProvinceDB();
            provinceDB.setProvince_id(province.getString("id"));
            provinceDB.setName(province.getString("name"));
            mProvinceDBs.add(provinceDB);
        }
        Logger.w(mProvinceDBs.toString());
        application.getDataBaseManager(ProvinceDBManager.class).insertList(mProvinceDBs);
    }

    /**
     * 将城市插入数据库
     */
    private void insertCity() {
        for (ProvinceDB provinceDB : mProvinceDBs) {
            JSONArray cityList = list.getJSONArray(provinceDB.getProvince_id());
            if (cityList != null)
                for (Object object : cityList) {
                    JSONObject city = (JSONObject) object;
                    CityDB cityDB = new CityDB();
                    cityDB.setProvince_id(city.getString("parent_id"));
                    cityDB.setCity_id(city.getString("id"));
                    cityDB.setName(city.getString("name"));
                    mCityDBs.add(cityDB);
                }
        }
        Logger.w(mCityDBs.toString());
        application.getDataBaseManager(CityDBManager.class).insertList(mCityDBs);
    }

    /**
     * 将城区插入数据库
     */
    private void insertDistrict() {
        for (CityDB cityDB : mCityDBs) {
            JSONArray districtList = list.getJSONArray(cityDB.getCity_id());
            if (districtList != null)
                for (Object object : districtList) {
                    JSONObject district = (JSONObject) object;
                    DistrictDB districtDB = new DistrictDB();
                    districtDB.setProvince_id(cityDB.getProvince_id());
                    districtDB.setCity_id(district.getString("parent_id"));
                    districtDB.setDistrict_id(district.getString("id"));
                    districtDB.setName(district.getString("name"));
                    mDistrictDBs.add(districtDB);
                }
        }
        Logger.w(mCityDBs.toString());
        application.getDataBaseManager(DistrictDBManager.class).insertList(mDistrictDBs);
    }
}
