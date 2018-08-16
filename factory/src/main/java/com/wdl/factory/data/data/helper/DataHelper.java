package com.wdl.factory.data.data.helper;

import com.wdl.factory.Factory;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.Image;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;

import java.util.List;

import retrofit2.Call;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/16 15:43
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class DataHelper {

    /**
     * 查询图片数据
     *
     * @param pi Pi
     */
    public static void select(Pi pi) {
        RemoteService service = Network.remoteService();
        Call<RspModel<List<Image>>> call = service.getPic(pi);
        call.enqueue(new CallbackImpl<List<Image>>() {
            @Override
            protected void failed(String msg) {

            }

            @Override
            protected void succeed(List<Image> data) {
                Factory.getDataCenter().dispatch(data.toArray(new Image[0]));
            }
        });
    }
}
