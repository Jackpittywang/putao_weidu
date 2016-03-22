package com.putao.wd.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
//        networkRequest(ExploreApi.);
//        loading.show();
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        String isJPG = mImageUrl.substring(mImageUrl.length() - 3, mImageUrl.length());
        if (isJPG.equals("gif")) {
            imageView_gif.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);
            DraweeController draweeController =
                    Fresco.newDraweeControllerBuilder()
                            .setUri(mImageUrl)
                            .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                            .build();
            imageView_gif.setController(draweeController);
//            loading.dismiss();
        } else {
            imageView_gif.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mImageUrl, mImageView);

//            loading.dismiss();
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

//    private String mImageUrl;
//    private ImageView mImageView;
//    private ProgressBar progressBar;
//    private PhotoViewAttacher mAttacher;
//    private ImageDraweeView imageView_gif;
//    private Bitmap bitmap, newBitmap;
//
//    public static ImageDetailFragment newInstance(String imageUrl) {
//        final ImageDetailFragment f = new ImageDetailFragment();
//
//        final Bundle args = new Bundle();
//        args.putString("url", imageUrl);
//        f.setArguments(args);
//
//        return f;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View v = inflater.inflate(R.layout.image_pager_detail_fragment, container, false);
//        mImageView = (ImageView) v.findViewById(R.id.image);
//        imageView_gif = (ImageDraweeView) v.findViewById(R.id.image_gif);
//        mAttacher = new PhotoViewAttacher(mImageView);
//
//        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//
//            @Override
//            public void onPhotoTap(View arg0, float arg1, float arg2) {
//                getActivity().finish();
//            }
//        });
//
//        progressBar = (ProgressBar) v.findViewById(R.id.loading);
//        return v;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
//        String isJPG = mImageUrl.substring(mImageUrl.length() - 3, mImageUrl.length());
//        if (isJPG.equals("gif")) {
//            imageView_gif.setVisibility(View.VISIBLE);
//            mImageView.setVisibility(View.GONE);
//            DraweeController draweeController =
//                    Fresco.newDraweeControllerBuilder()
//                            .setUri(mImageUrl)
//                            .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
//                            .build();
//            imageView_gif.setController(draweeController);
//        } else {
//            imageView_gif.setVisibility(View.GONE);
//            mImageView.setVisibility(View.VISIBLE);
//            //由于图片太大需要对某一些图片进行加工，缩放
//            ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
//                @Override
//                public void onLoadingStarted(String imageUri, View view) {
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                    String message = null;
//                    switch (failReason.getType()) {
//                        case IO_ERROR:
//                            message = "下载错误";
//                            break;
//                        case DECODING_ERROR:
//                            message = "图片无法显示";
//                            break;
//                        case NETWORK_DENIED:
//                            message = "网络有问题，无法下载";
//                            break;
//                        case OUT_OF_MEMORY:
//                            message = "图片太大无法显示";
//                            break;
//                        case UNKNOWN:
//                            message = "未知的错误";
//                            break;
//                    }
//                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                    progressBar.setVisibility(View.GONE);
//                    mAttacher.update();
//                }
//            });
//        }
//
//    }

    //图片的缩放
    public void OnImageDetail(String path) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(path, newOpts);
//            return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

}
