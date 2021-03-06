package com.putao.wd.pt_store.product;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDRequestHelper;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.model.ProductDetail;
import com.putao.wd.model.ServiceProduct;
import com.putao.wd.model.StoreProduct;
import com.putao.wd.pt_companion.DiaryActivity;
import com.putao.wd.pt_me.message.RemindFragment;
import com.putao.wd.pt_store.shopping.ShoppingCarActivity;
import com.putao.wd.pt_store.shopping.ShoppingCarPopupWindow;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.BasicWebView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 商品的详情不是简单的h5页面
 * Created by Administrator on 2016/4/9.
 */
public class ProductDetailV2Activity extends BasicFragmentActivity implements View.OnClickListener {
    public static final String PRODUCT_ID = "product_id";
    public static final String BUNDLE_PRODUCT_NUM = "bundle_product_num";
    public static final String BUNDLE_PRODUCT_ICON = "product_icon";
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
    //商品没下架
    @Bind(R.id.rl_detail)
    RelativeLayout rl_detail;
    //商品已下架
    @Bind(R.id.ll_no_detail)
    LinearLayout ll_no_detail;
    @Bind(R.id.shopping_add_car)
    TextView shopping_add_car;//加入购物车
    @Bind(R.id.shopping_txt_number)
    TextView shopping_txt_number;//购物车数量
    @Bind(R.id.linear_shopping_number)
    LinearLayout linear_shopping_number;


    private SharePopupWindow mSharePopupWindow;//分享弹框
    private ShoppingCarPopupWindow mShoppingCarPopupWindow;//购物车弹窗
    StoreProduct storeProduct;

    private String product_id;//产品id
    private String product_num;//是否是从陪伴传送过来的数据
    private int status;//判断是否已下架
    private boolean isHave;
    private String title, subtitle, shareUrl, imageUrl, imageShare;

    private ProductDetail detail = null;

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
        product_num = args.getString(BUNDLE_PRODUCT_NUM);
        status = args.getInt(AccountConstants.Bundle.BUNDLE_STORE_STATUS);
        isHave = args.getBoolean(AccountConstants.Bundle.BUNDLE_STORE_ISHAVE, false);
        wv_content.setWebViewClient(new WebViewClient() {
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
        if (isHave) {
            rl_detail.setVisibility(View.GONE);
            ll_no_detail.setVisibility(View.VISIBLE);
            shopping_share.setVisibility(View.GONE);
            shopping_relative_car.setVisibility(View.GONE);
        } else {
            if (status == 0) {//已下架
                rl_detail.setVisibility(View.GONE);
                ll_no_detail.setVisibility(View.VISIBLE);
            } else if (status == 1) {//未下架
                rl_detail.setVisibility(View.VISIBLE);
                ll_no_detail.setVisibility(View.GONE);
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
//            if (product_num.equals("diary")) {
//                product_id = args.getString(DiaryActivity.BUNDLE_PRODUCT_ID);
//            } else {
                    product_id = args.getString(RemindFragment.BUNDLE_REMIND_PRODUCTID);
//            }
                    wv_content.loadUrl(PTWDRequestHelper.store()
                            .addParam("pid", product_id)
                            .joinURL(StoreApi.URL_PRODUCT_VIEW_V2));
                    getProduct(product_id);
                } else {
                    storeProduct = (StoreProduct) args.getSerializable(BUNDLE_PRODUCT);
                        wv_content.loadUrl(storeProduct.getMobile_url());
                        tv_product_price.setText(storeProduct.getPrice());
                        imageShare = ImageUtils.getImageSizeUrl(storeProduct.getImage(), ImageUtils.ImageSizeURL.SIZE_96x96);
                        mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext, storeProduct.getId(), storeProduct.getTitle(), storeProduct.getSubtitle());
                }
            }
        }
        //分享弹框的点击事件
        addListener();
    }

    private void addListener() {

        mSharePopupWindow.setOnShareClickListener(false, new OnShareClickListener() {

            @Override
            public void onWechat() {
                if (!product_num.equals("product_num")) {
                    ShareTools.wechatWebShare(ProductDetailV2Activity.this, true, title, subtitle, imageUrl, shareUrl);
                } else {//不是从陪伴页面传送过来的数据
                    ShareTools.wechatWebShare(ProductDetailV2Activity.this, true, storeProduct.getTitle(), storeProduct.getSubtitle(), imageShare, storeProduct.getMobile_url());
                }
            }

            @Override
            public void onWechatFriend() {
                if (!product_num.equals("product_num")) {
                    ShareTools.wechatWebShare(ProductDetailV2Activity.this, false, title, subtitle, imageUrl, shareUrl);
                } else {//不是从陪伴页面传送过来的数据
                    ShareTools.wechatWebShare(ProductDetailV2Activity.this, false, storeProduct.getTitle(), storeProduct.getSubtitle(), imageShare, storeProduct.getMobile_url());
                }
            }

            @Override
            public void onQQFriend() {
                if (!product_num.equals("product_num")) {
                    ShareTools.OnQQZShare(ProductDetailV2Activity.this, true, title, subtitle, imageUrl, shareUrl);
                } else {//不是从陪伴页面传送过来的数据
                    ShareTools.OnQQZShare(ProductDetailV2Activity.this, true, storeProduct.getTitle(), storeProduct.getSubtitle(), imageShare, storeProduct.getMobile_url());
                }
            }

            @Override
            public void onQQZone() {
                if (!product_num.equals("product_num")) {
                    ShareTools.OnQQZShare(ProductDetailV2Activity.this, false, title, subtitle, imageUrl, shareUrl);
                } else {//不是从陪伴页面传送过来的数据
                    ShareTools.OnQQZShare(ProductDetailV2Activity.this, false, storeProduct.getTitle(), storeProduct.getSubtitle(), imageShare, storeProduct.getMobile_url());
                }
            }

            public void onSinaWeibo() {
                if (!product_num.equals("product_num")) {
                    ShareTools.OnWeiboShare(ProductDetailV2Activity.this, title, imageUrl, shareUrl);
                } else {//不是从陪伴页面传送过来的数据
                    ShareTools.OnWeiboShare(ProductDetailV2Activity.this, storeProduct.getTitle(), imageShare, storeProduct.getMobile_url());
                }
            }

            @Override
            public void onCopyUrl() {
                ClipboardManager copy = (ClipboardManager) ProductDetailV2Activity.this
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                if (!product_num.equals("product_num")) {
                    copy.setText(shareUrl);
                } else {//不是从陪伴页面传送过来的数据
                    copy.setText(storeProduct.getMobile_url());
                }
                ToastUtils.showToastShort(ProductDetailV2Activity.this, "复制成功");
            }
        }, !product_num.equals("product_num") ? shareUrl : storeProduct.getMobile_url());
    }

    private void getProduct(final String product_id) {
        networkRequest(StoreApi.getProductDetail(product_id), new SimpleFastJsonCallback<ProductDetail>(ProductDetail.class, loading) {
            @Override
            public void onSuccess(String url, ProductDetail result) {
                if (null == result) {
                    finish();
                    return;
                }
                detail = result;
                title = result.getTitle();
                subtitle = result.getSubtitle();
                shareUrl = result.getShare();
                imageUrl = result.getPictures().get(0);
                imageUrl = ImageUtils.getImageSizeUrl(imageUrl, ImageUtils.ImageSizeURL.SIZE_96x96);
                tv_product_price.setText(result.getPrice());
                mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext, null == result.getId() ? product_id : result.getId(), result.getTitle(), result.getSubtitle());
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
                    linear_shopping_number.setVisibility(View.VISIBLE);
                    shopping_txt_number.setText(count + "");
                } else {
                    linear_shopping_number.setVisibility(View.GONE);
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

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.shopping_add_car, R.id.shopping_back, R.id.shopping_share, R.id.shopping_relative_car})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_share://分享
                YouMengHelper.onEvent(mContext, YouMengHelper.MallHome_detail_share);
                if (null != mSharePopupWindow)
                    mSharePopupWindow.show(shopping_share);
                break;
            case R.id.shopping_add_car://加入购物车
                YouMengHelper.onEvent(mContext, YouMengHelper.MallHome_detail_add);
                if (null != mShoppingCarPopupWindow)
                    mShoppingCarPopupWindow.getProductSpec();
                else
                    ToastUtils.showToastShort(mContext, "加入失败");
                break;
            case R.id.shopping_back://返回
                YouMengHelper.onEvent(mContext, YouMengHelper.MallHome_detail_back);
                finish();
                break;
//            case R.id.shopping_car_buy://立即购买
//                Bundle bundle = new Bundle();
//                bundle.putString(PRODUCT_ID, storeProduct.getId());
//                bundle.putInt("product_count", 1);
//                startActivity(WriteOrderActivity.class, bundle);
//                break;
            case R.id.shopping_relative_car://点击进入购物车
                YouMengHelper.onEvent(mContext, YouMengHelper.MallHome_detail_shopper);
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
