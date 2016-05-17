package com.putao.wd.album.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.album.adapter.BucketAdapter;
import com.putao.wd.album.adapter.ImageInfoAdapter;
import com.putao.wd.album.model.ImageBucket;
import com.putao.wd.album.model.ImageInfo;
import com.putao.wd.album.model.ThumbImageInfo;
import com.putao.wd.album.util.MediaImageUtil;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.LoadingHUD;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/6.
 */
public class PhotoAlbumActivity extends BasicFragmentActivity<GlobalApplication> implements View.OnClickListener {
    private final String TAG = PhotoAlbumActivity.class.getSimpleName();
    @Bind(R.id.tvClose)
    TextView tvClose;
    @Bind(R.id.tvSelectFolder)
    TextView tvSelectFolder;
    @Bind(R.id.rcPhotoList)
    BasicRecyclerView rcPhotoList;
    @Bind(R.id.flFolder)
    FrameLayout flFolder;
    @Bind(R.id.ll_SelectFolder)
    LinearLayout ll_SelectFolder;
    @Bind(R.id.rcFolderList)
    BasicRecyclerView rcFolderList;
    @Bind(R.id.tvCount)
    TextView tvCount;
    @Bind(R.id.tvTip)
    TextView tvTip;
    @Bind(R.id.tvNext)
    TextView tvNext;
    @Bind(R.id.llBottoom)
    LinearLayout llBottoom;

    private int maxCount = 1;
    private List<ImageBucket> buckets = new ArrayList<>();
    private List<ImageInfo> currentImages = new ArrayList<>();
    private List<ImageInfo> selectPhotos = new ArrayList<>();
    private ImageInfoAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_album;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        executeAllImage();

        Bundle data = getIntent().getExtras();
        if (data != null && data.containsKey("MAX_COUNT")) {
            maxCount = data.getInt("MAX_COUNT");
        }

        if (maxCount < 2)
            tvTip.setText("请选择" + maxCount + "张照片");
        else
            tvTip.setText("请选择1 - " + maxCount + "张照片");
        tvCount.setText(" " + selectPhotos.size());

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        rcPhotoList.setLayoutManager(manager);
        adapter = new ImageInfoAdapter(mContext, currentImages);
        rcPhotoList.setAdapter(adapter);
        rcPhotoList.setOnItemClickListener(new OnItemClickListener<ImageInfo>() {
            @Override
            public void onItemClick(ImageInfo photoInfo, int position) {
                if (photoInfo.isSelect && selectPhotos.contains(photoInfo)) {
                    photoInfo.isSelect = false;
                    selectPhotos.remove(photoInfo);
                    adapter.notifyDataSetChanged();
                } else {
                    if (selectPhotos.size() < maxCount) {
                        photoInfo.isSelect = true;
                        selectPhotos.add(photoInfo);
                        adapter.notifyDataSetChanged();
                    }
                }
                if (selectPhotos.size() > 0) {
                    tvCount.setBackgroundColor(getResources().getColor(R.color.text_main_color_nor));
                    tvNext.setBackgroundColor(getResources().getColor(R.color.text_main_color_nor));
                } else {
                    tvCount.setBackgroundColor(getResources().getColor(R.color.player_gray));
                    tvNext.setBackgroundColor(getResources().getColor(R.color.player_gray));
                }
                tvCount.setText(" " + selectPhotos.size());
            }
        });

        LinearLayoutManager bucketManager = new LinearLayoutManager(this);
        bucketManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcFolderList.setLayoutManager(bucketManager);
        BucketAdapter bucketAdapter = new BucketAdapter(mContext, buckets);
        rcFolderList.setAdapter(bucketAdapter);
        rcFolderList.setOnItemClickListener(new OnItemClickListener<ImageBucket>() {
            @Override
            public void onItemClick(ImageBucket bucket, int position) {
                currentImages.clear();
                currentImages.addAll(bucket.images);
                for (ImageInfo info : bucket.images) {
                    if (selectPhotos.contains(info)) {
                        info.isSelect = true;
                    }
                }
                tvSelectFolder.setText(bucket.bucketName);
                adapter.notifyDataSetChanged();
                hideFolderSelect();
            }
        });
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, DensityUtil.getDeviceHeight(mContext));
        rcFolderList.setLayoutParams(params);
//        rcFolderList.animate().translationY(-DensityUtil.getDeviceHeight(mContext)).setDuration(0).start();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tvClose, R.id.ll_SelectFolder, R.id.tvNext})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvClose:
                finish();
            case R.id.ll_SelectFolder:
                showFolderSelect();
                break;
            case R.id.tvNext:
                if (selectPhotos.size() > 0) {
                    EventBusHelper.post(selectPhotos, AccountConstants.EventBus.EVENT_ALBUM_SELECT);
                    finish();
                } else ToastUtils.showToastShort(mContext, "您还没有选择图片~");
                break;
        }

    }

    public void showFolderSelect() {
        flFolder.setVisibility(View.VISIBLE);
//        rcFolderList.animate().translationY(DensityUtil.getDeviceHeight(mContext)).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300).start();
    }

    public void hideFolderSelect() {
        flFolder.setVisibility(View.GONE);
//        rcFolderList.animate().translationY(DensityUtil.getDeviceHeight(mContext)).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(0).start();
    }

    LoadingHUD dialog;

    private void executeAllImage() {


        new AsyncTask<Void, Void, Void>() {
            private List<ImageInfo> defaultImags = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = LoadingHUD.getInstance(mContext);
                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                List<ImageInfo> imageInfos = MediaImageUtil.queryAllPhotoByFolder(mContext, "");
                List<ThumbImageInfo> thumbImageInfos = MediaImageUtil.queryAllPhotoThumbnail(mContext);
                for (ImageInfo info : imageInfos) {
                    //匹配缩略图路径
                    for (ThumbImageInfo thumbInfo : thumbImageInfos) {
                        if (thumbInfo._ID.equals(info._ID)) {
                            info.THUMB_DATA = thumbInfo.THUMB_DATA;
                            break;
                        }
                    }

                    //按文件夹分类
                    boolean has = false;
                    for (ImageBucket bucket : buckets) {
                        if (bucket.bucketID.equals(info.BUCKET_ID)) {
                            bucket.images.add(info);
                            has = true;
                            break;
                        }
                    }

                    if (!has) {
                        ImageBucket bucket = new ImageBucket();
                        bucket.bucketID = info.BUCKET_ID;
                        bucket.bucketName = info.BUCKET_DISPLAY_NAME;
                        bucket.images.add(info);
                        buckets.add(bucket);
                    }

                    //默认显示的图片集合
                    if (info._DATA.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DCIM)) {
                        defaultImags.add(info);
                    }
                }

                ImageBucket bucket = new ImageBucket();
                bucket.bucketID = "";
                bucket.bucketName = "相机胶卷";
                bucket.images.addAll(defaultImags);
                buckets.add(0, bucket);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                currentImages.clear();
                currentImages.addAll(defaultImags);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }.execute();
    }

}