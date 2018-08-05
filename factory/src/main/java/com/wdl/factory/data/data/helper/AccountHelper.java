package com.wdl.factory.data.data.helper;

import com.wdl.factory.Factory;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.LoginModel;
import com.wdl.factory.model.api.account.RegisterModel;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;
import com.wdl.factory.persistence.Account;

import factory.data.DataSource;
import retrofit2.Call;
import utils.LogUtils;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/3 15:06
 * 描述：    账户的工具类
 */
@SuppressWarnings("unused")
public class AccountHelper {
    /**
     * 登陆
     *
     * @param model    LoginModel
     * @param callback Callback
     */
    public static void login(final LoginModel model, final DataSource.Callback<User> callback) {
        RemoteService service = Network.remoteService();
        Call<RspModel<User>> call = service.login(model);
        call.enqueue(new CallbackImpl<User>() {
            @Override
            protected void failed(String msg) {
                if (callback!=null) {
                    Factory.decodeRspCode(msg,callback);
                }
            }

            @Override
            protected void succeed(User user) {
                //保存数据库
                UserDb userDb = new UserDb().getUserDb(user);
                LogUtils.e(userDb.toString());
                userDb.save();
                //同步至XML文件
                Account.saveUserInfo(user);
                if (callback!=null) {
                    callback.onLoaded(user);
                }
            }
        });
    }

    /**
     * 注册
     *
     * @param model    RegisterModel
     * @param callback callback
     */
    public static void register(RegisterModel model, final DataSource.Callback<String> callback) {
        RemoteService service = Network.remoteService();
        Call<RspModel<String>> call = service.register(model);
        LogUtils.e("c"+model.toString());
        call.enqueue(new CallbackImpl<String>() {
            @Override
            protected void failed(String msg) {
                if (callback!=null) {
                    Factory.decodeRspCode(msg,callback);
                }
            }

            @Override
            protected void succeed(String msg) {
                if (callback!=null) {
                    LogUtils.e("succeed:"+msg);
                    callback.onLoaded(msg);
                }
            }
        });


    }

    /**
     * 获取验证码
     *
     * @param user User
     */
    public static void getCode(User user,final DataSource.Callback<String> callback) {
        RemoteService service = Network.remoteService();
        final Call<RspModel<String>> call = service.getSmsCode(user);
        call.enqueue(new CallbackImpl<String>() {
            @Override
            protected void failed(String msg) {
                if (callback!=null) {
                    Factory.decodeRspCode(msg,callback);
                }
            }

            @Override
            protected void succeed(String data) {
                if (callback!=null)
                    callback.onLoaded(data);
            }
        });
    }
}
