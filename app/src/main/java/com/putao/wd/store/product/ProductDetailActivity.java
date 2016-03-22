package com.putao.wd.store.product;

import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.PTWDRequestHelper;
import com.putao.wd.me.message.RemindFragment;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.model.Product;
import com.putao.wd.model.ProductDetail;
import com.putao.wd.model.Service;
import com.putao.wd.model.ServiceProduct;
import com.putao.wd.model.StoreProduct;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.store.order.WriteOrderActivity;
import com.putao.wd.store.shopping.ShoppingCarActivity;
import com.putao.wd.store.shopping.ShoppingCarPopupWindow;
import com.putao.wd.user.LoginActivity;
import com.squareup.okhttp.FormEncodingBuilder;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.model.http.request.RequestMethod;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.BasicWebView;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 商品详情
 * Created by guchenkai on 2015/11/30.
 */
public class ProductDetailActivity extends BasicFragmentActivity implements View.OnClickListener {
    public static final String PRODUCT_ID = "product_id";
    /* public static final String BUNDLE_PRODUCT_ICON = "product_icon";*/
    public static final String BUNDLE_PRODUCT = "bundle_product";
    public static final String BUNDLE_IS_DETAIL = "bundle_is_detail";
    public static final String BUNDLE_IS_SERVICE = "bundle_is_service";
    public static final String BUNDLE_IS_REMIND = "bundle_is_remind";
    public boolean is_detail = false;
    public boolean is_service = false;
    public boolean is_remind = false;

    @Bind(R.id.rl_main)
    RelativeLayout rl_main;
    @Bind(R.id.wv_content)
    BasicWebView wv_content;
    @Bind(R.id.tv_product_price)
    TextView tv_product_price;
    @Bind(R.id.shopping_back)
    ImageView shopping_back;
    @Bind(R.id.shopping_share)
    ImageView shopping_share;
    @Bind(R.id.shopping_relative_car)
    RelativeLayout shopping_relative_car;
    @Bind(R.id.shopping_add_car)
    TextView shopping_add_car;//加入购物车
    @Bind(R.id.shopping_car_buy)
    TextView shopping_car_buy;//立即购买
    @Bind(R.id.shopping_txt_number)
    TextView shopping_txt_number;//立即购买

    /*@Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.cb_banner)
    ConvenientBanner<String> cb_banner;
    @Bind(R.id.tv_product_title)
    TextView tv_product_title;
    @Bind(R.id.tv_product_intro)
    TextView tv_product_intro;
    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar stickyHeaderLayout_sticky;*/

    private SharePopupWindow mSharePopupWindow;//分享弹框
    private ShoppingCarPopupWindow mShoppingCarPopupWindow;//购物车弹窗
    StoreProduct storeProduct;

    private String product_id;//产品id

    private ProductDetail detail = null;
    private String imageUrl;//商品图片

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        mSharePopupWindow = new SharePopupWindow(mContext);
        is_detail = args.getBoolean(BUNDLE_IS_DETAIL);
        is_service = args.getBoolean(BUNDLE_IS_SERVICE);
        is_remind = args.getBoolean(BUNDLE_IS_REMIND);
        if (is_detail) {
            if (is_service) {
                ServiceProduct storeProduct = (ServiceProduct) args.getSerializable(BUNDLE_PRODUCT);
                wv_content.loadUrl(PTWDRequestHelper.store()
                        .addParam("pid", storeProduct.getProduct_id())
                        .joinURL(StoreApi.URL_PRODUCT_VIEW_V2));
                tv_product_price.setText(storeProduct.getPrice());
                getProduct(storeProduct.getProduct_id());
            } else {
                OrderProduct storeProduct = (OrderProduct) args.getSerializable(BUNDLE_PRODUCT);
                wv_content.loadUrl(PTWDRequestHelper.store()
                        .addParam("pid", storeProduct.getProduct_id())
                        .joinURL(StoreApi.URL_PRODUCT_VIEW_V2));
                tv_product_price.setText(storeProduct.getPrice());
                getProduct(storeProduct.getProduct_id());
            }
        } else if (is_remind) {
            String product_id = args.getString(RemindFragment.BUNDLE_REMIND_PRODUCTID);
            wv_content.loadUrl(PTWDRequestHelper.store()
                    .addParam("pid", product_id)
                    .joinURL(StoreApi.URL_PRODUCT_VIEW_V2));
            getProduct(product_id);
        } else {
            storeProduct = (StoreProduct) args.getSerializable(BUNDLE_PRODUCT);
            wv_content.loadUrl(storeProduct.getMobile_url());
            tv_product_price.setText(storeProduct.getPrice());
            mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext, storeProduct.getId(), storeProduct.getTitle(), storeProduct.getSubtitle());
        }
        addListener();

//         imageUrl = args.getString(BUNDLE_PRODUCT_ICON);
//        product_id = args.getString(BUNDLE_PRODUCT_ID);
//        mSharePopupWindow = new SharePopupWindow(mContext);
//        sticky_layout.canScrollView();
//        addListener();
//        getProductDetail(product_id);
//        stickyHeaderLayout_sticky.setOnTitleItemSelectedListener(this);
    }

    private void addListener() {
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.wechatWebShare(ProductDetailActivity.this, true, storeProduct.getTitle(), storeProduct.getSubtitle(), storeProduct.getImage(), storeProduct.getMobile_url());
            }

            @Override
            public void onWechatFriend() {
                ShareTools.wechatWebShare(ProductDetailActivity.this, false, storeProduct.getTitle(), storeProduct.getSubtitle(), storeProduct.getImage(), storeProduct.getMobile_url());
            }

            @Override
            public void onQQFriend() {
                ShareTools.OnQQZShare(ProductDetailActivity.this, true, storeProduct.getTitle(), storeProduct.getSubtitle(), storeProduct.getImage(), storeProduct.getMobile_url());
            }

            @Override
            public void onQQZone() {
                ShareTools.OnQQZShare(ProductDetailActivity.this, false, storeProduct.getTitle(), storeProduct.getSubtitle(), storeProduct.getImage(), storeProduct.getMobile_url());
            }

            public void onSinaWeibo() {
                ShareTools.OnWeiboShare(ProductDetailActivity.this, storeProduct.getTitle(), storeProduct.getMobile_url());
            }

            @Override
            public void onCopyUrl() {
                ClipboardManager copy = (ClipboardManager) ProductDetailActivity.this
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(storeProduct.getMobile_url());
                ToastUtils.showToastShort(ProductDetailActivity.this, "复制成功");
            }
        });
    }

    private void getProduct(String product_id) {
        networkRequest(StoreApi.getProductDetail(product_id), new SimpleFastJsonCallback<ProductDetail>(ProductDetail.class, loading) {
            @Override
            public void onSuccess(String url, ProductDetail result) {
                if (null == result) {
                    finish();
                    return;
                }
                detail = result;
                tv_product_price.setText(result.getPrice());
                mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext, result.getId(), result.getTitle(), result.getSubtitle());
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
  /*  private void loadHtml(String product_id, String type) {
        String base_url = GlobalApplication.isDebug ? "http://static.uzu.wang/weidu_event/" : "http://static.putaocdn.com/weidu/";
        String webUrl = base_url + "uncdn/index.html?id=" + product_id + "&nav=" + type;
        wv_content.loadUrl(args.getString(BUNDLE_PRODUCT_ICON));
    }*/

    /**
     * 商品详情
     *//*
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
                    cb_banner.setPages(new CBViewHolderCreator<ImageHolderView>() {
                        @Override
                        public ImageHolderView createHolder() {
                            return new ImageHolderView();
                        }
                    }, result.getPictures())
                            .setPageTransformer(new CardsTransformer());
                }
                mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext, result.getId());
                loading.dismiss();
            }
        });
    }

    *//**
     * 广告页适配器
     *//*
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
    }*/

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
                    shopping_txt_number.setVisibility(View.VISIBLE);
                    shopping_txt_number.setText(count + "");
                } else {
                    shopping_txt_number.setVisibility(View.GONE);
                }
                loading.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getCartCount();
    }

  /*  @Override
    protected void onDestroy() {
        super.onDestroy();
//        wv_content.onPause();
        wv_content.loadUrl("");
    }*/
   /* private void addListener() {
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.wechatWebShare(mContext, true, detail.getTitle(), detail.getSubtitle(), imageUrl, detail.getShare());
            }

            @Override
            public void onWechatFriend() {
                ShareTools.wechatWebShare(mContext, false, detail.getTitle(), detail.getSubtitle(), imageUrl, detail.getShare());
            }
        });
    }*/

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.shopping_add_car, R.id.shopping_back, R.id.shopping_car_buy, R.id.shopping_share, R.id.shopping_relative_car})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_share://分享
                mSharePopupWindow.show(shopping_share);
                break;
            case R.id.shopping_add_car://加入购物车
                mShoppingCarPopupWindow.getProductSpec();
                break;
            case R.id.shopping_back://返回
                MobclickAgent.onEvent(mContext, YouMengHelper.CreatorHome_conceit_detail_back);
                finish();
                break;
            case R.id.shopping_car_buy://立即购买
                Bundle bundle = new Bundle();
                bundle.putString(PRODUCT_ID, storeProduct.getId());
                bundle.putInt("product_count", 1);
                startActivity(WriteOrderActivity.class, bundle);
                break;
            case R.id.shopping_relative_car://点击进入购物车
                MobclickAgent.onEvent(mContext, YouMengHelper.CreatorHome_conceit_detail_cart);
                startActivity(ShoppingCarActivity.class);
                break;

        }
    }


    public void onRightAction() {
        if (!AccountHelper.isLogin()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ShoppingCarActivity.class);
            startActivity(LoginActivity.class, bundle);
            return;
        }
        startActivity(ShoppingCarActivity.class);
    }

    /* @Override
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
     }*/
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
        mShoppingCarPopupWindow.show(rl_main);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (wv_content != null) {
                wv_content.getClass().getMethod("onPause").invoke(wv_content, (Object[]) null);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (wv_content != null) {
                wv_content.getClass().getMethod("onResume").invoke(wv_content, (Object[]) null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        wv_content.destroy();
        super.onDestroy();
    }
}