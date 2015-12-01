package com.sunnybear.library.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunnybear.library.controller.handler.WeakHandler;

/**
 * 加载对话框
 * Created by guchenkai on 2015/12/1.
 */
public class LoadingDialog extends Dialog {
    private static final int CHANGE_TITLE_WHAT = 1;
    private static final int CHNAGE_TITLE_DELAYMILLIS = 300;
    private static final int MAX_SUFFIX_NUMBER = 3;
    private static final char SUFFIX = '.';

    private ImageView iv_route;
    private TextView detail_tv;
    private TextView tv_point;
    private RotateAnimation mAnim;

    private WeakHandler mHandler = new WeakHandler(new Handler.Callback() {
        private int num = 0;

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_TITLE_WHAT:
                    StringBuffer sb = new StringBuffer();
                    if (num >= MAX_SUFFIX_NUMBER) num = 0;
                    num++;
                    for (int i = 0; i < num; i++) {
                        sb.append(SUFFIX);
                    }
                    tv_point.setText(sb.toString());
                    if (isShowing())
                        mHandler.sendEmptyMessageDelayed(CHANGE_TITLE_WHAT, CHNAGE_TITLE_DELAYMILLIS);
                    else
                        num = 0;
                    break;
            }
            return true;
        }
    });

    public LoadingDialog(Context context) {
        super(context);
    }
}
