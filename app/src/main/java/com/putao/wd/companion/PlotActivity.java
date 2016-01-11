package com.putao.wd.companion;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.model.ExploreProductPlot;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.image.BitmapLoader;

import butterknife.Bind;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 教育理念炫耀页面
 * Created by guchenkai on 2016/1/4.
 */
public class PlotActivity extends BasicFragmentActivity {
    public static final String BUNDLE_DISPLAY_PLOT = "plot";

    @Bind(R.id.ll_main)
    RelativeLayout ll_main;
    @Bind(R.id.iv_plot_icon)
    ImageView iv_plot_icon;
    @Bind(R.id.tv_content)
    TextView tv_content;

    private ExploreProductPlot plot;
    private SharePopupWindow mSharePopupWindow;

    private Handler mHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_explore_plot_dialog;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        mSharePopupWindow = new SharePopupWindow(mContext);
        plot = (ExploreProductPlot) args.getSerializable(BUNDLE_DISPLAY_PLOT);

        BitmapLoader.newInstance((Activity) mContext).load(plot.getImg_url(), new BitmapLoader.BitmapCallback() {
            @Override
            public void onResult(Bitmap bitmap) {
                if (bitmap == null) {
                    ToastUtils.showToastLong(mContext, "分享失败");
                    finish();
                    return;
                }
                iv_plot_icon.setImageBitmap(bitmap);
                mHandler.sendEmptyMessageDelayed(0x01, 200);
            }
        });
        tv_content.setText(plot.getContent());

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mSharePopupWindow.show(ll_main);
            }
        };
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ImageUtils.cutOutViewToImage(ll_main, GlobalApplication.shareImagePath,
                        new ImageUtils.OnImageSaveCallback() {
                            @Override
                            public void onImageSave(boolean isSuccess) {
                                ShareTools.newInstance(Wechat.NAME)
                                        .setImagePath(GlobalApplication.shareImagePath)
                                        .execute(mContext);
                                mSharePopupWindow.dismiss();
                                finish();
                            }
                        });
            }

            @Override
            public void onWechatFriend() {
                ImageUtils.cutOutViewToImage(ll_main, GlobalApplication.shareImagePath,
                        new ImageUtils.OnImageSaveCallback() {
                            @Override
                            public void onImageSave(boolean isSuccess) {
                                ShareTools.newInstance(WechatMoments.NAME)
                                        .setImagePath(GlobalApplication.shareImagePath)
                                        .execute(mContext);
                                mSharePopupWindow.dismiss();
                                finish();
                            }
                        });
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
