package com.wdl.factory.data.data.helper;

import com.wdl.factory.R;
import com.wdl.factory.model.api.account.LoginModel;
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
    public static void login(LoginModel model, final DataSource.Callback<User> callback) {
        RemoteService service = Network.remoteService();
        Call<RspModel<User>> call = service.login(model);
        call.enqueue(new Callback<RspModel<User>>() {
            @Override
            public void onResponse(Call<RspModel<User>> call, Response<RspModel<User>> response) {
                RspModel<User> rspModel = response.body();
                if (rspModel.getStatus()==200){
                    User user = rspModel.getData();
                    LogUtils.e(user.toString());
                    if (callback!=null)
                        callback.onLoaded(user);
                }
            }

            @Override
            public void onFailure(Call<RspModel<User>> call, Throwable t) {
                if (callback!=null)
                    callback.onNotAvailable(R.string.data_net_error);
            }
        });
    }

}
