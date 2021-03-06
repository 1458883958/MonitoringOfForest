package com.wdl.factory.data.data.user;

import android.text.TextUtils;

import com.wdl.factory.data.data.helper.DbHelper;
import com.wdl.factory.data.data.pi.PiCenter;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.model.db.UserDb;
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
public class UserDispatcher implements UserCenter {
    //单例
    private static UserCenter instance;
    //单线程池,用来处理设备列表
    private final Executor executor = Executors.newSingleThreadExecutor();

    /**
     * 获取单例
     *
     * @return UserCenter
     */
    public static UserCenter getUserCenter() {
        //如果单前实例为空,避免重复创建
        if (instance == null) {
            //同步操作
            synchronized (UserDispatcher.class) {
                //内存中，此实例为空,创建
                if (instance == null)
                    instance = new UserDispatcher();
            }
        }
        return instance;
    }

    @Override
    public void dispatch(User... users) {
        if (users == null || users.length == 0) return;
        //丢到单线程池中
        executor.execute(new UserHandler(users));
    }


    private class UserHandler implements Runnable {
        private final User[] users;

        public UserHandler(User[] users) {
            this.users = users;
        }

        @Override
        public void run() {
            //单线程池调度时触发
            List<UserDb> dbs = new ArrayList<>();
            for (User user : users) {
                if (user==null|| TextUtils.isEmpty(""+user.getuCid()))continue;
                //添加
                UserDb db = user.build();
                dbs.add(db);

            }
            //进行数据库存储并分发通知
            DbHelper.save(UserDb.class,dbs.toArray(new UserDb[0]));
        }
    }

}
