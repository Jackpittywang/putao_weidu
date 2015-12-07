package com.putao.wd.store.order;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import butterknife.Bind;

/**
 * 填写订单
 * Created by guchenkai on 2015/11/30.
 */
public class WriteOrderActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.iv_reapte_picbar)
    ImageView iv_reapte_picbar;

    private int screenWidth=0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_order;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        screenWidth=((WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        Resources res = mContext.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.img_cart_lace_stuff);
        iv_reapte_picbar.setImageBitmap(createRepeater(screenWidth, bitmap));//screenWidth为屏幕宽度（或显示图片的imageview宽度）

    }

    //将图片在imageview中x轴循环填充需要的bitmap
    public static Bitmap createRepeater(int width, Bitmap src) {
        int count = (width + src.getWidth() - 1) / src.getWidth(); //计算出平铺填满所给width（宽度）最少需要的重复次数
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth()*count, src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int idx = 0; idx < count; ++idx) {
            canvas.drawBitmap(src, idx * src.getWidth(), 0, null);
        }
        return bitmap;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
