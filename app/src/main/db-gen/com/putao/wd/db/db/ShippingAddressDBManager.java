package com.putao.wd.db.db;

import com.putao.wd.GlobalApplication;
import com.putao.wd.db.DataBaseManager;
import com.putao.wd.db.dao.DaoMaster;
import com.putao.wd.db.entity.ShippingAddressDB;

import de.greenrobot.dao.AbstractDao;

/**
 * 收货人地址操作
 * Created by guchenkai on 2015/11/26.
 */
public class ShippingAddressDBManager extends DataBaseManager<ShippingAddressDB, Long> {
    private static ShippingAddressDBManager mInstance;

    public static ShippingAddressDBManager getInstance(GlobalApplication application) {
        if (mInstance == null)
            mInstance = new ShippingAddressDBManager(application.getDBHelper());
        return mInstance;
    }

    public ShippingAddressDBManager(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<ShippingAddressDB, Long> getAbstractDao() {
        return daoSession.getShippingAddressDBDao();
    }
}
