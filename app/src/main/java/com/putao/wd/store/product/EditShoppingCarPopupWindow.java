package com.putao.wd.store.product;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.model.Cart;
import com.putao.wd.model.Norms;
import com.putao.wd.model.ProductNorms;
import com.putao.wd.model.ProductNormsSku;
import com.putao.wd.store.product.adapter.EditNormsSelectAdapter;
import com.putao.wd.store.product.util.SpecUtils;
import com.sunnybear.library.controller.BasicPopupWindow;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.select.Tag;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 编辑购物车产品规格参数弹窗
 * Created by wangou on 2015/11/30.
 */
public class EditShoppingCarPopupWindow extends BasicPopupWindow implements View.OnClickListener {
    public static final String EVENT_UPDATE_NORMS = "update_norms";

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
    @Bind(R.id.tv_confirm_update)
    TextView tv_confirm_update;//确认修改


    private EditNormsSelectAdapter adapter;

    private String count = "1";//总数量
    private float Price = 0;

    private List<Tag> mSelTags = new ArrayList<>();
    private int mSpecItemCount;
    private List<ProductNormsSku> skus;
    private ProductNormsSku sku;//选中的商品
    private List<Norms> normses;//规格列表

    private Cart mCart;//当前修改的购物车项目

    public EditShoppingCarPopupWindow(Context context, String pid, Cart cart) {
        super(context);
        mCart = cart;
        getProductSpec(pid);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_edit_shopping_car;
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
                        normses = SpecUtils.getNormses(result.getSpec());
                        adapter = new EditNormsSelectAdapter(mActivity, normses);
                        rv_norms.setAdapter(adapter);
                    }
                });
    }


    /**
     * 更改商品规格购物车
     */
    private void cartChange(String old_pid, String new_pid) {
        mActivity.networkRequest(StoreApi.cartChange(old_pid, new_pid), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                Logger.d(result.toString());

            }
        });
    }

    @OnClick({R.id.iv_close, R.id.tv_confirm_update})
    @Override
    public void onClick(View v) {
        ProductNormsSku sku = SpecUtils.getProductSku(skus, mSelTags);
        switch (v.getId()) {
            case R.id.tv_confirm_update://修改购物车产品规格参数
                //cartChange(product_id, sku.getPid());
                String strSku = "";
                for (int i = 0; i < normses.size(); i++) {
                    strSku = strSku + normses.get(i).getTitle().substring(2) + ":" + mSelTags.get(i).getText() + " ";
                }
                Cart cart = new Cart();
                cart.setPrice(sku.getPrice());
                cart.setSku(strSku);
                cart.setPid(sku.getPid());
                sku.setQuantity(strSku);//临时保存返回到购物车主界面的规格
                EventBusHelper.post(cart, EVENT_UPDATE_NORMS);
                break;
        }
        dismiss();
    }

    @Subcriber(tag = EditNormsSelectAdapter.EVENT_DEFAULT_TAG)
    public void eventDefaultTag(Tag tag) {
        getProductSku(tag);
    }

    @Subcriber(tag = EditNormsSelectAdapter.EVENT_SEL_TAG)
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
            }
        }
    }
}
