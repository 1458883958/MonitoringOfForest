package com.wdl.utils;

import android.util.Log;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    utils
 * 创建者：   wdl
 * 创建时间： 2018/8/1 11:12
 * 描述：    日志输出的简单包装
 */
@SuppressWarnings("unused")
public class LogUtils {
    //tag
    private static final String TAG = "wdl";
    //全局打印控制
    private static final boolean IS_PRINTF = true;

    public static void d(String text) {
        if (IS_PRINTF)
            Log.d(TAG, text);
    }

    public static void e(String text) {
        if (IS_PRINTF)
            Log.e(TAG, text);
    }

    public static void i(String text){
        if (IS_PRINTF)
            Log.i(TAG, text);
    }

    public static void w(String text){
        if (IS_PRINTF)
            Log.w(TAG, text);
    }

}
