package com.wdl.common.widget;

import android.app.ProgressDialog;
import android.content.Context;

import com.wdl.common.R;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.widget
 * 创建者：   wdl
 * 创建时间： 2018/8/19 15:21
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class Progress extends ProgressDialog{
    public Progress(Context context) {
        super(context);
        init();
    }

    public Progress(Context context, int theme) {
        super(context, theme);
        init();
    }

    private void init() {
        this.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.setCancelable(false);// 设置是否可以通过点击Back键取消
        this.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        this.setTitle(R.string.title_dealing);
        this.setMax(100);
        this.show();
    }
}
