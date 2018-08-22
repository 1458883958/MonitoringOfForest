package com.wdl.factory.data.data.helper;


import android.os.SystemClock;

import com.wdl.common.common.Common;
import com.wdl.factory.Factory;
import com.wdl.factory.R;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.LoginModel;
import com.wdl.factory.model.api.account.RegisterModel;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.AccessToken;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.data.DataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.wdl.factory.presenter.account.LoginPresenter;
import com.wdl.factory.presenter.account.QQPresenter;
import com.wdl.utils.LogUtils;

import java.util.HashMap;

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
                LogUtils.e(msg);
                if (callback != null) {
                    Factory.decodeRspCode(msg, callback);
                }
            }

            @Override
            protected void succeed(User user) {
                //保存数据库并通知界面
                DbHelper.save(UserDb.class, user.build());
                //同步至XML文件
                Account.saveUserInfo(user);
                if (callback != null) {
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
        LogUtils.e("c" + model.toString());
        call.enqueue(new CallbackImpl<String>() {
            @Override
            protected void failed(String msg) {
                if (callback != null) {
                    Factory.decodeRspCode(msg, callback);
                }
            }

            @Override
            protected void succeed(String msg) {
                if (callback != null) {
                    LogUtils.e("succeed:" + msg);
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
    public static void getCode(User user, final DataSource.Callback<String> callback) {
        RemoteService service = Network.remoteService();
        final Call<RspModel<String>> call = service.getSmsCode(user);
        call.enqueue(new CallbackImpl<String>() {
            @Override
            protected void failed(String msg) {
                if (callback != null) {
                    Factory.decodeRspCode(msg, callback);
                }
            }

            @Override
            protected void succeed(String data) {
                if (callback != null)
                    callback.onLoaded(data);
            }
        });
    }

    /**
     * QQ登录
     *
     * @param user     User
     * @param callback callback
     */
    public static void qqLogin(User user, final DataSource.Callback<User> callback) {
        RemoteService service = Network.remoteService();
        final Call<RspModel<User>> call = service.loginQQ(user);
        call.enqueue(new CallbackImpl<User>() {
            @Override
            protected void failed(String msg) {
                if (callback!=null)
                    callback.onNotAvailable(R.string.data_account_unbind_qq);
            }

            @Override
            protected void succeed(User data) {
                //保存数据库并通知界面
                DbHelper.save(UserDb.class, data.build());
                //同步至XML文件
                Account.saveUserInfo(data);
                if (callback != null) {
                    callback.onLoaded(data);
                }
            }
        });
    }

    public static void qqRegister(RegisterModel model, final DataSource.Callback<String> callback) {
        RemoteService service = Network.remoteService();
        Call<RspModel<User>> call = service.regQQ(model);
        LogUtils.e("c" + model.toString());
        call.enqueue(new CallbackImpl<User>() {
            @Override
            protected void failed(String msg) {
                if (callback != null) {
                    LogUtils.e("default:" + msg);
                    Factory.decodeRspCode(msg, callback);
                }
            }

            @Override
            protected void succeed(User msg) {
                if (callback != null) {
                    LogUtils.e("succeed:" + msg);
                    callback.onLoaded(msg.getuTelephone());
                }
            }
        });

    }

    public static void qqBind(RegisterModel model, final DataSource.Callback<String> callback) {
        RemoteService service = Network.remoteService();
        Call<RspModel<User>> call = service.bindQQ(model);
        LogUtils.e("c" + model.toString());
        call.enqueue(new CallbackImpl<User>() {

            @Override
            protected void failed(String msg) {
                if (callback != null) {
                    LogUtils.e("default:" + msg);
                    Factory.decodeRspCode(msg, callback);
                }
            }

            @Override
            protected void succeed(User data) {
                if (callback != null) {
                    callback.onLoaded(data.getuTelephone());
                }
            }
        });

    }

    public static void update(final int type, User user, final DataSource.Callback<User> callback) {
        RemoteService service = Network.remoteService();
        final Call<RspModel<User>> call = service.update(user);
        call.enqueue(new CallbackImpl<User>() {
            @Override
            protected void failed(String msg) {
                if (callback!=null)
                    Factory.decodeRspCode(msg,callback);
            }

            @Override
            protected void succeed(User data) {
                //保存数据库并通知界面
                UserDb db = Account.getUserDb();
                if (db==null)return;
                if (type==1)db.setAlias(data.getuFullname());
                if (type==2)db.setMail(data.getuEmail());
                if (type==4)db.setImage(data.getuImagepath());
                if (type==5)db.setAddress(data.getuIpaddress());
                DbHelper.update(UserDb.class, db);
                if (callback!=null){
                    callback.onLoaded(data);
                }
            }
        });
    }

    public static void getToken(){
        RemoteService service = Network.remoteService2();
        HashMap<String,String> map = new HashMap<>();
        map.put("grant_type","client_credentials");
        map.put("client_id", Common.Constance.CLIENT_ID);
        map.put("client_secret", Common.Constance.CLIENT_SECRET);
        Call<AccessToken> call = service.getToken(map);
        call.enqueue(new Callback<AccessToken>() {

            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                AccessToken accessToken = response.body();
                if (accessToken!=null){
                    String token = accessToken.getAccess_token();
                    LogUtils.e("token:"+token);
                    Account.setToken(token);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                return;
            }
        });
    }
}
