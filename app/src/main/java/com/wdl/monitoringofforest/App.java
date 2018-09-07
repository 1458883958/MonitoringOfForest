package com.wdl.monitoringofforest;


import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.igexin.sdk.PushManager;
import com.mob.MobSDK;
import com.tencent.bugly.crashreport.CrashReport;
import com.wdl.common.app.Application;
import com.wdl.factory.Factory;
import com.wdl.monitoringofforest.service.PushService;

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
        PushManager.getInstance().initialize(this,
                PushService.class);
        MobSDK.init(this);
        //TTS语音听写
        SpeechUtility
                .createUtility(getApplicationContext(),
                        SpeechConstant.APPID + "=" + "5b75848b");

        CrashReport.initCrashReport(getApplicationContext(), "8ce40ccd28", true);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
