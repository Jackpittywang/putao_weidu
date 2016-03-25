package com.putao.wd.start.browse;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.image.ImageDraweeView;


import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片浏览
 * Created by guchenkai on 2015/12/24.
 */
public class PictrueBrowseFragment extends BasicFragment {

    @Bind(R.id.pv_image)
    PhotoView pv_image;
    @Bind(R.id.iv_image)
    ImageDraweeView iv_image;
    @Bind(R.id.loading)
    ProgressBar loading;

    private boolean mIsGif;
    private String mPicUrl;
    public static final String IS_GIF = "is_gif";
    public static final String PIC_URL = "pic_url";

    @Override
    protected int getLayoutId() {
        return R.layout.image_pager_detail_fragment;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        mIsGif = args.getBoolean(IS_GIF);
        mPicUrl = args.getString(PIC_URL);
        if (mIsGif) {
            ViewGroup.LayoutParams layoutParams = iv_image.getLayoutParams();
            layoutParams.height = DensityUtil.dp2px(mActivity, 280);
            iv_image.setLayoutParams(layoutParams);
            pv_image.setVisibility(View.GONE);
            iv_image.setVisibility(View.VISIBLE);
            iv_image.setImageURL(mPicUrl);
            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        } else {
            pv_image.setVisibility(View.VISIBLE);
            iv_image.setVisibility(View.GONE);
            pv_image.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

                @Override
                public void onViewTap(View view, float v, float v1) {
                    getActivity().finish();
                }
            });
            ImageLoader.getInstance().displayImage(mPicUrl, pv_image, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    super.onLoadingStarted(imageUri, view);
                    loading.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    super.onLoadingFailed(imageUri, view, failReason);
                    loading.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    loading.setVisibility(View.GONE);
                }
            });
        }
    }



    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
