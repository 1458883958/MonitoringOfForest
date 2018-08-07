package com.wdl.factory.data.data.helper;

import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.Notice;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;
import com.wdl.utils.LogUtils;

import java.util.List;

import retrofit2.Call;

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
    public static void notice(){
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
}
