package com.putao.wd.util;

import com.putao.wd.RedDotReceiver;
import com.putao.wd.account.AccountHelper;
import com.sunnybear.library.util.PreferenceUtils;

/**
 * Created by zhanghao on 2016/4/25.
 */
public class RedDotUtils {
    private static boolean[] mRedDots = new boolean[4];

    public static boolean showMessageCenterDot() {
        mRedDots = PreferenceUtils.getValue(RedDotReceiver.MESSAGECENTER + AccountHelper.getCurrentUid(), mRedDots);
        for (boolean redDot : mRedDots) {
            if (redDot) return true;
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


}
