package com.putao.wd.image;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.image.ImageDraweeView;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;


public class ImageDetailFragment extends BasicFragment {
    private String mImageUrl;
    @Bind(R.id.image)
    ImageView mImageView;
    @Bind(R.id.image_gif)
    ImageDraweeView imageView_gif;
    private PhotoViewAttacher mAttacher;

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);

        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.image_pager_detail_fragment;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        String isJPG = mImageUrl.substring(mImageUrl.length() - 3, mImageUrl.length());
        if (isJPG.equals("gif")) {
            DraweeController draweeController =
                    Fresco.newDraweeControllerBuilder()
                            .setUri(mImageUrl)
                            .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                            .build();
            imageView_gif.setController(draweeController);
            imageView_gif.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);
        } else {
            imageView_gif.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mImageUrl, mImageView);

        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
