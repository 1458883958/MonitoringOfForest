package com.wdl.factory.data.data.pi;

import android.text.TextUtils;

import com.wdl.factory.data.data.helper.DbHelper;
import com.wdl.factory.model.card.Pi;
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
public class PiDispatcher implements PiCenter {
    //单例
    private static PiCenter instance;
    //单线程池,用来处理设备列表
    private final Executor executor = Executors.newSingleThreadExecutor();

    /**
     * 获取单例
     *
     * @return PiCenter
     */
    public static PiCenter getPiCenter() {
        //如果单前实例为空,避免重复创建
        if (instance == null) {
            //同步操作
            synchronized (PiDispatcher.class) {
                //内存中，此实例为空,创建
                if (instance == null)
                    instance = new PiDispatcher();
            }
        }
        return instance;
    }

    @Override
    public void dispatch(Pi... pis) {
        if (pis == null || pis.length == 0) return;
        //丢到单线程池中
        executor.execute(new PiHandler(pis));
    }

    @Override
    public void dispatch(PiDb... piDbs) {
        if (piDbs == null || piDbs.length == 0) return;
        //丢到单线程池中
        executor.execute(new PiDbHandler(piDbs));
    }

    private class PiHandler implements Runnable {
        private final Pi[] pis;

        public PiHandler(Pi[] pis) {
            this.pis = pis;
        }

        @Override
        public void run() {
            //单线程池调度时触发
            List<PiDb> dbs = new ArrayList<>();
            for (Pi pi : pis) {
                if (pi==null|| TextUtils.isEmpty(""+pi.getpId()))continue;
                //添加
                PiDb db = pi.build();
                db.setUserId(Account.getUserId());
                dbs.add(db);

            }
            //进行数据库存储并分发通知
            DbHelper.save(PiDb.class,dbs.toArray(new PiDb[0]));
        }
    }

    private class PiDbHandler implements Runnable {
        private final PiDb[] pis;

        public PiDbHandler(PiDb[] pis) {
            this.pis = pis;
        }

        @Override
        public void run() {
            DbHelper.update(PiDb.class,pis);
        }
    }
}
