package com.wdl.monitoringofforest.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.wdl.factory.data.data.helper.AccountHelper;
import com.wdl.factory.persistence.Account;
import com.wdl.monitoringofforest.broadcast.AlarmReceiver;
import com.wdl.utils.LogUtils;

import java.math.BigDecimal;

/**
 * 每隔一个月进行一次请求token
 */
public class LongRunService extends Service {
    public LongRunService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO 网络请求
                LogUtils.e("BroadcastReceiver : 网络请求");
                getBaiDuToken();
            }
        }).start();
        //一个月的毫秒数
        int anMonth = 1000 * 60 * 60 * 24;
        //int anMonth = 1000;
        BigDecimal decimal = new BigDecimal(anMonth);
        decimal = decimal.multiply(new BigDecimal(30));

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        BigDecimal decimal2 = decimal.add(new BigDecimal(SystemClock.elapsedRealtime()));
        long triggerAtTime = decimal2.longValue();
        //int anMonth = 1000 * 60 * 30;
        //long triggerAtTime = SystemClock.elapsedRealtime() + anMonth;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        assert manager != null;
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 获取百度植物识别的token
     */
    public void getBaiDuToken() {
        AccountHelper.getToken();
    }
}
