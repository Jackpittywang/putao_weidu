package com.putao.wd.db;

import com.putao.wd.db.dao.DaoMaster;
import com.putao.wd.db.dao.DistrictDBDao;
import com.putao.wd.db.entity.DistrictDB;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.WhereCondition;

/**
 * 城区操作
 * Created by guchenkai on 2015/12/6.
 */
public class DistrictDBManager extends DataBaseManager<DistrictDB, String> {
    private static DistrictDBManager mInstance;

    public static DistrictDBManager getInstance(DaoMaster.OpenHelper helper) {
        if (mInstance == null)
            mInstance = new DistrictDBManager(helper);
        return mInstance;
    }

    public DistrictDBManager(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<DistrictDB, String> getAbstractDao() {
        return daoSession.getDistrictDBDao();
    }

    /**
     * 根据城市id获取城区名
     *
     * @param cityId 城市id
     * @return 城区名列表
     */
    public List<String> getDistrictNamesByCityId(String cityId) {
        List<String> districtNames = new ArrayList<>();
        List<DistrictDB> districtDBs = getQueryBuilder().where(DistrictDBDao.Properties.City_id.eq(cityId)).list();
        for (DistrictDB districtDB : districtDBs) {
            districtNames.add(districtDB.getName());
        }
        return districtNames;
    }

    /**
     * 根据城市名称获取城区名
     *
     * @param cityName 城市名称
     * @return 城区名列表
     */
    public List<String> getDistrictNamesByCityName(String cityName) {
        List<String> districtNames = new ArrayList<>();
        List<DistrictDB> districtDBs = getQueryBuilder().where(
                new WhereCondition.StringCondition("CITY_ID=" + "(SELECT CITY_ID FROM putao_wd_city WHERE NAME=\"" + cityName + "\")"))
                .list();
        for (DistrictDB districtDB : districtDBs) {
            districtNames.add(districtDB.getName());
        }
        return districtNames;
    }

    /**
     * 根据城区名称获得城区id
     *
     * @param districtName 城区名称
     * @return 城区id
     */
    public String getDistrictId(String districtName) {
        DistrictDB districtDB = getQueryBuilder().where(DistrictDBDao.Properties.Name.eq(districtName)).uniqueOrThrow();
        return districtDB.getDistrict_id();
    }

    /**
     * 根据城区名称获得城区id
     *
     * @param districtId 城区名称
     * @return 城区id
     */
    public String getdistrictNameByDistrictId(String districtId) {
        DistrictDB districtDB = getQueryBuilder().where(DistrictDBDao.Properties.District_id.eq(districtId)).uniqueOrThrow();
        return districtDB.getName();
    }
}
