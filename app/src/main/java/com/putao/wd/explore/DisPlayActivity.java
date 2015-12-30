package com.putao.wd.explore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.home.adapter.ExploreDetailAdapter;
import com.putao.wd.model.ExploreProductDetail;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.Logger;
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
    @Bind(R.id.ll_main)
    LinearLayout ll_main;
    @Bind(R.id.root)
    ScrollView root;
    @Bind(R.id.rv_display_data)
    BasicRecyclerView rv_display_data;
    private ExploreDetailAdapter adapter;
    private List<ExploreProductDetail> details;

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
        details = (List<ExploreProductDetail>) args.getSerializable("details");
        adapter = new ExploreDetailAdapter(mContext, details);
        rv_display_data.setAdapter(adapter);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mSharePopupWindow.show(ll_main);
            }
        };
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ImageUtils.cutOutViewToImage(root, GlobalApplication.shareImagePath,
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
                ImageUtils.cutOutViewToImage(root, GlobalApplication.shareImagePath,
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
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(0x01, 200);
    }

    @Override
    protected void onDestroy() {
        Logger.d("炫耀页面关闭");
        super.onDestroy();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
