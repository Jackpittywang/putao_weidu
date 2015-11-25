package com.putao.wd;

import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.util.AppUtils;
import com.sunnybear.library.util.SDCardUtil;

/**
 * 全局变量
 * Created by guchenkai on 2015/11/25.
 */
public class GlobalApplication extends BasicApplication {

    @Override
    protected String getBuglyKey() {
        return "900013684";
    }

    @Override
    protected boolean isDebug() {
        return AppUtils.getVersionName(getApplicationContext()).startsWith("D");
    }

    @Override
    public String getPackageName() {
        return "com.putao.wd";
    }

    @Override
    protected String getNetworkCacheDirectoryPath() {
        return SDCardUtil.getSDCardPath();
    }

    @Override
    protected int getNetworkCacheSize() {
        return 20 * 1024 * 1024;
    }

    @Override
    protected int getNetworkCacheMaxAgeTime() {
        return 0;
    }
}
