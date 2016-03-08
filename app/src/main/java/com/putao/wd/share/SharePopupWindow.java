package com.putao.wd.share;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicPopupWindow;

import butterknife.OnClick;

/**
 * 分享弹出框
 * Created by guchenkai on 2015/11/27.
 */
public class SharePopupWindow extends BasicPopupWindow implements View.OnClickListener {
    private boolean isCopy = true;
    private OnShareClickListener mOnShareClickListener;

    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        mOnShareClickListener = onShareClickListener;
    }

    public void setOnShareClickListener(boolean isCopy, OnShareClickListener onShareClickListener) {
        this.isCopy = isCopy;
        mOnShareClickListener = onShareClickListener;
        if (!isCopy) {
            LinearLayout ll_second = (LinearLayout) mRootView.findViewById(R.id.ll_copy_url);
            ll_second.setVisibility(View.GONE);
        }
    }

    public SharePopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_share;
    }

    @OnClick({
            R.id.ll_wechat,
            R.id.ll_wechat_friend_circle,
           /* R.id.ll_qq_friend,
            R.id.ll_qq_zone,*/
            /*R.id.ll_sina_weibo,*/
            R.id.ll_copy_url,
            R.id.tv_cancel
    })
    @Override
    public void onClick(View v) {
        if (mOnShareClickListener != null)
            switch (v.getId()) {
                case R.id.ll_wechat://微信
                    mOnShareClickListener.onWechat();
                    break;
                case R.id.ll_wechat_friend_circle://微信朋友圈
                    mOnShareClickListener.onWechatFriend();
                    break;
                /*case R.id.ll_qq_friend://QQ好友
                    mOnShareClickListener.onQQFriend();
                    break;
                case R.id.ll_qq_zone://QQ空间
                    mOnShareClickListener.onQQZone();
                    break;*/
//                case R.id.ll_sina_weibo://新浪微博
//                    mOnShareClickListener.onSinaWeibo();
//                    break;
                case R.id.ll_copy_url://复制链接
                    mOnShareClickListener.onCopyUrl();
                    break;
            }
        dismiss();
    }

    @Override
    public void dismiss() {
        if (mOnShareClickListener != null)
            mOnShareClickListener.onCancel();
        super.dismiss();
    }
}