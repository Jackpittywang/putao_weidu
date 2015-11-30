package com.putao.wd.product;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.product.adapter.NormsSelectAdapter;
import com.sunnybear.library.view.BasicPopupWindow;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 购物车弹窗
 * Created by guchenkai on 2015/11/30.
 */
public class ShoppingCarPopupWindow extends BasicPopupWindow implements View.OnClickListener {
    private static final String[] colors = new String[]{
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
    };
    @Bind(R.id.rv_select_norms)
    BasicRecyclerView rv_select_norms;

    private NormsSelectAdapter adapter;

    public ShoppingCarPopupWindow(Context context, boolean isOpenSelfAdaptionHeight) {
        super(context, isOpenSelfAdaptionHeight);
        adapter = new NormsSelectAdapter(context, null);
        rv_select_norms.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_shopping_car;
    }

    @OnClick({R.id.iv_close})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
        }
    }
}
