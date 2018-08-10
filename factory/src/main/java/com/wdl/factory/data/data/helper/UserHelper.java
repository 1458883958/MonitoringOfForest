package com.wdl.factory.data.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wdl.factory.Factory;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.Notice;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.model.db.UserDb_Table;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;
import com.wdl.utils.LogUtils;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/6 15:29
 * 描述：    用户工具类
 */
@SuppressWarnings("unused")
public class UserHelper {

    /**
     * 获取所有公告
     */
    public static void notice() {
        RemoteService service = Network.remoteService();
        final Call<RspModel<List<Notice>>> call = service.notice();
        call.enqueue(new CallbackImpl<List<Notice>>() {
            @Override
            protected void failed(String msg) {
                LogUtils.e("0x0x0x0x0x0xx0");
            }

            @Override
            protected void succeed(List<Notice> data) {
                for (Notice datum : data) {
                    LogUtils.e(datum.toString());
                }

            }
        });
    }

    /**
     * 从网络拉取
     *
     * @param id userId
     * @return User
     */
    public static UserDb searchFromNet(int id) {
        RemoteService service = Network.remoteService();
        try {
            Response<RspModel<User>> response = service.getUserInfo(id).execute();
            User user = response.body().getData();
            UserDb db = user.build();
            Factory.getUserCenter().dispatch(user);
            return db;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从本地拉取
     *
     * @param id userId
     * @return User
     */
    public static UserDb searchFromLocal(int id) {
        return SQLite.select()
                    .from(UserDb.class)
                    .where(UserDb_Table.id.eq(id))
                    .querySingle();
    }

    /**
     * 优先从本地拉取
     *
     * @param id userId
     * @return User
     */
    public static UserDb findFistOfLocal(int id) {
        UserDb db = searchFromLocal(id);
        if (db==null){
            return searchFromNet(id);
        }
        return db;
    }

    /**
     * 优先从网络拉取
     *
     * @param id userId
     * @return User
     */
    public static UserDb findFistOfNet(int id) {
        UserDb db = searchFromNet(id);
        if (db==null){
            return searchFromLocal(id);
        }
        return db;
    }
}
