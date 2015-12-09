package com.sunnybear.library.view.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.sunnybear.library.R;

/**
 * 图片加载控件
 * Created by guchenkai on 2015/11/4.
 */
public class ImageDraweeView extends SimpleDraweeView {
    public static int placeholderImage;
    public static int progressBarImage;
    public static int failureImage;
    public static int retryImage;
    private float mRatio;
    private ResizeOptions mResizeOptions;

    private PictureProcessor mProcessor;

    public ImageDraweeView(Context context) {
        this(context, null);
    }

    public ImageDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageDraweeView);
        mRatio = a.getFloat(R.styleable.ImageDraweeView_aspect_ratio, -1f);
        if (mRatio < 0)
            setAspectRatio(mRatio);
        a.recycle();

        init(context);
    }

    private void init(Context context) {
        GenericDraweeHierarchy hierarchy = getHierarchy();
        //淡入淡出
        hierarchy.setFadeDuration(500);
        if (placeholderImage != 0)
            //设置占位图
            hierarchy.setPlaceholderImage(ContextCompat.getDrawable(context, placeholderImage), ScalingUtils.ScaleType.FIT_CENTER);
        if (progressBarImage != 0)
            //设置正在加载图
            hierarchy.setProgressBarImage(ContextCompat.getDrawable(context, progressBarImage), ScalingUtils.ScaleType.CENTER_INSIDE);
        if (failureImage != 0)
            //设置失败图
            hierarchy.setFailureImage(ContextCompat.getDrawable(context, failureImage), ScalingUtils.ScaleType.CENTER_INSIDE);
        if (retryImage != 0)
            //设置重试图
            hierarchy.setRetryImage(ContextCompat.getDrawable(context, retryImage), ScalingUtils.ScaleType.CENTER_INSIDE);
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
        setHierarchy(hierarchy);

        mProcessor = new PictureProcessor();
    }

    public ImageDraweeView resize(int width, int height) {
        if (width == 0 || height == 0) {
            mResizeOptions = null;
            return this;
        }
        mResizeOptions = new ResizeOptions(width, height);
        return this;
    }


    /**
     * 添加加载后处理器
     *
     * @param processor 加载后处理器
     */
    public void addProcessor(ProcessorInterface processor) {
        mProcessor.addProcessor(processor);
    }

    /**
     * 加载图片
     * @param url 图片的url
     */
    public void setImageURL(String url) {
        if (TextUtils.isEmpty(url))
            return;
        Uri uri = Uri.parse(url);
        setImageURI(uri);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(mProcessor)
                .setAutoRotateEnabled(true)
                .setLocalThumbnailPreviewsEnabled(true)
                .setResizeOptions(mResizeOptions)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setTapToRetryEnabled(true)//加载失败时点击重新加载
                .setOldController(getController())
                .build();
        setController(controller);
    }
}
