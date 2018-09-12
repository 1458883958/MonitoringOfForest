package com.wdl.factory.data.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wdl.factory.Factory;
import com.wdl.factory.R;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.LoginModel;
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

            }

            @Override
            protected void succeed(List<Notice> data) {
                Factory.getNoticeCenter().dispatch(data.toArray(new Notice[0]));
            }
        });
    }

    /**
     * 从网络拉取
     *
     * @param userx User
     * @return User
     */
    public static UserDb searchFromNet(User userx) {
        RemoteService service = Network.remoteService();
        try {
            Response<RspModel<User>> response = service.getUserInfo(userx).execute();
            User user = response.body().getData();
            LogUtils.e("searchFromNet"+user.toString());
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
            User user = new User();
            user.setuId(id);
            return searchFromNet(user);
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
        User user = new User();
        user.setuId(id);
        UserDb db = searchFromNet(user);
        if (db==null){
            return searchFromLocal(id);
        }
        return db;
    }

    /**
     * @param query 查询的key
     * @param callback 回调
     * @return Call 用来判断上次的请求是否完成等
     */
    public static Call search(LoginModel query, final DataSource.Callback<List<User>> callback){
        RemoteService service = Network.remoteService();
        final Call<RspModel<List<User>>> call = service.search(query);
        call.enqueue(new CallbackImpl<List<User>>() {
            @Override
            protected void failed(String msg) {
                if (callback!=null)
                    callback.onNotAvailable(R.string.data_net_error);
            }

            @Override
            protected void succeed(List<User> data) {
                if (callback!=null)
                    callback.onLoaded(data);
            }
        });
        return call;
    }
}
