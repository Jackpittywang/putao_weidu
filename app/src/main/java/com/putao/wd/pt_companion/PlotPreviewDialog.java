package com.putao.wd.pt_companion;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.model.ExploreProductPlot;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.view.image.BitmapLoader;

/**
 * 教育理念预览
 * Created by guchenkai on 2016/1/4.
 */
public class PlotPreviewDialog extends Dialog {
    private View mRootView;
    private ImageView iv_plot_icon;
    private TextView tv_content;

    public PlotPreviewDialog(Context context, ExploreProductPlot plot) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        this.setCanceledOnTouchOutside(true);
        mRootView = getLayoutInflater().inflate(R.layout.fragment_explore_plot_dialog, null);
        iv_plot_icon = (ImageView) mRootView.findViewById(R.id.iv_plot_icon);
        tv_content = (TextView) mRootView.findViewById(R.id.tv_content);

        BitmapLoader.newInstance((Activity) context).load(plot.getImg_url(), new BitmapLoader.BitmapCallback() {
            @Override
            public void onResult(Bitmap bitmap) {
                iv_plot_icon.setImageBitmap(bitmap);
            }
        });
        tv_content.setText(plot.getContent());
        setContentView(mRootView);
    }

    public View getRootView() {
        return mRootView;
    }

    public void shareImage() {
        ImageUtils.cutOutViewToImage(mRootView, GlobalApplication.shareImagePath);
    }
}
