package com.wdl.common.widget;

import android.app.Dialog;
import android.content.Context;

import com.wdl.common.R;

import java.util.Objects;


public class AppUpdateProgressDialog extends Dialog {

    private NumberProgressBar numberProgressBar;

    public AppUpdateProgressDialog(Context context) {
        super(context, R.style.Custom_Progress);
        initLayout();
    }

    public AppUpdateProgressDialog(Context context, int theme) {
        super(context, R.style.Custom_Progress);
        initLayout();
    }

    private void initLayout() {
        this.setContentView(R.layout.update_progress_layout);
        Objects.requireNonNull(getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        numberProgressBar = findViewById(R.id.number_progress);
        this.setCanceledOnTouchOutside(false);//点击dialog背景部分不消失
    }

    public void setProgress(int mProgress) {
        numberProgressBar.setProgress(mProgress);
    }
}
