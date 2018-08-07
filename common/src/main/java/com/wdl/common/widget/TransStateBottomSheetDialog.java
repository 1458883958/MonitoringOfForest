package com.wdl.common.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.ViewGroup;
import android.view.Window;

import com.wdl.common.tools.UiTool;

import java.util.Objects;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.widget
 * 创建者：   wdl
 * 创建时间： 2018/8/1 18:52
 * 描述：    TODO
 */
//为了解决顶部状态栏变黑而写的TransStatusBottomSheetDialog
@SuppressWarnings("unused")
public class TransStateBottomSheetDialog extends BottomSheetDialog {

    public TransStateBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public TransStateBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected TransStateBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Window window = getWindow();
        if (window == null) return;

        //屏幕高度
        int screenH = UiTool.getSreenHeight(Objects.requireNonNull(getOwnerActivity()));
        //状态栏高度
        int statusH = UiTool.getStatusBarHeight(getOwnerActivity());

        int dialogH = screenH - statusH;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                dialogH <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogH);
    }
}