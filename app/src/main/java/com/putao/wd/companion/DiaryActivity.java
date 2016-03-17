package com.putao.wd.companion;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.home.adapter.ExploreAdapter;
import com.putao.wd.model.DiaryApp;
import com.putao.wd.model.Diarys;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.ExploreProductPlot;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.store.product.ProductDetailActivity;
import com.putao.wd.video.YoukuVideoPlayerActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DiskFileCacheHelper;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.image.BitmapLoader;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import butterknife.Bind;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 陪伴日志
 * Created by zhanghao on 2016/1/14.
 */
public class DiaryActivity extends PTWDActivity {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ll_date_empty)
    RelativeLayout ll_date_empty;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rl_main)
    RelativeLayout rl_main;
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.iv_plot_icon)
    ImageDraweeView iv_plot_icon;
    @Bind(R.id.txt_companion_text)
    TextView txt_companion_text;
    @Bind(R.id.btn_companion_look_detail)
    Button btn_companion_look_detail;
    @Bind(R.id.companion_no_image)
    ImageDraweeView companion_no_image;

    public static String DIARY_APP = "diary_app";

    private ExploreAdapter adapter;
    private DiaryApp mDiaryApp;
    private SharePopupWindow mSharePopupWindow;
    private int mPage;
    private int mPostion;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_companion_diary;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        addListener();
        Bundle bundle = getIntent().getExtras();
        mPostion = bundle.getInt("position");
        mDiaryApp = (DiaryApp) args.getSerializable(DIARY_APP);
        navigation_bar.setMainTitle(mDiaryApp.getProduct_name());
        adapter = new ExploreAdapter(this, null);
        rv_content.setAdapter(adapter);
        mSharePopupWindow = new SharePopupWindow(mContext);
        getDiaryIndex();

    }

    private void addListener() {
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                networkRequest(ExploreApi.getDiaryData(String.valueOf(mDiaryApp.getProduct_id()), mPage),
                        new SimpleFastJsonCallback<Diarys>(Diarys.class, loading) {
                            @Override
                            public void onSuccess(String url, Diarys result) {
                                if (result != null && result.getData().size() > 0)
                                    adapter.addAll(result.getData());
                                rv_content.loadMoreComplete();
                                checkLoadMoreComplete(result.getCurrent_page(), result.getTotal_page());
                                loading.dismiss();
                            }
                        });
            }
        });
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDiaryIndex();
            }
        });
    }

    private void checkLoadMoreComplete(int currentPage, int totalPage) {
        if (currentPage == totalPage)
            rv_content.noMoreLoading();
        else mPage++;
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 获取陪伴成长日志
     */
    private void getDiaryIndex() {
        mPage = 1;
        networkRequest(ExploreApi.getDiaryData(String.valueOf(mDiaryApp.getProduct_id()), mPage),
                new SimpleFastJsonCallback<Diarys>(Diarys.class, loading) {
                    @Override
                    public void onSuccess(String url, Diarys result) {
                        if (result != null && result.getData().size() > 0) {
                            adapter.replaceAll(result.getData());
                            ll_date_empty.setVisibility(View.GONE);
                            ptl_refresh.setVisibility(View.VISIBLE);
                        } else {
                            ll_date_empty.setVisibility(View.VISIBLE);
                            OnCompanionDiary(mPostion);
                            ptl_refresh.setVisibility(View.GONE);
                            //各种详情的点击跳转
                            btn_companion_look_detail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable(ProductDetailActivity.BUNDLE_PRODUCT, );
//                                    startActivity(ProductDetailActivity.class, bundle);

                                }
                            });
                        }
                        ptl_refresh.refreshComplete();
                        checkLoadMoreComplete(result.getCurrent_page(), result.getTotal_page());
                        loading.dismiss();
                    }
                });
    }

    @Subcriber(tag = ExploreAdapter.EVENT_DISPLAY)
    public void eventDisplay(ExploreProduct exploreProduct) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DisPlayActivity.BUNDLE_DISPLAY_DETAILS, exploreProduct);
        startActivity(DisPlayActivity.class, bundle);
    }

    @Subcriber(tag = ExploreAdapter.EVENT_DISPLAY)
    public void eventDisplay(ExploreProductPlot plot) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PlotActivity.BUNDLE_DISPLAY_PLOT, plot);
        startActivity(PlotActivity.class, bundle);
    }

    @Subcriber(tag = ExploreAdapter.EVENT_PLAYER)
    public void eventPlayer(Bundle bundle) {
        startActivity(YoukuVideoPlayerActivity.class, bundle);
    }

    private Handler mHandler;
    private String video_id;
    private String content;
    private String img_url;

    @Subcriber(tag = ExploreAdapter.EVENT_DIARY_SHARE)
    public void eventShare(ExploreProductPlot exploreProductPlot) {
        content = exploreProductPlot.getContent();
        video_id = exploreProductPlot.getVideo_id();
        img_url = exploreProductPlot.getImg_url();
        if (TextUtils.isEmpty(img_url)) {
            iv_plot_icon.setVisibility(View.GONE);
            mSharePopupWindow.show(rl_main);
        } else {
            iv_plot_icon.setVisibility(View.VISIBLE);
            BitmapLoader.newInstance((Activity) mContext).load(exploreProductPlot.getImg_url(), new BitmapLoader.BitmapCallback() {
                @Override
                public void onResult(Bitmap bitmap) {
                    if (bitmap == null) {
                        ToastUtils.showToastLong(mContext, "分享失败");
                        return;
                    }
                    iv_plot_icon.setImageBitmap(bitmap);
                    mHandler.sendEmptyMessageDelayed(0x01, 200);
                }
            });
        }
        tv_content.setText(exploreProductPlot.getContent());

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mSharePopupWindow.show(rl_main);
            }
        };

        mSharePopupWindow.setOnShareClickListener(false, new OnShareClickListener() {
            @Override
            public void onWechat() {
                if (TextUtils.isEmpty(video_id)) {
                    ImageUtils.cutOutViewToImage(rl_main, GlobalApplication.shareImagePath,
                            new ImageUtils.OnImageSaveCallback() {
                                @Override
                                public void onImageSave(boolean isSuccess) {
                                    ShareTools.newInstance(Wechat.NAME)
                                            .setImagePath(GlobalApplication.shareImagePath)
                                            .execute(mContext);
                                    mSharePopupWindow.dismiss();
                                }
                            });
                } else {
                    ShareTools.wechatWebShare(mContext, true, null, content, img_url, "http://v.youku.com/v_show/id_" + video_id);
                }
            }

            @Override
            public void onWechatFriend() {
                if (TextUtils.isEmpty(video_id)) {
                    ImageUtils.cutOutViewToImage(rl_main, GlobalApplication.shareImagePath,
                            new ImageUtils.OnImageSaveCallback() {
                                @Override
                                public void onImageSave(boolean isSuccess) {
                                    ShareTools.newInstance(WechatMoments.NAME)
                                            .setImagePath(GlobalApplication.shareImagePath)
                                            .execute(mContext);
                                    mSharePopupWindow.dismiss();
                                }
                            });
                } else {
                    ShareTools.wechatWebShare(mContext, false, null, content, img_url, "http://v.youku.com/v_show/id_" + video_id);
                }
            }

            @Override
            public void onQQFriend() {
                if (TextUtils.isEmpty(video_id)) {
                    ImageUtils.cutOutViewToImage(rl_main, GlobalApplication.shareImagePath,
                            new ImageUtils.OnImageSaveCallback() {
                                @Override
                                public void onImageSave(boolean isSuccess) {
                                    ShareTools.newInstance(QQ.NAME)
                                            .setImagePath(GlobalApplication.shareImagePath)
                                            .execute(mContext);
                                    mSharePopupWindow.dismiss();
                                }
                            });
                } else {
                    ShareTools.OnQQZShare(mContext, true, null, content, img_url, "http://v.youku.com/v_show/id_" + video_id);
                }
            }

            @Override
            public void onQQZone() {

            }

            public void onSinaWeibo() {
                if (TextUtils.isEmpty(video_id)) {
                    ImageUtils.cutOutViewToImage(rl_main, GlobalApplication.shareImagePath,
                            new ImageUtils.OnImageSaveCallback() {
                                @Override
                                public void onImageSave(boolean isSuccess) {
                                    ShareTools.newInstance(SinaWeibo.NAME)
                                            .setImagePath(GlobalApplication.shareImagePath)
                                            .execute(mContext);
                                    mSharePopupWindow.dismiss();
                                }
                            });
                } else {
                    ShareTools.OnWeiboShare(mContext, content, "http://v.youku.com/v_show/id_" + video_id);
                }

            }
        });
    }

    public DiskFileCacheHelper getDiskCacheHelper() {
        return mDiskFileCacheHelper;
    }


    //跳转显示默认界面的判断
    public void OnCompanionDiary(int postion) {
        switch (postion) {
            case 0:
                txt_companion_text.setText(R.string.companion_taotaoright);
                companion_no_image.setBackgroundResource(R.drawable.img_p_dataempty_01);
                break;
            case 1:
                txt_companion_text.setText(R.string.companion_banderui);
                companion_no_image.setBackgroundResource(R.drawable.img_p_dataempty_03);
                break;
            case 2:
                txt_companion_text.setText(R.string.companion_mofang);
                companion_no_image.setBackgroundResource(R.drawable.img_p_dataempty_02);
                break;
            case 3:
                txt_companion_text.setText(R.string.companion_maisisi);
                companion_no_image.setBackgroundResource(R.drawable.img_p_dataempty_04);
                break;
            case 4:
                txt_companion_text.setText(R.string.companion_hello);
                companion_no_image.setBackgroundResource(R.drawable.img_p_dataempty_06);
                break;
            case 5:
                txt_companion_text.setText(R.string.companion_hani);
                companion_no_image.setBackgroundResource(R.drawable.img_p_dataempty_07);
                break;
            case 6:
                txt_companion_text.setText(R.string.companion_tutuWord);
                companion_no_image.setBackgroundResource(R.drawable.img_p_dataempty_05);
                break;
        }
    }
}
