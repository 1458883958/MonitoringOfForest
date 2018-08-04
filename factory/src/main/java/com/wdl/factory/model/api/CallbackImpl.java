package com.wdl.factory.model.api;

import com.wdl.factory.R;
import com.wdl.factory.model.api.account.RspModel;

import factory.data.DataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.api
 * 创建者：   wdl
 * 创建时间： 2018/8/3 14:59
 * 描述：    回调的基本封装
 */
@SuppressWarnings("unused")
public abstract class CallbackImpl<T> implements Callback<RspModel<T>> {

    @Override
    public void onResponse(Call<RspModel<T>> call, Response<RspModel<T>> response) {
        RspModel<T> rspModel = response.body();
        assert rspModel != null;
        if (rspModel.getStatus() == 200) {
            T data = rspModel.getData();
            succeed(data);
        } else {
            String msg = rspModel.getMsg();
            failed(msg);
        }
    }

    @Override
    public void onFailure(Call<RspModel<T>> call, Throwable t) {
        failed(t.getMessage());
    }

    protected abstract void failed(String msg);

    protected abstract void succeed(T data);


}
