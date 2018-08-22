package com.wdl.monitoringofforest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wdl.monitoringofforest.service.LongRunService;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.monitoringofforest.broadcast
 * 创建者：   wdl
 * 创建时间： 2018/8/22 10:53
 * 描述：    TODO
 */
public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LongRunService.class);
        context.startService(i);
    }
}
