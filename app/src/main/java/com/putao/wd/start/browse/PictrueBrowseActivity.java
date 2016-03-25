package com.putao.wd.start.browse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MyViewPager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.PicClickResult;
import com.putao.wd.model.PicList;
import com.putao.wd.share.SharePopupWindow;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.image.ImageDraweeView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import uk.co.senab.photoview.IPhotoView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片浏览
 * Created by guchenkai on 2015/12/24.
 */
public class PictrueBrowseActivity extends PTWDActivity implements ViewPager.OnPageChangeListener {
    //    public static final String BUNDLE_CLICK_INDEX = "click_index";
//    public static final String BUNDLE_PICTRUES = "pictrues";
    public static final String IMAGE_URL = "image_url";

    @Bind(R.id.vp_pics)
    ViewPager vp_pics;
    @Bind(R.id.ll_main)
    RelativeLayout ll_main;
    //    @Bind(R.id.loading)
//    ProgressBar dialog;
    @Bind(R.id.relative_pager)
    RelativeLayout relative_pager;
    private SharePopupWindow mSharePopupWindow;//分享弹框

    private int startNum;
    PicClickResult picClickResult;
    ArrayList<PicList> mPicList;
//    private ImageView mImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browse;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mSharePopupWindow = new SharePopupWindow(mContext);
//        mClickIndex = args.getInt(BUNDLE_CLICK_INDEX);
//        mPicList = (ArrayList<String>) args.getSerializable(BUNDLE_PICTRUES);
        picClickResult = (PicClickResult) args.getSerializable(IMAGE_URL);

        //开始的张数
        startNum = picClickResult.getClickIndex();
        mPicList = picClickResult.getPicList();

        setMainTitleColor(Color.WHITE);
        setMainTitle((startNum + 1) + "/" + mPicList.size());

        vp_pics.setOffscreenPageLimit(3);
        vp_pics.setAdapter(new PagerAdapter() {
                               private LayoutInflater inflater = getLayoutInflater();


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
                                   View contentView = inflater.inflate(R.layout.image_pager_detail_fragment, container, false);
                                   PhotoView mPhotoView = (PhotoView) contentView.findViewById(R.id.image);
                                   final ProgressBar dialog = (ProgressBar) contentView.findViewById(R.id.loading);
                                   ImageView mImageView = null;
                                   final ViewGroup.LayoutParams params;
                                   String imageUrl = mPicList.get(position).getSrc();
                                   String isJPG = imageUrl.substring(imageUrl.length() - 3, imageUrl.length());
                                   if (isJPG.equals("gif")) {
                                       mImageView = new ImageDraweeView(mContext);
                                       ((ImageDraweeView) mImageView).setAspectRatio(DensityUtil.getDeviceHeight(mContext) / DensityUtil.getDeviceWidth(mContext));
                                       params = new ViewGroup.LayoutParams(DensityUtil.getDeviceWidth(mContext), ViewGroup.LayoutParams.WRAP_CONTENT);
                                       ((ImageDraweeView) mImageView).setImageURL(imageUrl);
                                       mImageView.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               finish();
                                           }
                                       });
                                   } else {
//                                       PhotoView mPhotoView = new PhotoView(mContext);
                                       mPhotoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                                           @Override
                                           public void onPhotoTap(View view, float v, float v1) {
                                               PictrueBrowseActivity.this.finish();
                                           }
                                       });
                                       params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                       ImageLoader.getInstance().displayImage(imageUrl, mPhotoView, new SimpleImageLoadingListener() {
                                           @Override
                                           public void onLoadingStarted(String imageUri, View view) {
                                               super.onLoadingStarted(imageUri, view);
                                               dialog.setVisibility(View.VISIBLE);
                                           }

                                           @Override
                                           public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                               super.onLoadingFailed(imageUri, view, failReason);
                                               dialog.setVisibility(View.VISIBLE);
                                           }

                                           @Override
                                           public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                               super.onLoadingComplete(imageUri, view, loadedImage);
                                               dialog.setVisibility(View.GONE);
                                           }
                                       });
                                       container.addView(contentView, params);
                                       return contentView;
                                   }
                                   container.addView(mImageView, params);
                                   return mImageView;
                               }

                               @Override
                               public void destroyItem(ViewGroup container, int position, Object object) {
                                   container.removeView((View) object);
                               }
                           }

        );
        addListener();
        vp_pics.setCurrentItem(startNum);
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

    //    @Override
//    public void onRightAction() {
//        mSharePopupWindow.show(ll_main);
//    }

}
