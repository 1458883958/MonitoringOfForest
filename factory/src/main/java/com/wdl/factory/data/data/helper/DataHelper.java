package com.wdl.factory.data.data.helper;

import com.wdl.factory.Factory;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.PageInfo;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.api.pi.ImagePage;
import com.wdl.factory.model.card.Image;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;
import com.wdl.utils.LogUtils;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public static void select(ImagePage pi) {
        RemoteService service = Network.remoteService();
        Call<RspModel<PageInfo<Image>>> call = service.getPic(pi);
        call.enqueue(new Callback<RspModel<PageInfo<Image>>>() {
            @Override
            public void onResponse(Call<RspModel<PageInfo<Image>>> call, Response<RspModel<PageInfo<Image>>> response) {

                RspModel<PageInfo<Image>> body = response.body();
                if (Objects.requireNonNull(body).getStatus()==200){
                    PageInfo<Image> pageInfo = body.getData();
                    List<Image> data = pageInfo.getList();
                    Factory.getDataCenter().dispatch(data.toArray(new Image[0]));
                }else if(body.getStatus()==500){
                    LogUtils.e(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<RspModel<PageInfo<Image>>> call, Throwable t) {

            }
        });
    }
}
