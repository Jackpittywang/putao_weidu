package com.sunnybear.library.view.image;

import android.graphics.Bitmap;

import com.facebook.imagepipeline.request.BaseRepeatedPostProcessor;

import java.util.LinkedList;

/**
 * fresco图片处理
 * Created by guchenkai on 2015/11/17.
 */
public class PictureProcessor extends BaseRepeatedPostProcessor {
    private LinkedList<ProcessorInterface> processorList = new LinkedList<>();

    public PictureProcessor addProcessor(ProcessorInterface processorInterface) {
        processorList.add(processorInterface);
        return this;
    }

    @Override
    public void process(Bitmap bitmap) {
        for (ProcessorInterface processor : processorList) {
            processor.process(bitmap);
        }
    }
}
