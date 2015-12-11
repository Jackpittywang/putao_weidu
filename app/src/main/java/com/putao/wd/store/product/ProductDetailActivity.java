package com.putao.wd.store.product;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ProductDetail;
import com.putao.wd.model.ProductNorms;
import com.putao.wd.share.SharePopupWindow;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;
import com.sunnybear.library.view.viewpager.BannerAdapter;
import com.sunnybear.library.view.viewpager.BannerLayout;

import org.jsoup.helper.DataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 商品详情
 * Created by guchenkai on 2015/11/30.
 */
public class ProductDetailActivity extends PTWDActivity implements View.OnClickListener {
    public static final String PRODUCT_ID = "product_id";

    private static final int[] resIds = new int[]{
            R.drawable.test_1, R.drawable.test_2, R.drawable.test_3, R.drawable.test_4, R.drawable.test_5, R.drawable.test_6, R.drawable.test_7
    };
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
    private boolean isStop;//广告栏是否被停止

    private SharePopupWindow mSharePopupWindow;//分享弹框
    private ShoppingCarPopupWindow mShoppingCarPopupWindow;//购物车弹窗
    private List<String> banners;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mSharePopupWindow = new SharePopupWindow(mContext);
        mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext);


        sticky_layout.canScrollView();
        wv_content.loadUrl("http://www.putao.com");
        //获取产品详情
        getProductDetail(args.getString(PRODUCT_ID));
        //广告位
        bl_banner.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position) {
                ImageView imageView = new ImageView(mContext);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(resIds[position]);
                return imageView;
            }

            @Override
            public int getCount() {
                return resIds.length;
            }
        });

        //获取规格参数
        //getProductSpce(args.getString(PRODUCT_ID));
    }

    /**
     * 商品详情
     */
    private void getProductDetail(String product_id){
        networkRequest(StoreApi.getProductDetail(product_id), new SimpleFastJsonCallback<ArrayList<ProductDetail>>(ProductDetail.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<ProductDetail> result) {
                Logger.d(result.toString());
                if(result.size()!=0){

                }
            }

        });
    }

    /**
     * 商品规格
     */
    private void getProductSpce(String product_id){
        networkRequest(StoreApi.getProductSpce(product_id), new SimpleFastJsonCallback<ProductNorms>(ProductNorms.class,loading) {
            @Override
            public void onSuccess(String url, ProductNorms result) {
                Logger.d(result.toString());
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
}