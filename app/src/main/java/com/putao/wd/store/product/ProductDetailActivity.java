package com.putao.wd.store.product;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ProductDetail;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.store.product.adapter.ProductBannerAdapter;
import com.putao.wd.store.shopping.ShoppingCarActivity;
import com.putao.wd.store.shopping.ShoppingCarPopupWindow;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;
import com.sunnybear.library.view.viewpager.BannerLayout;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 商品详情
 * Created by guchenkai on 2015/11/30.
 */
public class ProductDetailActivity extends PTWDActivity implements View.OnClickListener, TitleBar.OnTitleItemSelectedListener {
    public static final String BUNDLE_PRODUCT_ID = "product_id";
    public static final String BUNDLE_PRODUCT_IMAGE = "product_imag";

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

    private ProductDetail detail = null;
    private String imageUrl;//商品图片

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
//        product_id = args.getString(BUNDLE_PRODUCT_ID);
//        imageUrl = args.getString(BUNDLE_PRODUCT_IMAGE);

        sticky_layout.canScrollView();
        mSharePopupWindow = new SharePopupWindow(mContext);
        addListener();

//        getProductDetail(product_id);

        stickyHeaderLayout_sticky.setOnTitleItemSelectedListener(this);
    }

    /**
     * 加载WebView
     *
     * @param product_id 产品id
     * @param type       种类
     */
    private void loadHtml(String product_id, String type) {
        String base_url = GlobalApplication.isDebug ? "http://static.uzu.wang/weidu_event/" : "http://static.putaocdn.com/weidu/";
        String webUrl = base_url + "uncdn/index.html?id=" + product_id + "&nav=" + type;
        wv_content.loadUrl(webUrl);
    }

    /**
     * 商品详情
     */
    private void getProductDetail(String product_id) {
        networkRequest(StoreApi.getProductDetail(product_id), new SimpleFastJsonCallback<ProductDetail>(ProductDetail.class, loading) {
            @Override
            public void onSuccess(String url, ProductDetail result) {
                detail = result;
                loadHtml(result.getId(), "0");
                tv_product_title.setText(result.getTitle());
                tv_product_intro.setText(result.getSubtitle());
                tv_product_price.setText(result.getPrice());

                //广告列表
                if (result.getPictures() != null) {
                    bl_banner.setAdapter(new ProductBannerAdapter(mContext, result.getPictures(), null));
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
                int count = object.getInteger("qt");
                if (count != 0) {
                    navigation_bar.hideRrightTitleIcon(false);
                    navigation_bar.setRightTitleIcon(count + "");
                } else {
                    navigation_bar.hideRrightTitleIcon(true);
                }
                loading.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isStop)
            isStop = bl_banner.startAutoScroll();
        getCartCount();
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
//                ShareTools.newInstance(Wechat.NAME)
//                        .setTitle("葡萄纬度")
//                        .setText("葡萄纬度分享给您")
//                        .setUrl(shareUrl).execute(mContext);
                ShareTools.wechatWebShare(mContext, true, detail.getTitle(), detail.getSubtitle(), imageUrl, detail.getShare());
            }

            @Override
            public void onWechatFriend() {
//                ShareTools.newInstance(WechatMoments.NAME)
//                        .setTitle("葡萄纬度")
//                        .setText("葡萄纬度分享给您")
//                        .setUrl(shareUrl).execute(mContext);
                ShareTools.wechatWebShare(mContext, false, detail.getTitle(), detail.getSubtitle(), imageUrl, detail.getShare());
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
                mShoppingCarPopupWindow.getProductSpec();
                break;
        }
    }

    @Override
    public void onRightAction() {
        if (!AccountHelper.isLogin()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ShoppingCarActivity.class);
            startActivity(LoginActivity.class, bundle);
            return;
        }
        startActivity(ShoppingCarActivity.class);
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ti_summary://概述
                loadHtml(product_id, "0");
                break;
            case R.id.ti_parameter://规格参数
                loadHtml(product_id, "1");
                break;
            case R.id.ti_pack://包装清单
                loadHtml(product_id, "2");
                break;
            case R.id.ti_service://售后
                loadHtml(product_id, "3");
                break;
        }
    }

    @Subcriber(tag = ShoppingCarPopupWindow.EVENT_REFRESH_TITLE_COUNT)
    public void eventRefreshCount(String tag) {
        getCartCount();
    }

    @Subcriber(tag = ShoppingCarPopupWindow.EVENT_TO_LOGIN)
    public void eventToLogin(String tag) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ShoppingCarActivity.class);
        startActivity(LoginActivity.class, bundle);
    }

    @Subcriber(tag = ShoppingCarPopupWindow.EVENT_GET_PRODUCT_SPEC)
    public void eventGetProductSpec(String tag) {
        mShoppingCarPopupWindow.show(ll_main);
    }

}