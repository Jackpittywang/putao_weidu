package com.putao.wd.db;

import com.putao.wd.db.dao.CompanionDBDao;
import com.putao.wd.db.dao.DaoMaster;
import com.putao.wd.db.entity.CompanionDB;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * 城市操作
 * Created by guchenkai on 2015/12/6.
 */
public class CompanionDBManager extends DataBaseManager<CompanionDB, String> {
    private static CompanionDBManager mInstance;

    public static CompanionDBManager getInstance(DaoMaster.OpenHelper helper) {
        if (mInstance == null)
            mInstance = new CompanionDBManager(helper);
        return mInstance;
    }

    public CompanionDBManager(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<CompanionDB, String> getAbstractDao() {
        return daoSession.getCompanionDBDao();
    }

    /**
     * 根据推送id获取推送数据
     *
     * @param id 省份id
     * @return 城市名列表
     */
    public CompanionDB getCompanInfoById(String id) {
        return getQueryBuilder().where(CompanionDBDao.Properties.id.eq(id)).unique();
    }

    /**
     * 获取没有还没有获取到数据的推送id
     */
    public List<String> getNotDownloadIds() {
        List<String> notDownloadIds = new ArrayList<>();
        List<CompanionDB> companionDBs = getQueryBuilder().where(CompanionDBDao.Properties.is_download.eq("0")).listLazy();
        for (CompanionDB companionDB : companionDBs) {
            notDownloadIds.add(companionDB.getId());
        }
        return notDownloadIds;
    }

    /**
     * 获取已经下载的文章列表
     */
    public List<CompanionDB> getDownloadArticles() {
        return getQueryBuilder().where(CompanionDBDao.Properties.is_download.eq("1")).build().list();
    }

    /**
     * 设置文章的下载状态
     */
    public void setDownloadFinish(String id) {
        CompanionDB unique = getQueryBuilder().where(CompanionDBDao.Properties.id.eq(id)).unique();
        unique.setIsDownload("1");
        update(unique);
    }
}
