package com.wdl.factory.data.data.helper;

import com.wdl.factory.data.DataSource;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.Version;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;

import retrofit2.Call;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/9/4 20:41
 * 描述：    TODO
 */
public class VersionHelper {

    public static void check(final DataSource.Callback<Version> callback){
        RemoteService service = Network.remoteService();
        Call<RspModel<Version>> call = service.getVersion();
        call.enqueue(new CallbackImpl<Version>() {
            @Override
            protected void failed(String msg) {
//                if (callback!=null)
//                    callback.onNotAvailable();
            }

            @Override
            protected void succeed(Version data) {
                if (callback!=null)
                    callback.onLoaded(data);
            }
        });
    }

}
