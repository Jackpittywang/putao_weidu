package com.putao.wd.start.browse;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.PicClickResult;
import com.putao.wd.model.PicList;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 图片浏览
 * Created by guchenkai on 2015/12/24.
 */
public class PictrueBrowseActivity extends PTWDActivity {
    public static final String IMAGE_URL = "image_url";

    @Bind(R.id.vp_pics)
    ViewPager vp_pics;

    private int startNum;
    PicClickResult picClickResult;
    ArrayList<PicList> mPicList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browse;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        initData();
        vp_pics.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        initView();
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
                }
        );
        addListener();
    }

    private void initView() {
        setMainTitleColor(Color.WHITE);
        setMainTitle((startNum + 1) + "/" + mPicList.size());
        vp_pics.setOffscreenPageLimit(1);
        vp_pics.setCurrentItem(startNum);
    }

    private void initData() {
        picClickResult = (PicClickResult) args.getSerializable(IMAGE_URL);
        //开始的张数
        startNum = picClickResult.getClickIndex();
        mPicList = picClickResult.getPicList();
    }

    private void addListener() {
        vp_pics.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.getInstance().clearMemoryCache();
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String imageUrl = mPicList.get(position).getSrc();
            String isJPG = imageUrl.substring(imageUrl.length() - 3, imageUrl.length());
            Bundle bundle = new Bundle();
            bundle.putBoolean(PictrueBrowseFragment.IS_GIF, "gif".equals(isJPG));
            bundle.putString(PictrueBrowseFragment.PIC_URL, imageUrl);
            return Fragment.instantiate(mContext, PictrueBrowseFragment.class.getName(), bundle);
        }

        @Override
        public int getCount() {
            return mPicList.size();
        }
    }
}
