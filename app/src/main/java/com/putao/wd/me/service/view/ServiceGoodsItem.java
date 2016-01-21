package com.putao.wd.me.service.view;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ServiceGoodsDto;
import com.sunnybear.library.view.image.ImageDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanguoqiang on 15/11/30.
 */
@Deprecated
public class ServiceGoodsItem extends LinearLayout {

    @Bind(R.id.img_goods)
    ImageDraweeView img_goods;
    @Bind(R.id.tv_price)
    TextView tv_price;
    @Bind(R.id.tv_number)
    TextView tv_number;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_specification)
    TextView tv_specification;
    private Context context;

//    private TextView tv_price;
//
//    private TextView tv_number;
//
//    private TextView tv_name;
//
//    private TextView tv_specification;
//
//    private ImageDraweeView img_goods;

    public ServiceGoodsItem(Context context, ServiceGoodsDto serviceGoodsDto) {
        super(context);

        this.context = context;
        View view = LinearLayout.inflate(context, R.layout.activity_service_list_item_goods, this);
        ButterKnife.bind(view, this);
//        img_goods = (ImageDraweeView) this.findViewById(R.id.img_goods);
//        tv_name = (TextView) this.findViewById(R.id.tv_name);
//        tv_number = (TextView) this.findViewById(R.id.tv_number);
//        tv_price = (TextView) this.findViewById(R.id.tv_price);
//        tv_specification = (TextView) this.findViewById(R.id.tv_specification);

        refreshView(serviceGoodsDto);
    }

    /**
     * 刷新界面
     *
     * @param serviceGoodsDto
     */
    private void refreshView(ServiceGoodsDto serviceGoodsDto) {
        img_goods.setImageURL(serviceGoodsDto.getImageUrl());
        if (serviceGoodsDto.isPreSale())
            tv_name.setText(Html.fromHtml("<font color=0xed5564>[预售]</font>" + serviceGoodsDto.getName()));
        else tv_name.setText(serviceGoodsDto.getName());
        tv_number.setText("x " + serviceGoodsDto.getNumber() + "");
        tv_price.setText("¥ " + serviceGoodsDto.getPrice() + "");
        tv_specification.setText(serviceGoodsDto.getSpecification());
    }


}
