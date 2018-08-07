package com.wdl.factory.data.data.helper;

import com.wdl.factory.Factory;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.card.User;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;
import com.wdl.factory.persistence.Account;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/6 16:49
 * 描述：    设备操作工具类
 */
@SuppressWarnings("unused")
public class PiHelper {

    /**
     * 查看已绑定设备
     */
    public static void select() {
        RemoteService service = Network.remoteService();
        if (Account.getUserId()==null)return;
        User user = new User();
        user.setuId(Account.getUserId());
        Call<RspModel<List<Pi>>> call = service.selectAllPi(user);
        call.enqueue(new CallbackImpl<List<Pi>>() {
            @Override
            protected void failed(String msg) {
            }

            @Override
            protected void succeed(List<Pi> data) {
                if (data==null||data.size()==0)return;
                //唤起进行数据存储
                Factory.getPiCenter().dispatch(data.toArray(new Pi[0]));
            }
        });
    }
}
