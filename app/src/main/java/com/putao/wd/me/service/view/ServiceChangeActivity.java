package com.putao.wd.me.service.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.api.UploadApi;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;
import com.putao.wd.me.service.ServiceChooseActivity;
import com.putao.wd.me.service.adapter.ChangeListAdapter;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.model.ProductData;
import com.putao.wd.model.ServiceBackImage;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.UploadFileTask;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;


/**
 * 退货列表
 * Created by wangou on 15/12/7.
 */
public class ServiceChangeActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Bind(R.id.tv_service_money)
    TextView tv_service_money;//退货金额
    @Bind(R.id.rv_service_back_list)
    BasicRecyclerView rv_service_back_list;//售后列表
    @Bind(R.id.rl_back_detail)
    RelativeLayout rl_back_detail;

    private final int ALBUM_REQCODE = 1;
    private final int CAMERA_REQCODE = 2;
    private int position;
    private SelectPopupWindow mSelectPopupWindow;
    private ChangeListAdapter adapter;
    public static final String ORDER_ID = "orderId";
    private Order mOrder;
    private String mOrderId;
    private ArrayList<OrderProduct> mProducts;
    private String[] mItems;

    private double totalPrice;


    //--------上传
    private String uploadToken;//上传token
    private String sha1;//上传文件sha1
    private File uploadFile;//上传文件
    private String filePath;//文件路径
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = (Bundle) msg.obj;
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_back_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        initData();
        addListener();
    }

    private void addListener() {
    }

    private void initView() {
        tv_service_money.setText("￥" + totalPrice);
        adapter = new ChangeListAdapter(mContext, mProducts);
        rv_service_back_list.setAdapter(adapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mOrderId = args.getString(ORDER_ID);
        mProducts = (ArrayList<OrderProduct>) args.getSerializable(ServiceChooseActivity.SERVICE_PRODUCT);
        mItems = getResources().getStringArray(R.array.change_spinnername);
        totalPrice = 0;
        for (OrderProduct product : mProducts) {
            double price = Double.parseDouble(product.getPrice());
            int quantity = Integer.parseInt(product.getQuantity());
            totalPrice += price * quantity;
        }
        initView();
    }

    private void chooseData() {
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Override
    public void onClick(View v) {

    }

    //选择图片源
    @Subcriber(tag = ChangeListAdapter.CHOOSE_IMAGE)
    public void chooseImage(int position) {
        this.position = position;
        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQCODE);
            }

            @Override
            public void onSecondClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ALBUM_REQCODE);
            }
        };
        mSelectPopupWindow.show(rl_back_detail);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        filePath = GlobalApplication.sdCardPath + File.separator + "service_back" + position + "a" + mProducts.size() + ".jpg";
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case CAMERA_REQCODE://相机选择
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap) bundle.get("data");

                    break;
                case ALBUM_REQCODE://相册选择
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);

                    Logger.d(picturePath);
                    cursor.close();
                    bitmap = ImageUtils.getSmallBitmap(picturePath, 320, 320);
                    break;
            }
            ImageUtils.bitmapOutSdCard(bitmap, filePath);
            ServiceBackImage serviceBackImage = new ServiceBackImage();
            serviceBackImage.setBitmap(bitmap);
            serviceBackImage.setURL(filePath);
            mProducts.get(position).getServiceBackImage().add(serviceBackImage);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRightAction() {

        for (OrderProduct product : mProducts) {
            List<ServiceBackImage> serviceBackImages = product.getServiceBackImage();
            if (serviceBackImages != null & serviceBackImages.size() > 0) {
                for (ServiceBackImage serviceBackImage : serviceBackImages) {
                    if (serviceBackImage != null) {
                        uploadFile = new File(serviceBackImage.getURL());
                        sha1 = FileUtils.getSHA1ByFile(uploadFile);
                        getUploadToken();
                        serviceBackImage.setSha1(sha1);
                    }
                }
            }
        }
        requestBack();
    }

    /**
     * 请求售后
     */
    private void requestBack() {
        String allProductId = "";
        Map<String, ProductData> productDataMap = new HashMap<>();
        String sha1 = new String();
        for (OrderProduct product : mProducts) {
            allProductId = allProductId + "," + product.getProduct_id();
            ProductData productData = new ProductData();
            productData.setProduct_id(product.getId());
            productData.setReason(product.getReason());
            productData.setDescription("");
            productData.setImage("");
            productData.setQuantity(product.getQuantity());
            for (ServiceBackImage serviceBackImage : product.getServiceBackImage()) {
                sha1+=serviceBackImage.getSha1()+",";
            }
            productData.setImage(sha1.substring(1));
            productDataMap.put(product.getProduct_id() + "", productData);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(productDataMap);
        String str = jsonObject.toJSONString();

        networkRequest(OrderApi.orderSubmitAfterSale(mOrderId, "3", "", allProductId.substring(1), jsonObject.toJSONString()),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Logger.d(result.toString());
                        ToastUtils.showToastShort(mContext, "申请提交成功,请等待审核");
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        ToastUtils.showToastShort(mContext, "提交失败");
                    }
                });
    }

    private void getUploadToken() {
        networkRequest(UserApi.getUploadToken(), new SimpleFastJsonCallback<String>(String.class, null) {
            @Override
            public void onSuccess(String url, String result) {
                JSONObject jsonObject = JSON.parseObject(result);
                uploadToken = jsonObject.getString("uploadToken");
                Logger.d(uploadToken);
                uploadFile();
            }
        });
    }

    private void uploadFile() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UploadApi.uploadFile(uploadToken, sha1, uploadFile, new UploadFileTask.UploadCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        Logger.d(result.toJSONString());
                    }
                });
            }
        });
        thread.start();
    }

}
