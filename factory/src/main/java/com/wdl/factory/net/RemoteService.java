package com.wdl.factory.net;

import com.wdl.factory.model.api.account.LoginModel;
import com.wdl.factory.model.api.account.RegisterModel;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.card.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.net
 * 创建者：   wdl
 * 创建时间： 2018/8/3 18:40
 * 描述：    网络请求的所有接口、api
 */
@SuppressWarnings("unused")
public interface RemoteService {
    /**
     * 获取短信验证码
     *
     * @param model 用户名
     * @return RspModel<String>
     */
    @POST("sendSmsCode")
    Call<RspModel<String>> getSmsCode(@Body User model);

    /**
     * 用户注册
     *
     * @param model RegisterModel
     * @return RspModel<String>
     */
    @POST("userReg")
    Call<RspModel<String>> register(@Body RegisterModel model);

    /**
     * 用户登陆
     *
     * @param model LoginModel
     * @return RspModel<User>
     */
    @POST("user/select")
    Call<RspModel<User>> login(@Body LoginModel model);

}
