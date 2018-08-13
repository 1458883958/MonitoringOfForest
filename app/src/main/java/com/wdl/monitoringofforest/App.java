package com.wdl.monitoringofforest;


import com.igexin.sdk.PushManager;
import com.mob.MobSDK;
import com.wdl.common.app.Application;
import com.wdl.factory.Factory;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.monitoringofforest
 * 创建者：   wdl
 * 创建时间： 2018/8/1 9:42
 * 描述：    application
 */
@SuppressWarnings("unused")
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Factory
        Factory.setUp();
        //初始化个推
        PushManager.getInstance().initialize(this);
        MobSDK.init(this);

    }
}
