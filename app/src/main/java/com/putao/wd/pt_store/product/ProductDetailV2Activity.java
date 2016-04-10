package com.putao.wd.pt_store.product;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.StoreApi;
import com.putao.wd.model.ProductDetail;
import com.putao.wd.pt_store.shopping.ShoppingCarActivity;
import com.putao.wd.pt_store.shopping.ShoppingCarPopupWindow;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;
import com.sunnybear.library.view.viewpager.banner.ConvenientBanner;
import com.sunnybear.library.view.viewpager.banner.holder.CBViewHolderCreator;
import com.sunnybear.library.view.viewpager.banner.holder.Holder;
import com.sunnybear.library.view.viewpager.transformer.CardsTransformer;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 商品的详情不是简单的h5页面
 * Created by Administrator on 2016/4/9.
 */
public class ProductDetailV2Activity extends BasicFragmentActivity implements View.OnClickListener, TitleBar.OnTitleItemSelectedListener {
    public static final String PRODUCT_ID = "product_id";
    public static final String BUNDLE_PRODUCT_NUM = "bundle_product_num";
    @Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.cb_banner)
    ConvenientBanner<String> cb_banner;
    @Bind(R.id.tv_product_price)
    TextView tv_product_price;
    @Bind(R.id.tv_product_title)
    TextView tv_product_title;
    @Bind(R.id.tv_product_intro)
    TextView tv_product_intro;
    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar stickyHeaderLayout_sticky;
    @Bind(R.id.stickyHeaderLayout_scrollable)
    BasicWebView stickyHeaderLayout_scrollable;
    @Bind(R.id.shopping_add_car)
    TextView shopping_add_car;//加入购物车
    @Bind(R.id.linear_shopping_number)
    LinearLayout linear_shopping_number;
    @Bind(R.id.shopping_txt_number)
    TextView shopping_txt_number;//购物车数量
    @Bind(R.id.ll_share)
    LinearLayout ll_share;//分享
    @Bind(R.id.rl_productdetailV2)
    RelativeLayout rl_productdetailV2;

    private String product_id, product_num;
    private String title, subtitle, shareUrl, imageUrl;
    private SharePopupWindow mSharePopupWindow;//分享弹框
    private ShoppingCarPopupWindow mShoppingCarPopupWindow;//购物车弹窗

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detailv2;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        mSharePopupWindow = new SharePopupWindow(mContext);
        product_id = (String) args.getSerializable(PRODUCT_ID);
        product_num = args.getString(BUNDLE_PRODUCT_NUM);
        stickyHeaderLayout_scrollable.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loading.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loading.dismiss();
            }
        });
        getProductDetail(product_id);
        //分享弹框的点击事件
        addListener();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onStart() {
        super.onStart();
        getCartCount();
    }

    private void addListener() {
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.wechatWebShare(ProductDetailV2Activity.this, true, title, subtitle, imageUrl, shareUrl);
            }

            @Override
            public void onWechatFriend() {
                ShareTools.wechatWebShare(ProductDetailV2Activity.this, false, title, subtitle, imageUrl, shareUrl);
            }

            @Override
            public void onQQFriend() {
                ShareTools.OnQQZShare(ProductDetailV2Activity.this, true, title, subtitle, imageUrl, shareUrl);
            }

            @Override
            public void onQQZone() {
                ShareTools.OnQQZShare(ProductDetailV2Activity.this, false, title, subtitle, imageUrl, shareUrl);
            }

            public void onSinaWeibo() {
                ShareTools.OnWeiboShare(ProductDetailV2Activity.this, title, imageUrl, shareUrl);
            }

            @Override
            public void onCopyUrl() {
                ClipboardManager copy = (ClipboardManager) ProductDetailV2Activity.this
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(shareUrl);
                ToastUtils.showToastShort(ProductDetailV2Activity.this, "复制成功");
            }
        });
    }

    @OnClick({R.id.shopping_add_car, R.id.shopping_back, R.id.shopping_relative_car, R.id.ll_share})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_add_car://加入购物车
                if (null != mShoppingCarPopupWindow)
                    mShoppingCarPopupWindow.getProductSpec();
                else
                    ToastUtils.showToastShort(mContext, "加入失败");
                break;
            case R.id.shopping_back://返回
                YouMengHelper.onEvent(mContext, YouMengHelper.CreatorHome_conceit_detail_back);
                finish();
                break;
//            case R.id.shopping_car_buy://立即购买
//                Bundle bundle = new Bundle();
//                bundle.putString(PRODUCT_ID, storeProduct.getId());
//                bundle.putInt("product_count", 1);
//                startActivity(WriteOrderActivity.class, bundle);
//                break;
            case R.id.shopping_relative_car://点击进入购物车
                YouMengHelper.onEvent(mContext, YouMengHelper.CreatorHome_conceit_detail_cart);
                startActivity(ShoppingCarActivity.class);
                break;
            case R.id.ll_share://非精品的商品的分享
                if (null != mSharePopupWindow)
                    mSharePopupWindow.show(ll_share);
                break;
        }
    }

    /**
     * 商品详情
     */
    private void getProductDetail(final String product_id) {
        networkRequest(StoreApi.getProductDetailPager(product_id), new SimpleFastJsonCallback<ProductDetail>(ProductDetail.class, loading) {
            @Override
            public void onSuccess(String url, ProductDetail result) {
                loadHtml(product_id, "0");
                title = result.getTitle();
                subtitle = result.getSubtitle();
                shareUrl = result.getShare();
                imageUrl = result.getPictures().get(0);
                tv_product_title.setText(result.getTitle());
                tv_product_intro.setText(result.getSubtitle());
                tv_product_price.setText(result.getPrice());

                //广告列表
                if (result.getPictures() != null) {
                    cb_banner.setPages(new CBViewHolderCreator<ImageHolderView>() {
                        @Override
                        public ImageHolderView createHolder() {
                            return new ImageHolderView();
                        }
                    }, result.getPictures())
                            .setPageTransformer(new CardsTransformer());
                }
                mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext, product_id, result.getTitle(), result.getSubtitle());
                loading.dismiss();
            }
        });
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
        stickyHeaderLayout_scrollable.loadUrl(webUrl);
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
                    linear_shopping_number.setVisibility(View.VISIBLE);
                    shopping_txt_number.setText(count + "");
                } else {
                    linear_shopping_number.setVisibility(View.GONE);
                }
                loading.dismiss();
            }
        });
    }

    /**
     * 广告页适配器
     */
    static class ImageHolderView implements Holder<String> {
        private ImageDraweeView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageDraweeView(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DensityUtil.px2dp(context, 200));
            imageView.setLayoutParams(params);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageURL(data);
        }
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
        mShoppingCarPopupWindow.show(rl_productdetailV2);
    }
}
