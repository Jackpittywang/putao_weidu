package com.sunnybear.library.view.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.sunnybear.library.util.ResourcesUtils;

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
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageDraweeView);
        mRatio = array.getFloat(R.styleable.ImageDraweeView_aspect_ratio, -1f);
        if (mRatio != -1f)
            setAspectRatio(mRatio);
        array.recycle();
        init(context);
    }

    private void init(Context context) {
        GenericDraweeHierarchy hierarchy = getHierarchy();
        hierarchy.setFadeDuration(300);//淡入淡出
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
     * 设置默认图片
     *
     * @param bitmap 默认图片
     */
    public void setDefaultImage(Bitmap bitmap) {
        GenericDraweeHierarchy hierarchy = getHierarchy();
        hierarchy.setPlaceholderImage(new BitmapDrawable(bitmap), ScalingUtils.ScaleType.FOCUS_CROP);
        setHierarchy(hierarchy);
    }

    /**
     * 设置ScaleType
     *
     * @param scaleType ScaleType
     */
    public void setScaleType(ScalingUtils.ScaleType scaleType) {
        GenericDraweeHierarchy hierarchy = getHierarchy();
        hierarchy.setActualImageScaleType(scaleType);
        setHierarchy(hierarchy);
    }

    /**
     * 设置默认图片
     *
     * @param resId 默认图片资源id
     */
    public void setDefaultImage(int resId) {
        Drawable drawable = ResourcesUtils.getDrawable(getContext(), resId);
        GenericDraweeHierarchy hierarchy = getHierarchy();
        hierarchy.setPlaceholderImage(drawable, ScalingUtils.ScaleType.FOCUS_CROP);
        setHierarchy(hierarchy);
    }

    /**
     * 加载图片
     *
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
