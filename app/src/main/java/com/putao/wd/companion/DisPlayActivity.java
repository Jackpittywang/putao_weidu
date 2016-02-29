package com.putao.wd.companion;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.home.adapter.ExploreDetailAdapter;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.ExploreProductDetail;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.image.BitmapLoader;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.List;

import butterknife.Bind;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 炫耀页面
 * Created by guchenkai on 2015/12/30.
 */
public class DisPlayActivity extends BasicFragmentActivity {
    public static final String BUNDLE_DISPLAY_DETAILS = "exploreProduct";

    @Bind(R.id.ll_main)
    LinearLayout ll_main;
    @Bind(R.id.root)
    ScrollView root;
    @Bind(R.id.iv_share_icon)
    ImageView iv_share_icon;
    @Bind(R.id.tv_summary)
    TextView tv_summary;
    @Bind(R.id.rv_display_data)
    BasicRecyclerView rv_display_data;
    private ExploreDetailAdapter adapter;
    private ExploreProduct exploreProduct;

    private SharePopupWindow mSharePopupWindow;

    private Handler mHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_display;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        Logger.d("炫耀页面开启");

        mSharePopupWindow = new SharePopupWindow(mContext);
        exploreProduct = (ExploreProduct) args.getSerializable(BUNDLE_DISPLAY_DETAILS);
        tv_summary.setText(exploreProduct.getBase_data_copies());

        List<ExploreProductDetail> details = exploreProduct.getDetails();
        if (details.size() % 2 != 0)
            details.add(new ExploreProductDetail());
        adapter = new ExploreDetailAdapter(mContext, details);
        rv_display_data.setAdapter(adapter);

        loading.show();
        BitmapLoader.newInstance((Activity) mContext).load(exploreProduct.getBase_img_url(),
                new BitmapLoader.BitmapCallback() {
                    @Override
                    public void onResult(Bitmap bitmap) {
                        loading.dismiss();
                        if (bitmap == null) {
                            ToastUtils.showToastLong(mContext, "分享失败");
                            finish();
                            return;
                        }
                        iv_share_icon.setImageBitmap(bitmap);
                        mHandler.sendEmptyMessageDelayed(0x01, 200);
                    }
                });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mSharePopupWindow.show(ll_main);
            }
        };
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ImageUtils.cutOutScrollViewToImage(root, GlobalApplication.shareImagePath,
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
                ImageUtils.cutOutScrollViewToImage(root, GlobalApplication.shareImagePath,
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
            public void onQQFriend() {

            }

            @Override
            public void onQQZone() {

            }

            @Override
            public void onSinaWeibo() {

            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[]{exploreProduct.getBase_img_url()};
    }
}
