package com.wdl.factory.net;

import com.wdl.factory.model.api.account.LoginModel;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.db.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
     * @param phone 用户名
     * @return RspModel<String>
     */
    @GET("getSmsCode/{uTelephone}")
    Call<RspModel<String>> getSmsCode(@Path(encoded = true, value = "uTelephone") String phone);

    /**
     * 用户登陆
     *
     * @param model LoginModel
     * @return RspModel<User>
     */
    @POST("user/select")
    Call<RspModel<User>> login(@Body LoginModel model);
}
