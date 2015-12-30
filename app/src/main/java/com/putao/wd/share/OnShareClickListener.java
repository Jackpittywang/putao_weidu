package com.putao.wd.share;

/**
 * 点击分享框
 * Created by guchenkai on 2015/12/30.
 */
public abstract class OnShareClickListener {

    public abstract void onWechat();

    public abstract void onWechatFriend();

//        void onQQFriend();
//
//        void onQQZone();
//
//        void onSinaWeibo();
//
//        void onCopyUrl();

    public void onCancel() {

    }
}
