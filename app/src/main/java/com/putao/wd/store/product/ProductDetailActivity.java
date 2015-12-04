package com.putao.wd.store.product;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.share.SharePopupWindow;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 商品详情
 * Created by guchenkai on 2015/11/30.
 */
public class ProductDetailActivity extends PTWDActivity implements View.OnClickListener {
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

    private SharePopupWindow mSharePopupWindow;//分享弹框
    private ShoppingCarPopupWindow mShoppingCarPopupWindow;//购物车弹窗

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        mSharePopupWindow = new SharePopupWindow(mContext);
        mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext);
        sticky_layout.canScrollView();
        wv_content.loadUrl("http://www.putao.com");
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