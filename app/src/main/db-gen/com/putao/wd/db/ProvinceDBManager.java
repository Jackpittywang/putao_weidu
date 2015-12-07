package com.putao.wd.db;

import com.putao.wd.db.dao.DaoMaster;
import com.putao.wd.db.entity.ProvinceDB;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * 省份操作
 * Created by guchenkai on 2015/12/6.
 */
public class ProvinceDBManager extends DataBaseManager<ProvinceDB, String> {
    private static ProvinceDBManager mInstance;

    public static ProvinceDBManager getInstance(DaoMaster.OpenHelper helper) {
        if (mInstance == null)
            mInstance = new ProvinceDBManager(helper);
        return mInstance;
    }

    public ProvinceDBManager(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<ProvinceDB, String> getAbstractDao() {
        return daoSession.getProvinceDBDao();
    }

    /**
     * 获得所有省份名
     *
     * @return 省份名
     */
    public List<String> getProvinceNames() {
        List<String> provinces = new ArrayList<>();
        List<ProvinceDB> provinceDBs = loadAll();
        for (ProvinceDB provinceDB : provinceDBs) {
            provinces.add(provinceDB.getName());
        }
        return provinces;
    }
}
