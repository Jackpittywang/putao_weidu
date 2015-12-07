package com.putao.wd.db;

import com.putao.wd.db.dao.DaoMaster;
import com.putao.wd.db.entity.AddressDB;

import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * 收货人地址操作
 * Created by guchenkai on 2015/11/26.
 */
public class AddressDBManager extends DataBaseManager<AddressDB, Long> {
    private static AddressDBManager mInstance;

    public static AddressDBManager getInstance(DaoMaster.OpenHelper helper) {
        if (mInstance == null)
            mInstance = new AddressDBManager(helper);
        return mInstance;
    }

    public AddressDBManager(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<AddressDB, Long> getAbstractDao() {
        return daoSession.getAddressDBDao();
    }

    /**
     * 清除默认状态
     */
    public void clearDefaultState() {
        List<AddressDB> addresses = loadAll();
        for (AddressDB address : addresses) {
            address.setIsDefault(false);
            update(address);
        }
    }
}
