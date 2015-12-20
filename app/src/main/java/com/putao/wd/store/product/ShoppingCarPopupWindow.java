package com.putao.wd.store.product;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.model.Cart;
import com.putao.wd.model.Norms;
import com.putao.wd.model.ProductNorms;
import com.putao.wd.model.ProductNormsSku;
import com.putao.wd.store.product.adapter.NormsSelectAdapter;
import com.putao.wd.store.product.util.SpecUtils;
import com.sunnybear.library.controller.BasicPopupWindow;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.MathUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.select.Tag;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 购物车弹窗
 * Created by guchenkai on 2015/11/30.
 */
public class ShoppingCarPopupWindow extends BasicPopupWindow implements View.OnClickListener {
//    public static final String EVENT_JOIN_CAR = "join_car";
    public static final String EVENT_UPDATE_NORMS="update_norms";

    @Bind(R.id.iv_product_icon)
    ImageDraweeView iv_product_icon;
    @Bind(R.id.iv_close)
    ImageView iv_close;
    @Bind(R.id.tv_product_title)
    TextView tv_product_title;//产品标题
    @Bind(R.id.tv_product_intro)
    TextView tv_product_intro;//产品副标题
    @Bind(R.id.rv_norms)
    BasicRecyclerView rv_norms;//产品规格
    @Bind(R.id.tv_product_price)
    TextView tv_product_price;//产品价格
    @Bind(R.id.ll_join_car)
    LinearLayout ll_join_car;//加入购物车
    @Bind(R.id.ll_add_shopcart)
    LinearLayout ll_add_shopcart;//加入购物车布局区域
    @Bind(R.id.tv_confirm_update)
    TextView tv_confirm_update;//确认修改


    private NormsSelectAdapter adapter;

    private String count = "1";//总数量
    private float Price = 0;
    private String product_id;

    private List<Tag> mSelTags = new ArrayList<>();
    private int mSpecItemCount;
    private List<ProductNormsSku> skus;
    private ProductNormsSku sku;//选中的商品
    private String operateType;//添加、修改

    public ShoppingCarPopupWindow(Context context, String pid,String operateType) {
        super(context);
        product_id = pid;
        //this.operateType=operateType;
        switch (operateType){
            case "add":
                ll_add_shopcart.setVisibility(View.VISIBLE);break;
            case "update":
                tv_confirm_update.setVisibility(View.VISIBLE);break;
        }
        ll_join_car.setClickable(false);
        adapter = new NormsSelectAdapter(mActivity, null);
        rv_norms.setAdapter(adapter);
        getProductSpec(pid);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_shopping_car;
    }

    /**
     * 获取商品规格
     *
     * @param product_id 产品id
     */
    private void getProductSpec(String product_id) {
        mActivity.networkRequest(StoreApi.getProductSpce(product_id),
                new SimpleFastJsonCallback<ProductNorms>(ProductNorms.class, loading) {
                    @Override
                    public void onSuccess(String url, ProductNorms result) {
                        mSpecItemCount = SpecUtils.getSpecItemCount(result.getSpec().getSpec_item());
                        skus = result.getSku();
                        List<Norms> normses = SpecUtils.getNormses(result.getSpec());
                        normses.add(new Norms());
                        adapter.addAll(normses);
                    }
                });
    }

    private void carAdd(String pid, String qt) {
        mActivity.networkRequest(StoreApi.cartAdd(pid, qt), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                ToastUtils.showToastShort(mContext, "添加成功！");
                Logger.d(result.toString());
            }
        });
    }

    /**
     * 更改商品规格购物车
     */
    private void cartChange(String old_pid,String new_pid){
        mActivity.networkRequest(StoreApi.cartChange(old_pid, new_pid), new SimpleFastJsonCallback<ArrayList<Cart>>(Cart.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Cart> result) {
                Logger.d(result.toString());

            }

        });
    }

    @OnClick({R.id.iv_close, R.id.ll_join_car,R.id.tv_confirm_update})
    @Override
    public void onClick(View v) {
        ProductNormsSku sku = SpecUtils.getProductSku(skus, mSelTags);
        switch (v.getId()) {
            case R.id.ll_join_car://加入购物车
                if (!MathUtils.compare(count, sku.getQuantity()))
                    carAdd(sku.getPid(), count);
                else
                    ToastUtils.showToastShort(mContext, "库存不足！");
                //ToastUtils.showToastLong(mActivity, SpecUtils.getProductSku(skus, mSelTags).toString());
//                EventBusHelper.post(EVENT_JOIN_CAR, EVENT_JOIN_CAR);
                break;
            case R.id.tv_confirm_update://修改购物车产品规格参数
                cartChange(product_id,sku.getPid());
                EventBusHelper.post(sku, EVENT_UPDATE_NORMS);
                break;
        }
        dismiss();
    }

    @Subcriber(tag = NormsSelectAdapter.EVENT_DEFAULT_TAG)
    public void eventDefaultTag(Tag tag) {
        getProductSku(tag);
    }

    @Subcriber(tag = NormsSelectAdapter.EVENT_SEL_TAG)
    public void eventSelTag(Tag tag) {
        getProductSku(tag);
    }

    /**
     * 获取商品规格对应的商品信息
     *
     * @param tag 标签
     */
    private void getProductSku(Tag tag) {
        SpecUtils.addTag(mSelTags, tag, mSpecItemCount);
        if (mSelTags.size() == mSpecItemCount) {
            sku = SpecUtils.getProductSku(skus, mSelTags);
            if (sku != null) {
                iv_product_icon.setImageURL(sku.getIcon());
                ll_join_car.setBackgroundResource(R.color.text_main_color_nor);
                ll_join_car.setClickable(true);
                tv_product_price.setText(sku.getPrice());
                adapter.resetAmount();
            } else {
                ll_join_car.setBackgroundResource(R.color.color_C2C2C2);
                ll_join_car.setClickable(false);
            }
        }
    }

    @Subcriber(tag = NormsSelectAdapter.EVENT_COUNT)
    public void eventCount(int count) {
        String string = MathUtils.multiplication(sku.getPrice(), count);
        this.count = string;
        tv_product_price.setText(string);
    }
}
