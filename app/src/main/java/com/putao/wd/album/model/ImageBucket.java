package com.putao.wd.album.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ImageBucket implements Serializable {
    public String bucketID = "";
    public String bucketName = "";
    public List<ImageInfo> images = new ArrayList<ImageInfo>();
}
