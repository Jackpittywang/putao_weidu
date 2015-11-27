package com.putao.wd.share.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.putao.wd.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分享弹出框
 * Created by guchenkai on 2015/11/27.
 */
public class SharePopupWindow extends PopupWindow implements View.OnClickListener {
    @Bind(R.id.popup_layout)
    RelativeLayout popup_layout;

    private OnShareListener mOnShareListener;

    public void setOnShareListener(OnShareListener onShareListener) {
        mOnShareListener = onShareListener;
    }

    public SharePopupWindow(Context context) {
        super(context);
        final View mRootView = LayoutInflater.from(context).inflate(R.layout.popup_share, null);
        setContentView(mRootView);
        ButterKnife.bind(this, mRootView);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.anim.in_from_down);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);
        mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = popup_layout.getHeight();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP && y < height)
                    dismiss();
                return true;
            }
        });
    }

    /**
     * 设置layout在PopupWindow中显示的位置
     *
     * @param view
     */
    public void show(View view) {
        showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @OnClick({
            R.id.ll_wechat,
            R.id.ll_wechat_friend_circle,
            R.id.ll_qq_friend,
            R.id.ll_qq_zone,
            R.id.ll_sina_weibo,
            R.id.ll_copy_url,
            R.id.tv_cancel
    })
    @Override
    public void onClick(View v) {
        if (mOnShareListener != null)
            switch (v.getId()) {
                case R.id.ll_wechat://微信
                    mOnShareListener.onWechatShare();
                    break;
                case R.id.ll_wechat_friend_circle://微信朋友圈
                    mOnShareListener.onWechatFriendCircleShare();
                    break;
                case R.id.ll_qq_friend://QQ好友
                    mOnShareListener.onQQFriendShare();
                    break;
                case R.id.ll_qq_zone://QQ空间
                    mOnShareListener.onQQZoneShare();
                    break;
                case R.id.ll_sina_weibo://新浪微博
                    mOnShareListener.onSinaWeiBoShare();
                    break;
                case R.id.ll_copy_url://复制链接
                    mOnShareListener.onCopyUrlShare();
                    break;
                case R.id.tv_cancel://取消
                    dismiss();
                    break;
            }
    }

    /**
     * 点击分享按钮监听
     */
    public interface OnShareListener {

        /**
         * 微信分享
         */
        void onWechatShare();

        /**
         * 微信朋友圈分享
         */
        void onWechatFriendCircleShare();

        /**
         * QQ好友分享
         */
        void onQQFriendShare();

        /**
         * QQ空间分享
         */
        void onQQZoneShare();

        /**
         * 新浪微博分享
         */
        void onSinaWeiBoShare();

        /**
         * 复制链接
         */
        void onCopyUrlShare();
    }
}
