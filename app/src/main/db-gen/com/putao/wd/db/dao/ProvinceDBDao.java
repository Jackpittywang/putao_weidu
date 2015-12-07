package com.putao.wd.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.putao.wd.db.entity.ProvinceDB;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "putao_wd_province".
*/
public class ProvinceDBDao extends AbstractDao<ProvinceDB, String> {

    public static final String TABLENAME = "putao_wd_province";

    /**
     * Properties of entity ProvinceDB.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Province_id = new Property(0, String.class, "province_id", true, "PROVINCE_ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
    };


    public ProvinceDBDao(DaoConfig config) {
        super(config);
    }
    
    public ProvinceDBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"putao_wd_province\" (" + //
                "\"PROVINCE_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: province_id
                "\"NAME\" TEXT);"); // 1: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"putao_wd_province\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ProvinceDB entity) {
        stmt.clearBindings();
 
        String province_id = entity.getProvince_id();
        if (province_id != null) {
            stmt.bindString(1, province_id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ProvinceDB readEntity(Cursor cursor, int offset) {
        ProvinceDB entity = new ProvinceDB( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // province_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // name
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ProvinceDB entity, int offset) {
        entity.setProvince_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(ProvinceDB entity, long rowId) {
        return entity.getProvince_id();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(ProvinceDB entity) {
        if(entity != null) {
            return entity.getProvince_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
