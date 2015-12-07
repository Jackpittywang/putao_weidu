package com.putao.wd.store.product;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicPopupWindow;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.select.Tag;
import com.sunnybear.library.view.select.TagBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 购物车弹窗
 * Created by guchenkai on 2015/11/30.
 */
public class ShoppingCarPopupWindow extends BasicPopupWindow implements View.OnClickListener {
    @Bind(R.id.tl_color_tag)
    TagBar tl_color_tag;
    private final List<Tag> mTags = new ArrayList<>();

    public ShoppingCarPopupWindow(Context context) {
        super(context);
        setUpData();
        tl_color_tag.addTags(mTags);

        tl_color_tag.setTagItemCheckListener(new TagBar.TagItemCheckListener() {
            @Override
            public void onTagItemCheck(Tag tag, int position) {
                ToastUtils.showToastLong(mContext, tag.toString());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_shopping_car;
    }

    /**
     * 测试数据
     */
    private void setUpData() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setTitle("塔塔紫");
        mTags.add(tag);

        tag = new Tag();
        tag.setId(2);
        tag.setTitle("淘淘粉");
        mTags.add(tag);

        tag = new Tag();
        tag.setId(3);
        tag.setTitle("萌撕拉蓝");
        tag.setIsEnable(false);
        mTags.add(tag);

        tag = new Tag();
        tag.setId(4);
        tag.setTitle("班得瑞绿");
        mTags.add(tag);

        tag = new Tag();
        tag.setId(5);
        tag.setTitle("魔方橙");
        mTags.add(tag);
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
