package com.sunnybear.library.view.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunnybear.library.R;

/**
 * loading对话框
 * Created by sunnybear on 2015/3/9.
 */
public class LoadingHUD extends Dialog {

    public static final int FADED_ROUND_SPINNER = 0x00;
    public static final int GEAR_SPINNER = 0x01;
    public static final int SIMPLE_ROUND_SPINNER = 0x02;

    private static LoadingHUD INSTANCE;
    private Context mContext;
    private View mView;
    private TextView tvMessage;

    private ImageView ivSuccess, ivFailure, ivProgressSpinner;
    private AnimationDrawable adProgressSpinner;

    public static LoadingHUD getINSTANCE(Context context) {
        if (INSTANCE == null)
            INSTANCE = new LoadingHUD(context);
        return INSTANCE;
    }

    public LoadingHUD(Context context) {
        super(context, R.style.DialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);

        mContext = context;
        mView = getLayoutInflater().inflate(R.layout.widget_loading_dialog_progress, null);
        tvMessage = (TextView) mView.findViewById(R.id.textview_message);
        ivSuccess = (ImageView) mView.findViewById(R.id.imageview_success);
        ivFailure = (ImageView) mView.findViewById(R.id.imageview_failure);
        ivProgressSpinner = (ImageView) mView.findViewById(R.id.imageview_progress_spinner);

        setSpinnerType(FADED_ROUND_SPINNER);
        setContentView(mView);
    }

    public void setSpinnerType(int spinnerType) {
        switch (spinnerType) {
            case FADED_ROUND_SPINNER:
                ivProgressSpinner.setImageResource(R.anim.round_spinner_fade);
                break;
            case GEAR_SPINNER:
                ivProgressSpinner.setImageResource(R.anim.gear_spinner);
                break;
            case SIMPLE_ROUND_SPINNER:
                ivProgressSpinner.setImageResource(R.anim.round_spinner);
                break;
            default:
                ivProgressSpinner.setImageResource(R.anim.round_spinner_fade);
                break;
        }
        adProgressSpinner = (AnimationDrawable) ivProgressSpinner.getDrawable();
    }

    public LoadingHUD setMessage(String message) {
        tvMessage.setText(message);
        return this;
    }

    @Override
    public void show() {
        if (!((Activity) mContext).isFinishing())
            super.show();
        else
            INSTANCE = null;
    }

    public void dismissWithSuccess() {
        dismissWithSuccess("Success", null);
    }

    public void dismissWithSuccess(String message, final OnDialogDismissListener onDialogDismissListener) {
        showSuccessImage();
        if (message != null)
            tvMessage.setText(message);
        else
            tvMessage.setText("");

        if (onDialogDismissListener != null)
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    onDialogDismissListener.onDialogDismiss();
                }
            });
        dismissHUD();
    }

    public void dismissWithFailure() {
        dismissWithFailure("Failure", null);
    }

    public void dismissWithFailure(String message, final OnDialogDismissListener onDialogDismissListener) {
        showFailureImage();
        if (message != null)
            tvMessage.setText(message);
        else
            tvMessage.setText("");

        if (onDialogDismissListener != null)
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    onDialogDismissListener.onDialogDismiss();
                }
            });
        dismissHUD();
    }

    protected void showSuccessImage() {
        ivProgressSpinner.setVisibility(View.GONE);
        ivSuccess.setVisibility(View.VISIBLE);
    }

    protected void showFailureImage() {
        ivProgressSpinner.setVisibility(View.GONE);
        ivFailure.setVisibility(View.VISIBLE);
    }

    protected void reset() {
        ivProgressSpinner.setVisibility(View.VISIBLE);
        ivFailure.setVisibility(View.GONE);
        ivSuccess.setVisibility(View.GONE);
        tvMessage.setText("Loading ...");
    }

    protected void dismissHUD() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
                reset();
            }
        }, 500);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        ivProgressSpinner.post(new Runnable() {
            @Override
            public void run() {
                adProgressSpinner.start();
            }
        });
    }

    /**
     * 关闭Dialog回调
     */
    public interface OnDialogDismissListener {

        void onDialogDismiss();
    }
}
