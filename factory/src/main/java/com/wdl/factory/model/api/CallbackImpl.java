package com.wdl.factory.model.api;

import com.wdl.factory.model.api.account.RspModel;
import com.wdl.utils.LogUtils;

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
        if (rspModel!=null) {
            LogUtils.e("rspModel:"+rspModel.toString());
            if (rspModel.getStatus() == 200) {
                T data = rspModel.getData();
                if (data==null){
                    String msg = rspModel.getMsg();
                    succeedMsg(msg);
                }
                succeed(data);
            } else if (rspModel.getStatus() == 500){
                String msg = rspModel.getMsg();
                failed(msg);
            }
        }else {
            failed("返回数据为空");
            return;
        }
    }

    @Override
    public void onFailure(Call<RspModel<T>> call, Throwable t) {
        failed(t.getMessage());
    }

    protected abstract void failed(String msg);

    protected abstract void succeed(T data);

    protected void succeedMsg(String msg){

    }


}
