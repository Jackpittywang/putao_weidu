package com.putao.wd.store.product;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ProductDetail;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.store.product.adapter.ProductBannerAdapter;
import com.putao.wd.store.shopping.ShoppingCarActivity;
import com.putao.wd.store.shopping.ShoppingCarPopupWindow;
import com.putao.wd.util.IndicatorHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.BadgeView;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;
import com.sunnybear.library.view.viewpager.BannerLayout;
import com.sunnybear.library.view.viewpager.BannerViewPager;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 商品详情
 * Created by guchenkai on 2015/11/30.
 */
public class ProductDetailActivity extends PTWDActivity implements View.OnClickListener, TitleBar.OnTitleItemSelectedListener {
    public static final String BUNDLE_PRODUCT_ID = "product_id";

    @Bind(R.id.ll_main)
    LinearLayout ll_main;
    @Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.stickyHeaderLayout_scrollable)
    BasicWebView wv_content;
    @Bind(R.id.tv_product_title)
    TextView tv_product_title;
    @Bind(R.id.tv_product_intro)
    TextView tv_product_intro;
    @Bind(R.id.tv_product_price)
    TextView tv_product_price;
    @Bind(R.id.bl_banner)
    BannerLayout bl_banner;
    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar stickyHeaderLayout_sticky;
    @Bind(R.id.ll_join_car)
    LinearLayout ll_join_car;//加入购物车

    private boolean isStop;//广告栏是否被停止

    private SharePopupWindow mSharePopupWindow;//分享弹框
    private ShoppingCarPopupWindow mShoppingCarPopupWindow;//购物车弹窗

    private String product_id;//产品id

    private String shareUrl;//分享的Url

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        product_id = args.getString(BUNDLE_PRODUCT_ID);

        sticky_layout.canScrollView();
        mSharePopupWindow = new SharePopupWindow(mContext);
        addListener();

        getProductDetail(product_id);
        getCartCount();

        stickyHeaderLayout_sticky.setOnTitleItemSelectedListener(this);
    }

    /**
     * 加载WebView
     *
     * @param product_id 产品id
     * @param type       种类
     */
    private void loadHtml(String product_id, String type) {
        String webUrl = "http://static.uzu.wang/weidu_event/uncdn/index.html?id=" + product_id + "&nav=" + type;
        wv_content.loadUrl(webUrl);
    }

    /**
     * 商品详情
     */
    private void getProductDetail(String product_id) {
        networkRequest(StoreApi.getProductDetail(product_id), new SimpleFastJsonCallback<ProductDetail>(ProductDetail.class, loading) {
            @Override
            public void onSuccess(String url, ProductDetail result) {
                loadHtml(result.getId(), "0");
                shareUrl = result.getShare();
                tv_product_title.setText(result.getTitle());
                tv_product_intro.setText(result.getSubtitle());
                tv_product_price.setText(result.getPrice());
                //广告列表

                if (result.getPictures() != null) {
                    bl_banner.setAdapter(new ProductBannerAdapter(mContext, result.getPictures(), new BannerViewPager.OnPagerClickListenr() {
                        @Override
                        public void onPagerClick(int position) {
                            ToastUtils.showToastLong(mContext, "点击第" + position + "项");
                        }
                    }));
                    bl_banner.setOffscreenPageLimit(result.getPictures().size());//缓存页面数
                }
                mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext, result.getId());
                loading.dismiss();
            }
        });
    }

    /**
     * 获得购物车数量
     */
    private void getCartCount() {
        networkRequest(StoreApi.getCartCount(), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                Logger.d(result);
                JSONObject object = JSON.parseObject(result);
                IndicatorHelper.getInstance(mContext, navigation_bar.getRightView())
                        .indicator(object.getInteger("qt"), BadgeView.POSITION_TOP_LEFT,
                                com.sunnybear.library.R.drawable.indicator_background, Color.WHITE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isStop)
            isStop = bl_banner.startAutoScroll();
    }

    @Override
    public void onStop() {
        super.onStop();
        isStop = bl_banner.stopAutoScroll();
    }

    private void addListener() {
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.newInstance(Wechat.NAME)
                        .setTitle("葡萄纬度")
                        .setText("葡萄纬度分享给您")
                        .setUrl(shareUrl).execute(mContext);
            }

            @Override
            public void onWechatFriend() {
                ShareTools.newInstance(WechatMoments.NAME)
                        .setTitle("葡萄纬度")
                        .setText("葡萄纬度分享给您")
                        .setUrl(shareUrl).execute(mContext);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.ll_share, R.id.ll_join_car})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_share://分享
                mSharePopupWindow.show(ll_main);
                break;
            case R.id.ll_join_car://加入购物车
                mShoppingCarPopupWindow.show(ll_main);
                break;
        }
    }

    @Override
    public void onRightAction() {
        startActivity(ShoppingCarActivity.class);
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ti_summary://概述
                loadHtml(product_id, "0");
                break;
            case R.id.ti_parameter://规格参数
//                wv_content.loadUrl("http://www.putao.com");
                break;
            case R.id.ti_pack://包装清单
//                wv_content.loadUrl("http://www.putao.com");
                break;
            case R.id.ti_service://售后
//                wv_content.loadUrl("http://www.putao.com");
                break;
        }
    }
}