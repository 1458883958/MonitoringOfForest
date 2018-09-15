package com.wdl.factory.data.data.helper;

import com.wdl.factory.Factory;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.Message;
import com.wdl.factory.model.db.MessageDb;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/9/15 18:54
 * 描述：    TODO
 */
public class MessageHelper {

    /**
     * 发送消息
     *
     * @param message Message
     */
    public static void pushMsg(final Message message) {
        Factory.runOnAsy(new Runnable() {
            @Override
            public void run() {
                RemoteService service = Network.remoteService();
                Call<RspModel> call = service.pushMessage(message);
                call.enqueue(new Callback<RspModel>() {
                    @Override
                    public void onResponse(Call<RspModel> call, Response<RspModel> response) {
                        RspModel rspModel = response.body();
                        if (rspModel != null) {
                            if (rspModel.getStatus() == 200) {
                                Factory.getMessageCenter().dispatch(message);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel> call, Throwable t) {

                    }
                });
            }
        });

    }
}
