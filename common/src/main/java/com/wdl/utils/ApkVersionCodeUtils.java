package com.wdl.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.utils
 * 创建者：   wdl
 * 创建时间： 2018/8/19 16:23
 * 描述：    apk版本相关工具类
 */
@SuppressWarnings("unused")
public class ApkVersionCodeUtils {

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            //获取软件版本号 对应AndroidManifest.xml下的versionCode
            versionCode = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),0)
                    .versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本名
     *
     * @param context 上下文
     * @return 版本名
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
