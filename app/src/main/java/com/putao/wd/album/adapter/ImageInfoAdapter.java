package com.putao.wd.album.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.album.model.ImageInfo;
import com.putao.wd.util.ImageLoaderUtil;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ImageInfoAdapter extends BasicAdapter<ImageInfo, ImageInfoAdapter.PhotoViewHolder> {
    private Context context;

    public ImageInfoAdapter(Context context, List<ImageInfo> imageInfos) {
        super(context, imageInfos);
        this.context = context;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_photo_album_item;
    }

    @Override
    public PhotoViewHolder getViewHolder(View itemView, int viewType) {
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindItem(PhotoViewHolder holder, ImageInfo photoInfo, int position) {
        ViewGroup.LayoutParams itemParam = new ViewGroup.LayoutParams(DensityUtil.getDeviceWidth(context) / 4, DensityUtil.getDeviceWidth(context) / 4);
        holder.rlItem.setLayoutParams(itemParam);
        if (TextUtils.isEmpty(photoInfo.THUMB_DATA))
            ImageLoaderUtil.getInstance(context).displayImage("file://" + photoInfo._DATA, holder.ivPhoto);
        else
            ImageLoaderUtil.getInstance(context).displayImage("file://" + photoInfo._DATA, holder.ivPhoto);
        holder.cbPhotoSelect.setChecked(photoInfo.isSelect);
    }

    class PhotoViewHolder extends BasicViewHolder {
        @Bind(R.id.rlItem)
        RelativeLayout rlItem;
        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;
        @Bind(R.id.cbPhotoSelect)
        CheckBox cbPhotoSelect;

        public PhotoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
