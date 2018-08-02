package com.wdl.common.common.app;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.io.File;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.common.app
 * 创建者：   wdl
 * 创建时间： 2018/8/1 18:09
 * 描述：    全局的application
 */
@SuppressWarnings({"unused","unchecked"})
public class Application extends android.app.Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 获取单例
     *
     * @return Application
     */
    public static Application getInstance() {
        return instance;
    }

    /**
     * 获取app的缓存文件夹地址
     *
     * @return File app的缓存文件夹地址
     */
    @SuppressLint("NewApi")
    public static File getCacheDirFile() {
        return instance.getCodeCacheDir();
    }

    /**
     * 获取头像缓存地址
     *
     * @return File
     */
    public static File getPortraitTmpFile() {
        //获取头像缓存地址
        File dir = new File(getCacheDirFile(),"portrait");
        //创建所有对应的目录
        dir.mkdirs();
        //清空旧缓存文件
        File[] files = dir.listFiles();
        if (files!=null&&files.length>0) {
            for (File file : files) {
                file.delete();
            }
        }
        File path = new File(dir, SystemClock.uptimeMillis()+".jpg");
        return path.getAbsoluteFile();
    }

    /**
     * 全局Toast提示
     *
     * @param message 提示的内容
     */
    public static void showToast(final String message) {
        //只能在主线程
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Toast.makeText(instance, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 全局Toast提示
     *
     * @param resId 资源id
     */
    public static void showToast(@StringRes int resId) {
        //只能在主线程
        showToast(instance.getString(resId));
    }
}
