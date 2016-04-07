package com.putao.wd.album.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.album.model.ImageBucket;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/7.
 */
public class BucketAdapter extends BasicAdapter<ImageBucket, BucketAdapter.BucketViewHolder> {
    private Context context;

    public BucketAdapter(Context context, List<ImageBucket> buckets) {
        super(context, buckets);
        this.context = context;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_photo_bucket_item;
    }

    @Override
    public BucketViewHolder getViewHolder(View itemView, int viewType) {
        return new BucketViewHolder(itemView);
    }

    @Override
    public void onBindItem(BucketViewHolder holder, ImageBucket bucket, int position) {
        if (bucket.images.size() > 0)
            if (TextUtils.isEmpty(bucket.images.get(0).THUMB_DATA))
                holder.ivFolder.setImageURI(Uri.parse("file://putao/" + bucket.images.get(0)._DATA));
            else
                holder.ivFolder.setImageURI(Uri.parse("file://putao/" + bucket.images.get(0).THUMB_DATA));
        holder.tvName.setText(bucket.bucketName);
        holder.tvCount.setText(bucket.images.size() + "");
    }

    class BucketViewHolder extends BasicViewHolder {
        @Bind(R.id.ivFolder)
        ImageDraweeView ivFolder;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvCount)
        TextView tvCount;


        public BucketViewHolder(View itemView) {
            super(itemView);
        }
    }
}
