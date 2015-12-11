package com.putao.wd.share;

import android.content.Context;

import com.putao.wd.GlobalApplication;

import java.io.File;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

/**
 * 分享工具
 * Created by guchenkai on 2015/11/27.
 */
public class ShareTools {
    private static OnekeyShare mOnekeyShare = new OnekeyShare();

    public static void showShare(Context context, String platfom, final String shareText) {
        mOnekeyShare.setTitle("分享文字");
        mOnekeyShare.setText("这是一段分享文字");
        mOnekeyShare.setDialogMode();
        mOnekeyShare.setImagePath(GlobalApplication.sdCardPath + File.separator + "screenshot.jpg");
        mOnekeyShare.disableSSOWhenAuthorize();
        mOnekeyShare.setPlatform(platfom);
        mOnekeyShare.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                paramsToShare.setText(shareText);
//                paramsToShare.setImageUrl(GlobalApplication.sdCardPath + File.separator + "screenshot.jpg");
            }
        });
        mOnekeyShare.show(context);
    }
}
