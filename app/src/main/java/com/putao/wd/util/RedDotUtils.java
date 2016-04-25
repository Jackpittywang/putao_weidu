package com.putao.wd.util;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.RedDotReceiver;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.db.CompanionDBManager;
import com.sunnybear.library.util.PreferenceUtils;

import java.util.List;

/**
 * Created by zhanghao on 2016/4/25.
 */
public class RedDotUtils {
    private static boolean[] mRedDots = new boolean[4];

    public static boolean showMessageCenterDot() {
        if (AccountHelper.isLogin()) {
            mRedDots = PreferenceUtils.getValue(RedDotReceiver.MESSAGECENTER + AccountHelper.getCurrentUid(), mRedDots);
            for (boolean redDot : mRedDots) {
                if (redDot) return true;
            }
        }
        return false;
    }

    public static void saveMessageCenterDot(String messagecenter) {
        mRedDots = PreferenceUtils.getValue(RedDotReceiver.MESSAGECENTER + AccountHelper.getCurrentUid(), mRedDots);
        switch (messagecenter) {
            case RedDotReceiver.MESSAGECENTER_REPLY:
                mRedDots[0] = true;
                break;
            case RedDotReceiver.MESSAGECENTER_PRAISE:
                mRedDots[1] = true;
                break;
            case RedDotReceiver.MESSAGECENTER_REMIND:
                mRedDots[2] = true;
                break;
            case RedDotReceiver.MESSAGECENTER_NOTICE:
                mRedDots[3] = true;
                break;
        }
        PreferenceUtils.save(RedDotReceiver.MESSAGECENTER + AccountHelper.getCurrentUid(), mRedDots);
    }

    public static boolean getCompanionDot(GlobalApplication app) {
        CompanionDBManager dataBaseManager = (CompanionDBManager) app.getDataBaseManager(CompanionDBManager.class);
        List<String> notDownloadIds = dataBaseManager.getNotDownloadIds();
        if (null == notDownloadIds || notDownloadIds.size() == 0) return false;
        return true;
    }

}
