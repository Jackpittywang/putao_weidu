package com.putao.wd;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GenerateDB {
    private static final int DATABASE_VERSION = 1;
    private static final String PACKAGE_NAME_BEAN = "com.putao.wd.db.entity";
    private static final String PACKAGE_NAME_DAO = "com.putao.wd.db.dao";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(DATABASE_VERSION, PACKAGE_NAME_BEAN);
        schema.setDefaultJavaPackageDao(PACKAGE_NAME_DAO);
        initProvince(schema);
        initCity(schema);
        initDistrict(schema);
        initAddress(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }

    /**
     * 省份
     */
    private static void initProvince(Schema schema) {
        Entity province = schema.addEntity("ProvinceDB");
        province.setTableName("putao_wd_province");
//        province.addIdProperty().autoincrement().index();//id
        province.addStringProperty("province_id").primaryKey();//省id
        province.addStringProperty("name");//省名字
    }

    /**
     * 城市
     */
    private static void initCity(Schema schema) {
        Entity city = schema.addEntity("CityDB");
        city.setTableName("putao_wd_city");
//        city.addIdProperty().autoincrement().index();//id
        city.addStringProperty("province_id");//省id
        city.addStringProperty("city_id").primaryKey();//市id
        city.addStringProperty("name");//市名字
    }

    /**
     * 城区
     */
    private static void initDistrict(Schema schema) {
        Entity district = schema.addEntity("DistrictDB");
        district.setTableName("putao_wd_district");
//        district.addIdProperty().autoincrement().index();//id
        district.addStringProperty("province_id");//省id
        district.addStringProperty("city_id");//市id
        district.addStringProperty("district_id").primaryKey();//区id
        district.addStringProperty("name");//区名字
    }

    /**
     * 地址
     */
    private static void initAddress(Schema schema) {
        Entity address = schema.addEntity("AddressDB");
        address.setTableName("putao_wd_address");
        address.addIdProperty().autoincrement().index();//id
        address.addStringProperty("name");//收货人姓名
        address.addStringProperty("province");//省份
        address.addStringProperty("province_id");//省份id
        address.addStringProperty("city");//城市
        address.addStringProperty("city_id");//城市id
        address.addStringProperty("district");//城区
        address.addStringProperty("district_id");//城区id
        address.addStringProperty("street");//街道
        address.addStringProperty("mobile");//收货人电话
        address.addBooleanProperty("isDefault");//是否是默认地址
    }
}
