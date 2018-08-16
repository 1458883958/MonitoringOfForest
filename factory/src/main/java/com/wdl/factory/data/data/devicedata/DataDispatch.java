package com.wdl.factory.data.data.devicedata;

import android.text.TextUtils;

import com.wdl.factory.data.data.feedback.FeedbackCenter;
import com.wdl.factory.data.data.feedback.FeedbackDispatch;
import com.wdl.factory.data.data.helper.DbHelper;
import com.wdl.factory.model.card.Image;
import com.wdl.factory.model.db.ImageDb;

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
public class DataDispatch implements DataCenter {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private static DataCenter instance;

    public static DataCenter getDataCenter() {
        if (instance == null) {
            synchronized (FeedbackDispatch.class) {
                if (instance == null) {
                    instance = new DataDispatch();
                }
            }
        }
        return instance;
    }

    @Override
    public void dispatch(Image... images) {
        if (images == null || images.length == 0) return;
        executor.execute(new DataHandler(images));
    }

    private class DataHandler implements Runnable {
        private Image[] images;

        public DataHandler(Image[] images) {
            this.images = images;
        }

        @Override
        public void run() {
            List<ImageDb> list = new ArrayList<>();
            for (Image image : images) {
                if (image==null|| TextUtils.isEmpty(""+image.getiId()))continue;
                //添加
                list.add(image.build());
            }
            //进行数据库存储并分发通知
            DbHelper.save(ImageDb.class,list.toArray(new ImageDb[0]));
        }
    }
}
