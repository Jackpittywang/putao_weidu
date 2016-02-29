package com.putao.wd.start.browse;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.image.ImageDraweeView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 图片浏览
 * Created by guchenkai on 2015/12/24.
 */
public class PictrueBrowseActivity extends PTWDActivity implements ViewPager.OnPageChangeListener {
    public static final String BUNDLE_CLICK_INDEX = "click_index";
    public static final String BUNDLE_PICTRUES = "pictrues";

    @Bind(R.id.vp_pics)
    ViewPager vp_pics;
    @Bind(R.id.ll_main)
    RelativeLayout ll_main;
    private SharePopupWindow mSharePopupWindow;//分享弹框

    private int mClickIndex;//当前点击的项目
    private ArrayList<String> mPicList;//图片数据源
    private int size;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browse;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mSharePopupWindow = new SharePopupWindow(mContext);
        mClickIndex = args.getInt(BUNDLE_CLICK_INDEX);
        mPicList = (ArrayList<String>) args.getSerializable(BUNDLE_PICTRUES);
        size = mPicList.size();

        setMainTitleColor(Color.WHITE);
        setMainTitle((mClickIndex + 1) + "/" + mPicList.size());

        vp_pics.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mPicList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageDraweeView imageView = new ImageDraweeView(mContext);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DensityUtil.px2dp(mContext, 300));
                imageView.setLayoutParams(params);
                imageView.setImageURL(mPicList.get(position));
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        vp_pics.setCurrentItem(mClickIndex);
        vp_pics.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.85f;
            private static final float MIN_ALPHA = 0.5f;

            @Override
            public void transformPage(View page, float position) {
                int pageWidth = page.getWidth();
                int pageHeight = page.getHeight();

                if (position < -1) {
                    page.setAlpha(0);
                } else if (position <= 1) {
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0)
                        page.setTranslationX(horzMargin - vertMargin / 2);
                    else
                        page.setTranslationX(-horzMargin + vertMargin / 2);
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                            / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
                } else {
                    page.setAlpha(0);
                }
            }
        });
        addListener();
    }

    private void addListener() {
        vp_pics.addOnPageChangeListener(this);
        /*mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {

            }

            @Override
            public void onWechatFriend() {

            }
        });*/
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onPageSelected(int position) {
        setMainTitle((position + 1) + "/" + mPicList.size());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRightAction() {
        mSharePopupWindow.show(ll_main);
    }
}
