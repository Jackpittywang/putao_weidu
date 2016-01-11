package com.putao.wd.store.shopping;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.StoreApi;
import com.putao.wd.model.Norms;
import com.putao.wd.model.ProductNorms;
import com.putao.wd.model.ProductNormsSku;
import com.putao.wd.store.shopping.adapter.NormsSelectAdapter;
import com.putao.wd.store.shopping.util.SpecUtils;
import com.sunnybear.library.controller.BasicPopupWindow;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
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
    public static final String EVENT_REFRESH_TITLE_COUNT = "title_count";
    public static final String EVENT_TO_LOGIN = "to_login";
    public static final String EVENT_GET_PRODUCT_SPEC = "get_product_spec";

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

    private NormsSelectAdapter adapter;

    private String mCount = "1";//总数量
    private float Price = 0;
    private String product_id;

    private List<Tag> mSelTags = new ArrayList<>();
    private int mSpecItemCount;
    private List<ProductNormsSku> skus;
    private ProductNormsSku sku;//选中的商品

    public ShoppingCarPopupWindow(Context context, String pid) {
        super(context);
        product_id = pid;
        ll_join_car.setClickable(false);
        adapter = new NormsSelectAdapter(mActivity, null);
        rv_norms.setAdapter(adapter);
//        getProductSpec();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_shopping_car;
    }

    /**
     * 获取商品规格
     */
    public void getProductSpec() {
        mActivity.networkRequest(StoreApi.getProductSpce(product_id),
                new SimpleFastJsonCallback<ProductNorms>(ProductNorms.class, loading) {
                    @Override
                    public void onSuccess(String url, ProductNorms result) {
                        mSpecItemCount = SpecUtils.getSpecItemCount(result.getSpec().getSpec_item());
                        skus = result.getSku();
                        List<Norms> normses = SpecUtils.getNormses(result.getSpec());
                        normses.add(new Norms());
                        adapter.replaceAll(normses);
                        EventBusHelper.post(EVENT_GET_PRODUCT_SPEC, EVENT_GET_PRODUCT_SPEC);
                        loading.dismiss();
                    }
                });
    }

    private void carAdd(String pid, String qt) {
        mActivity.networkRequest(StoreApi.cartAdd(pid, qt), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                ToastUtils.showToastShort(mContext, "添加成功！");
                Logger.d(result.toString());
                EventBusHelper.post(EVENT_REFRESH_TITLE_COUNT, EVENT_REFRESH_TITLE_COUNT);
            }
        });
    }

    @OnClick({R.id.iv_close, R.id.ll_join_car})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_join_car://加入购物车
                if (!AccountHelper.isLogin()) {
                    EventBusHelper.post(EVENT_TO_LOGIN, EVENT_TO_LOGIN);
                    return;
                }
                ProductNormsSku sku = SpecUtils.getProductSku(skus, mSelTags);
                if (!MathUtils.compare(mCount, sku.getQuantity()))
                    carAdd(sku.getPid(), mCount);
                else
                    ToastUtils.showToastShort(mContext, "库存不足！");
                //ToastUtils.showToastLong(mActivity, SpecUtils.getProductSku(skus, mSelTags).toString());
//                EventBusHelper.post(EVENT_JOIN_CAR, EVENT_JOIN_CAR);
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
                if (MathUtils.compare(mCount, sku.getQuantity())) {
                    ll_join_car.setBackgroundResource(R.color.color_C2C2C2);
                    ll_join_car.setClickable(false);
                }
            } else {
                ll_join_car.setBackgroundResource(R.color.color_C2C2C2);
                ll_join_car.setClickable(false);
            }
        }
    }

    @Subcriber(tag = NormsSelectAdapter.EVENT_COUNT)
    public void eventCount(int count) {
        String sum = MathUtils.multiplication(sku.getPrice(), count);
        mCount = String.valueOf(count);
        tv_product_price.setText(sum);
    }
}
