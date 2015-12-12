package com.putao.wd.store.product;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicPopupWindow;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
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
    @Bind(R.id.iv_product_icon)
    ImageDraweeView iv_product_icon;
    @Bind(R.id.iv_close)
    ImageView iv_close;
    @Bind(R.id.tv_product_title)//产品标题
    TextView tv_product_title;
    @Bind(R.id.tv_product_intro)//产品副标题
    TextView tv_product_intro;
    @Bind(R.id.rl_product)
    RelativeLayout rl_product;
    @Bind(R.id.ll_color)//
    LinearLayout ll_color;
    @Bind(R.id.tv_subtract)//减法
    TextView tv_subtract;
    @Bind(R.id.tv_count)//总数
    TextView tv_count;
    @Bind(R.id.tv_add)//加法
    TextView tv_add;
    @Bind(R.id.tv_product_price)//产品价格
    TextView tv_product_price;
    @Bind(R.id.ll_join_car)//加入购物车
    LinearLayout ll_join_car;
    @Bind(R.id.popup_layout)
    RelativeLayout popup_layout;

    private int count=1;//总数量
    private float sumPrice=0;
    public ShoppingCarPopupWindow(Context context, List<String> colorList,String title,String describe,String price) {
        super(context);
        setUpData(colorList);
        tl_color_tag.addTags(mTags);
        tv_product_title.setText(title);
        tv_product_intro.setText(describe);
        tv_product_price.setText(price);
        sumPrice=Float.parseFloat(price.substring(1));
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
    private void setUpData(List<String> colorList) {
        Tag tag;
        for (int i = 0; i < colorList.size(); i++) {
            tag = new Tag();
            tag.setId(i);
            tag.setTitle(colorList.get(i));
            mTags.add(tag);
        }


//        Tag tag = new Tag();
//        tag.setId(1);
//        tag.setTitle("塔塔紫");
//        mTags.add(tag);
//
//        tag = new Tag();
//        tag.setId(2);
//        tag.setTitle("淘淘粉");
//        mTags.add(tag);
//
//        tag = new Tag();
//        tag.setId(3);
//        tag.setTitle("萌撕拉蓝");
//        tag.setIsEnable(false);
//        mTags.add(tag);
//
//        tag = new Tag();
//        tag.setId(4);
//        tag.setTitle("班得瑞绿");
//        mTags.add(tag);
//
//        tag = new Tag();
//        tag.setId(5);
//        tag.setTitle("魔方橙");
//        mTags.add(tag);
    }

    @OnClick({R.id.iv_close,R.id.tv_add,R.id.tv_subtract})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_add:
                if(1==count)
                    tv_subtract.setEnabled(true);
                tv_count.setText(++count+"");
                tv_product_price.setText(sumPrice*count+"");
                break;
            case R.id.tv_subtract:
                tv_count.setText(--count+"");
                tv_product_price.setText(sumPrice*count+"");
                if(1==count)
                    tv_subtract.setEnabled(false);
                break;
        }
    }
}
