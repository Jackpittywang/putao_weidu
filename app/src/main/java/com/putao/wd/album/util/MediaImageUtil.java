package com.putao.wd.album.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.putao.wd.album.model.ImageInfo;
import com.putao.wd.album.model.ThumbImageInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class MediaImageUtil {
    public static final String IMAGE_URI = "content://media/external/images/media/";

    /**
     * 得到所有照片的缩略图
     *
     * @param context
     * @return
     */
    public static List<ThumbImageInfo> queryAllPhotoThumbnail(Context context) {
        List<ThumbImageInfo> photoInfos = new ArrayList<ThumbImageInfo>();
        String[] projection = {MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.DATA};
        Cursor cursor = null;
        try {
            String sortString = MediaStore.Images.Thumbnails._ID + " desc";
            cursor = context.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, sortString);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ThumbImageInfo photoInfo = new ThumbImageInfo();
                    photoInfo._ID = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
                    photoInfo.THUMB_DATA = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
                    photoInfo.THUMB_ID = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID));
                    photoInfos.add(photoInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return photoInfos;
    }


    /**
     * 得到所有照片
     * @param context
     * @param folderPath
     * @return
     */
    public static List<ImageInfo> queryAllPhotoByFolder(Context context, String folderPath) {
        List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED, MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        Cursor cursor = null;
        try {
            String sortString = MediaStore.Images.ImageColumns.DATE_MODIFIED + " desc";
            if (TextUtils.isEmpty(folderPath)) {
                cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortString);
            } else {
                String[] selectionArgs = new String[]{"%" + folderPath + "%"};
                cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, MediaStore.Images.Media.DATA
                        + " like ?  or " + MediaStore.Images.Media.DATA + " like ?  ", selectionArgs, sortString);
            }

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo._ID = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    imageInfo._DATA = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    imageInfo._SIZE = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));
                    imageInfo.TITLE = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                    imageInfo.DATE_ADDED = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));
                    imageInfo.DATE_MODIFIED = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED));
                    imageInfo.BUCKET_ID = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID));
                    imageInfo.BUCKET_DISPLAY_NAME = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    imageInfos.add(imageInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            return imageInfos;
        }
    }
}