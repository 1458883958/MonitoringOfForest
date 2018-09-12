package com.wdl.factory.data.data.notice;

import android.text.TextUtils;

import com.wdl.factory.data.data.helper.DbHelper;
import com.wdl.factory.data.data.pi.PiCenter;
import com.wdl.factory.data.data.pi.PiDispatcher;
import com.wdl.factory.model.card.Notice;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.db.NoticeDb;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.persistence.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/7 11:33
 * 描述：    设备中心实现类
 */
@SuppressWarnings("unused")
public class NoticeDispatcher implements NoticeCenter {
    //单例
    private static NoticeCenter instance;
    //单线程池,用来处理设备列表
    private final Executor executor = Executors.newSingleThreadExecutor();

    /**
     * 获取单例
     *
     * @return PiCenter
     */
    public static NoticeCenter getNoticeCenter() {
        //如果单前实例为空,避免重复创建
        if (instance == null) {
            //同步操作
            synchronized (NoticeDispatcher.class) {
                //内存中，此实例为空,创建
                if (instance == null)
                    instance = new NoticeDispatcher();
            }
        }
        return instance;
    }

    @Override
    public void dispatch(Notice... notices) {
        if (notices == null || notices.length == 0) return;
        //丢到单线程池中
        executor.execute(new NoticeHandler(notices));
    }


    private class NoticeHandler implements Runnable {
        private final Notice[] notices;

        public NoticeHandler(Notice[] notices) {
            this.notices = notices;
        }

        @Override
        public void run() {
            //单线程池调度时触发
            List<NoticeDb> dbs = new ArrayList<>();
            for (Notice notice : notices) {
                if (notice==null|| TextUtils.isEmpty(""+notice.getnId()))continue;
                //添加
                NoticeDb db = notice.build();
                dbs.add(db);

            }
            //进行数据库存储并分发通知
            DbHelper.save(NoticeDb.class,dbs.toArray(new NoticeDb[0]));
        }
    }
}
