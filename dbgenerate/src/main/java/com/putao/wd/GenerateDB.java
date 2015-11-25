package com.putao.wd;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;

public class GenerateDB {
    private static final int DATABASE_VERSION = 1;
    private static final String PACKAGENAME_BEAN = "com.putao.wd.db.entity";
    private static final String PACKAGENAME_DAO = "com.putao.wd.db.dao";

    public static void main(String[] args) throws Exception{
        Schema schema=new Schema(DATABASE_VERSION,PACKAGENAME_BEAN);
        schema.setDefaultJavaPackageDao(PACKAGENAME_DAO);

        new DaoGenerator().generateAll(schema,args[0]);
    }
}
