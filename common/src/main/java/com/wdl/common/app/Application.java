package com.wdl.common.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.io.File;
import java.util.Stack;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.app
 * 创建者：   wdl
 * 创建时间： 2018/8/1 18:09
 * 描述：    全局的application
 */
@SuppressWarnings({"unused", "unchecked"})
public class Application extends android.app.Application {

    private static Application instance;
    private Stack<Activity> activities = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activities.push(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activities.remove(activity);
                activity.finish();
            }
        });
    }

    public void exit(){
        for (Activity activity : activities) {
            activities.remove(activity);
            activity.finish();
        }
        showAccount(this);

    }
    protected void showAccount(Context context){

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
        File dir = new File(getCacheDirFile(), "portrait");
        //创建所有对应的目录
        dir.mkdirs();
        //清空旧缓存文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                file.delete();
            }
        }
        File path = new File(dir, SystemClock.uptimeMillis() + ".jpg");
        return path.getAbsoluteFile();
    }

    /**
     * 获取录音的本地文件
     *
     * @param isTmp 是否是缓存文件
     *              true 每次返回的地址一致
     *              false 反之
     * @return path.getAbsoluteFile()
     */
    public static File getAudioTmpFile(boolean isTmp) {
        //获取audio的缓存地址
        File dir = new File(getCacheDirFile(), "audio");
        dir.mkdirs();

        //删除旧的缓存文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                file.delete();
            }
        }

        File path = new File(getCacheDirFile(), isTmp ? "tmp.mp3" : SystemClock.uptimeMillis() + ".mp3");
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
