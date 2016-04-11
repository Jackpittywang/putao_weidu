package com.putao.wd.pt_companion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.image.ImageDraweeView;

import org.w3c.dom.Text;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/9.
 */
public class OfficialAccountsActivity extends PTWDActivity {

    @Bind(R.id.iv_icon)
    ImageDraweeView iv_icon;
    @Bind(R.id.tv_official_title)
    TextView tv_official_title;
    @Bind(R.id.tv_recommend)
    TextView tv_recommend;
    @Bind(R.id.btn_cancel_associate)
    Button btn_cancel_associate;


//    @Bind(R.id.no_cancel)
//    TextView no_cancel;
//    @Bind(R.id.cancel_associate)
//    TextView cancel_associate;


    private View view_custom;
    private AlertDialog mDialog = null;
    private AlertDialog.Builder builder = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_officialaccounts;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        addListener();
    }

    private void addListener() {
        btn_cancel_associate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        builder = new AlertDialog.Builder(this);

        final LayoutInflater inflater = OfficialAccountsActivity.this.getLayoutInflater();
        view_custom = inflater.inflate(R.layout.activity_officialaccounts_dialog, null, false);

        builder.setView(view_custom);
        builder.setCancelable(false);

        view_custom.findViewById(R.id.no_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        view_custom.findViewById(R.id.cancel_associate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        builder.show();

    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }
}
