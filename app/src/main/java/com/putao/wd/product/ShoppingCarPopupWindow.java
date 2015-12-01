package com.putao.wd.product;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.product.adapter.NormsSelectAdapter;
import com.sunnybear.library.controller.BasicPopupWindow;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 购物车弹窗
 * Created by guchenkai on 2015/11/30.
 */
public class ShoppingCarPopupWindow extends BasicPopupWindow implements View.OnClickListener {
    @Bind(R.id.rv_select_norms)
    BasicRecyclerView rv_select_norms;

    private NormsSelectAdapter adapter;

    public ShoppingCarPopupWindow(Context context) {
        super(context);
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
