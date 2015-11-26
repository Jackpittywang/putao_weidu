package com.putao.wd;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GenerateDB {
    private static final int DATABASE_VERSION = 1;
    private static final String PACKAGENAME_BEAN = "com.putao.wd.db.entity";
    private static final String PACKAGENAME_DAO = "com.putao.wd.db.dao";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(DATABASE_VERSION, PACKAGENAME_BEAN);
        schema.setDefaultJavaPackageDao(PACKAGENAME_DAO);
        initAddress(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }

    private static void initAddress(Schema schema) {
        Entity address = schema.addEntity("ShippingAddressDB");
        address.setTableName("putao_wd_shipping_address");
        address.addIdProperty().autoincrement().index();//id
        address.addStringProperty("name").notNull();//收货人姓名
        address.addStringProperty("province").notNull();//省
        address.addStringProperty("city").notNull();//市
        address.addStringProperty("district").notNull();//区
        address.addStringProperty("street").notNull();//街道
        address.addStringProperty("mobile").notNull();//收货人电话
        address.addBooleanProperty("isDefault");//是否是默认地址
    }
}
