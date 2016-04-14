package com.putao.wd.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.putao.wd.db.dao.CompanionDBDao;
import com.putao.wd.db.dao.DaoMaster;
import com.putao.wd.db.entity.CompanionDB;
import com.putao.wd.model.ServiceMessageList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * 获取没有还没有获取到数据的推送id
     */
    public ArrayList<String> getNotDownloadIds(String serviceId) {
        ArrayList<String> notDownloadIds = new ArrayList<>();
        List<CompanionDB> companionDBs = getQueryBuilder().where(CompanionDBDao.Properties.is_download.eq("0"), CompanionDBDao.Properties.service_id.eq(serviceId)).list();
        for (CompanionDB companionDB : companionDBs) {
            notDownloadIds.add(companionDB.getId());
        }
        return notDownloadIds;
    }

    /**
     * 获取已经下载的文章列表
     */
    public List<CompanionDB> getDownloadArticles(String service_id) {
        return getQueryBuilder().where(CompanionDBDao.Properties.service_id.eq(service_id), CompanionDBDao.Properties.is_download.eq("1")).build().list();
    }

    /**
     * 设置文章的下载状态
     */
    public void updataDownloadFinish(String service_id, ServiceMessageList serviceMessageList) {
        CompanionDB unique = getQueryBuilder().where(CompanionDBDao.Properties.id.eq(serviceMessageList.getId())).unique();
        unique.setContent_lists(JSON.toJSONString(serviceMessageList.getContent_lists()));
        unique.setIsDownload("1");
        unique.setType(serviceMessageList.getType());
        unique.setRelease_time(serviceMessageList.getRelease_time() + "");
        unique.setService_id(service_id);
        update(unique);
    }

    /**
     * 插入没有带下载的文章
     */
    public void insertFixDownload(String service_id, String id) {
        Random random = new Random();
        insert(new CompanionDB(id, service_id, "article", "", "", 0 + ""));
    }

    /**
     * 插入已经下载的文章
     */
    public void insertFinishDownload(String service_id, String id, String release_time, String content_lists) {
        insert(new CompanionDB(id, service_id, "article", release_time, content_lists, 1 + ""));
    }

    /**
     * 删除订阅号的内容
     */
    public void deleteContent() {
        rawQuery("delete * from " + CompanionDBDao.TABLENAME + " where " + CompanionDBDao.Properties.is_download.columnName + " = '1'");
    }
}
