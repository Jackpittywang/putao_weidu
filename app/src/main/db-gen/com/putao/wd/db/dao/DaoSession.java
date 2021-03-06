package com.putao.wd.db.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.putao.wd.db.entity.CompanionDB;
import com.putao.wd.db.entity.ProvinceDB;
import com.putao.wd.db.entity.CityDB;
import com.putao.wd.db.entity.DistrictDB;

import com.putao.wd.db.dao.ProvinceDBDao;
import com.putao.wd.db.dao.CityDBDao;
import com.putao.wd.db.dao.DistrictDBDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 *
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig provinceDBDaoConfig;
    private final DaoConfig cityDBDaoConfig;
    private final DaoConfig districtDBDaoConfig;
    private final DaoConfig companionDBDaoConfig;

    private final ProvinceDBDao provinceDBDao;
    private final CityDBDao cityDBDao;
    private final CompanionDBDao companionDBDao;
    private final DistrictDBDao districtDBDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        provinceDBDaoConfig = daoConfigMap.get(ProvinceDBDao.class).clone();
        provinceDBDaoConfig.initIdentityScope(type);

        cityDBDaoConfig = daoConfigMap.get(CityDBDao.class).clone();
        cityDBDaoConfig.initIdentityScope(type);

        districtDBDaoConfig = daoConfigMap.get(DistrictDBDao.class).clone();
        districtDBDaoConfig.initIdentityScope(type);

        companionDBDaoConfig = daoConfigMap.get(CompanionDBDao.class).clone();
        companionDBDaoConfig.initIdentityScope(type);

        provinceDBDao = new ProvinceDBDao(provinceDBDaoConfig, this);
        cityDBDao = new CityDBDao(cityDBDaoConfig, this);
        companionDBDao = new CompanionDBDao(companionDBDaoConfig, this);
        districtDBDao = new DistrictDBDao(districtDBDaoConfig, this);

        registerDao(ProvinceDB.class, provinceDBDao);
        registerDao(CityDB.class, cityDBDao);
        registerDao(DistrictDB.class, districtDBDao);
        registerDao(CompanionDB.class, companionDBDao);
    }

    public void clear() {
        provinceDBDaoConfig.getIdentityScope().clear();
        cityDBDaoConfig.getIdentityScope().clear();
        districtDBDaoConfig.getIdentityScope().clear();
        companionDBDaoConfig.getIdentityScope().clear();
    }

    public ProvinceDBDao getProvinceDBDao() {
        return provinceDBDao;
    }

    public CityDBDao getCityDBDao() {
        return cityDBDao;
    }

    public DistrictDBDao getDistrictDBDao() {
        return districtDBDao;
    }

    public CompanionDBDao getCompanionDBDao() {
        return companionDBDao;
    }

}
