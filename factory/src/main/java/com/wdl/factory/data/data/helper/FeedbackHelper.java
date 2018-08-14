package com.wdl.factory.data.data.helper;

import com.wdl.factory.Factory;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.Feedback;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;

import retrofit2.Call;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/14 12:30
 * 描述：    反馈工具类
 */
public class FeedbackHelper {

    /**
     * 添加反馈
     *
     * @param feedback Feedback
     */
    public static void insert(Feedback feedback, final DataSource.SucceedCallback<Feedback> callback) {
        RemoteService service = Network.remoteService();
        final Call<RspModel<Feedback>> call = service.insertFeedback(feedback);
        call.enqueue(new CallbackImpl<Feedback>() {
            @Override
            protected void failed(String msg) {
            }

            @Override
            protected void succeed(Feedback data) {
                Factory.getFeedbackCenter().dispatch(data);
                if (callback!=null)
                    callback.onLoaded(data);
            }
        });
    }
}
