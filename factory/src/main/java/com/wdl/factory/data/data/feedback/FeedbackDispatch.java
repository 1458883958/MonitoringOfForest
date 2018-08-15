package com.wdl.factory.data.data.feedback;

import android.text.TextUtils;

import com.wdl.factory.data.data.helper.DbHelper;
import com.wdl.factory.model.card.Feedback;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.db.FeedbackDb;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.persistence.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.feedback
 * 创建者：   wdl
 * 创建时间： 2018/8/14 12:34
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class FeedbackDispatch implements FeedbackCenter {
    private static FeedbackCenter instance;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public static FeedbackCenter getFeedbackCenter() {
        if (instance == null) {
            synchronized (FeedbackDispatch.class) {
                if (instance == null) {
                    instance = new FeedbackDispatch();
                }
            }
        }
        return instance;
    }

    @Override
    public void dispatch(Feedback... feeds) {
        if (feeds==null||feeds.length==0)return;
        executor.execute(new FeedbackHandler(feeds));
    }

    private class FeedbackHandler implements Runnable{

        private final Feedback[] feeds;

        public FeedbackHandler(Feedback[] feeds) {
            this.feeds = feeds;
        }

        @Override
        public void run() {
            List<FeedbackDb> list = new ArrayList<>();
            for (Feedback feedback : feeds) {
                if (feedback==null|| TextUtils.isEmpty(""+feedback.getfId()))continue;
                //添加
                list.add(feedback.build());
            }
            //进行数据库存储并分发通知
            DbHelper.save(FeedbackDb.class,list.toArray(new FeedbackDb[0]));
        }
    }
}
