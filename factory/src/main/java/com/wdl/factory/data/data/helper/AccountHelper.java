package com.wdl.factory.data.data.helper;

import com.wdl.factory.Factory;
import com.wdl.factory.R;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.LoginModel;
import com.wdl.factory.model.api.account.RegisterModel;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.db.User;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;

import factory.data.DataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
                    //callback.onNotAvailable();
                }
            }

            @Override
            protected void succeed(User user) {
                if (callback!=null) {
                    callback.onLoaded(user);
                }
            }
        });
        //LogUtils.e(model.toString());
//        call.enqueue(new Callback<RspModel<User>>(callback) {
//            @Override
//            public void onResponse(Call<RspModel<User>> call, Response<RspModel<User>> response) {
//                RspModel<User> rspModel = response.body();
//                if (rspModel == null) {
//                    LogUtils.e("rsp为空");
//                    return;
//                } else {
//                    LogUtils.e(rspModel.toString());
//                    if (rspModel.getStatus() == 200) {
//                        User user = rspModel.getData();
//                        LogUtils.e("//" + user.toString());
//                        if (callback != null)
//                            callback.onLoaded(user);
//                    } else Factory.decodeRsp(rspModel, callback);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RspModel<User>> call, Throwable t) {
//                LogUtils.e(t.getMessage());
//                if (callback != null)
//                    callback.onNotAvailable(R.string.data_net_error);
//            }
//        });
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
        call.enqueue(new CallbackImpl<String>() {
            @Override
            protected void failed(String msg) {
                if (callback!=null) {
                    //callback.onNotAvailable()
                }
            }

            @Override
            protected void succeed(String msg) {
                if (callback!=null) {
                    callback.onLoaded(msg);
                }
            }
        });


    }

    /**
     * 获取验证码
     *
     * @param phone String
     */
    public static void getCode(String phone,final DataSource.Callback<String> callback) {
        RemoteService service = Network.remoteService();
        Call<RspModel<String>> call = service.getSmsCode(phone);
        call.enqueue(new Callback<RspModel<String>>() {
            @Override
            public void onResponse(Call<RspModel<String>> call, Response<RspModel<String>> response) {
                RspModel<String> rspModel = response.body();
                if (rspModel!=null) {
                    if(rspModel.getStatus() == 200) {
                        if (callback != null)
                            callback.onLoaded(rspModel.getData());
                    }
                }else {
                    LogUtils.e("rsp:"+"空");
                }

            }
            @Override
            public void onFailure(Call<RspModel<String>> call, Throwable t) {
                LogUtils.e(t.getMessage());
            }
        });
    }
}
