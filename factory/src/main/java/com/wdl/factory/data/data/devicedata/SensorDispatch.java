package com.wdl.factory.data.data.devicedata;

import android.text.TextUtils;

import com.wdl.factory.data.data.feedback.FeedbackDispatch;
import com.wdl.factory.data.data.helper.DbHelper;
import com.wdl.factory.model.card.Image;
import com.wdl.factory.model.card.Sensor;
import com.wdl.factory.model.db.ImageDb;
import com.wdl.factory.model.db.SensorDb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.devicedata
 * 创建者：   wdl
 * 创建时间： 2018/8/16 15:47
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class SensorDispatch implements SensorCenter {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private static SensorCenter instance;

    public static SensorCenter getSensorCenter() {
        if (instance == null) {
            synchronized (SensorDispatch.class) {
                if (instance == null) {
                    instance = new SensorDispatch();
                }
            }
        }
        return instance;
    }

    @Override
    public void dispatch(Sensor... sensors) {
        if (sensors == null || sensors.length == 0) return;
        executor.execute(new SensorHandler(sensors));
    }

    private class SensorHandler implements Runnable {
        private Sensor[] sensors;

        public SensorHandler(Sensor[] sensors) {
            this.sensors = sensors;
        }

        @Override
        public void run() {
            List<SensorDb> list = new ArrayList<>();
            for (Sensor sensor : sensors) {
                if (sensor==null|| TextUtils.isEmpty(""+sensor.getdId()))continue;
                //添加
                list.add(sensor.build());
            }
            //进行数据库存储并分发通知
            DbHelper.save(SensorDb.class,list.toArray(new SensorDb[0]));
        }
    }
}
