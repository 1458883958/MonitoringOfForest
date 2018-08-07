package com.wdl.common.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Window;


/**
 * 项目名：  IMTalker
 * 包名：    com.example.common.app.tools
 * 创建者：   wdl
 * 创建时间： 2018/5/21 16:32
 * 描述：    获取屏幕宽高 状态栏高度的工具类
 */

public class UiTool {
    private static int STATUS_BAR_HEIGHT = -1;

    /**
     * 获取状态栏高度
     *
     * @param activity activity
     * @return STATUS_BAR_HEIGHT
     */
    public static int getStatusBarHeight(Activity activity) {
        //如果未计算过并且版本小于4.4
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && STATUS_BAR_HEIGHT == -1) {
            try {
                final Resources res = activity.getResources();
                // 尝试获取status_bar_height这个属性的Id对应的资源int值
                int resId = res
                        .getIdentifier("status_bar_height", "dimen", "android");
                if (resId <= 0) {
                    @SuppressLint("PrivateApi")
                    Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                    Object object = clazz.newInstance();
                    resId = Integer.parseInt(clazz.getField("status_bar_height")
                            .get(object).toString());

                }
                //如果拿到了,直接获取
                if (resId > 0) {
                    STATUS_BAR_HEIGHT = res.getDimensionPixelSize(resId);
                }
                //未拿到,通过window获取
                if (resId <= 0) {
                    Rect rect = new Rect();
                    Window window = activity.getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(rect);
                    STATUS_BAR_HEIGHT = rect.top;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return STATUS_BAR_HEIGHT;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity activity
     * @return displayMetrics.heightPixels
     */
    public static int getSreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity activity
     * @return displayMetrics.widthPixels
     */
    public static int getSreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
}
