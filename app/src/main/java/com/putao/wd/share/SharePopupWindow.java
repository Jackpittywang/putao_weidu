package com.putao.wd.share;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView tv_collection;

    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        setOnShareClickListener(true, onShareClickListener);
    }

    public void setOnShareClickListener(boolean isCopy, OnShareClickListener onShareClickListener) {
        this.isCopy = isCopy;
        mOnShareClickListener = onShareClickListener;
        LinearLayout ll_second = (LinearLayout) mRootView.findViewById(R.id.ll_second);
        TextView tv_qq_zone = (TextView) mRootView.findViewById(R.id.tv_qq_zone);
        ImageView iv_qq_zone = (ImageView) mRootView.findViewById(R.id.iv_qq_zone);
        tv_collection = (TextView) mRootView.findViewById(R.id.tv_collection);
        if (!isCopy) {
            //复制
            ll_second.setVisibility(View.GONE);
            //QQ空间
            tv_qq_zone.setText("新浪微博");
            iv_qq_zone.setImageResource(R.drawable.icon_40_05);
        } else {
            //复制
            ll_second.setVisibility(View.VISIBLE);
            //QQ空间
            tv_qq_zone.setText("QQ空间");
            iv_qq_zone.setImageResource(R.drawable.icon_40_04);
        }
    }

    public SharePopupWindow(Context context) {
        super(context);
        setAnimationStyle(R.style.bottom_anim_style);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_share;
    }

    @OnClick({
            R.id.ll_wechat,
            R.id.ll_wechat_friend_circle,
            R.id.ll_collection,
            R.id.ll_qq_friend,
            R.id.ll_qq_zone,
            R.id.ll_sina_weibo,
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
                case R.id.ll_collection://收藏
                    mOnShareClickListener.onCollection(tv_collection);
                    break;
                case R.id.ll_qq_friend://QQ好友
                    mOnShareClickListener.onQQFriend();
                    break;
                case R.id.ll_qq_zone://QQ空间
                    if (isCopy) {
                        mOnShareClickListener.onQQZone();
                    } else {
                        mOnShareClickListener.onSinaWeibo();
                    }
                    break;
                case R.id.ll_sina_weibo://新浪微博
                    mOnShareClickListener.onSinaWeibo();
                    break;
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