package com.wdl.factory.net;

import com.wdl.factory.model.api.PageInfo;
import com.wdl.factory.model.api.account.LoginModel;
import com.wdl.factory.model.api.account.RegisterModel;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.api.pi.ImagePage;
import com.wdl.factory.model.api.pi.Model;
import com.wdl.factory.model.api.pi.PiModel;
import com.wdl.factory.model.card.AccessToken;
import com.wdl.factory.model.card.Feedback;
import com.wdl.factory.model.card.Image;
import com.wdl.factory.model.card.Notice;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.card.Version;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.net
 * 创建者：   wdl
 * 创建时间： 2018/8/3 18:40
 * 描述：    网络请求的所有接口、api
 */
@SuppressWarnings("unused")
public interface RemoteService {

    @POST("token")
    Call<AccessToken> getToken(@QueryMap HashMap<String, String> map);

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

    /**
     * 更新用户信息
     *
     * @param user User
     * @return RspModel<User>
     */
    @POST("user/update")
    Call<RspModel<User>> update(@Body User user);


    /**
     * 获取用户信息
     *
     * @param id uId
     * @return RspModel<User>
     */
    @GET("user/getUserInfo/{uId}")
    Call<RspModel<User>> getUserInfo(@Path("uId") int id);

    /*---------------------------------------------------------------------------------------------------------*/

    /**
     * 查询所有公告
     *
     * @return RspModel<List                               <                               Notice>>
     */
    @POST("notice/selectAll")
    Call<RspModel<List<Notice>>> notice();

    /*---------------------------------------------------------------------------------------------------------*/

    /**
     * 查看已绑定设备列表
     *
     * @param user User
     * @return RspModel<List   <   Pi>>
     */
    @POST("pi/select")
    Call<RspModel<List<Pi>>> selectAllPi(@Body User user);

    /**
     * 绑定设备
     *
     * @param model PiModel
     * @return RspModel<Pi>
     */
    @POST("pi/add")
    Call<RspModel<Pi>> addPi(@Body PiModel model);

    /**
     * 删除设备
     *
     * @param model PiModel
     * @return RspModel<Pi>
     */
    @POST("pi/delete")
    Call<RspModel<Pi>> deletePi(@Body PiModel model);

    /**
     * 控制开关
     *
     * @param model PiModel
     * @return RspModel<String>
     */
    @POST("pi/changeSwitchState")
    Call<RspModel<Pi>> changedState(@Body PiModel model);


    /**
     * 更新设备信息,uId
     *
     * @param model PiModel
     * @return RspModel<Pi>
     */
    @POST("pi/update")
    Call<RspModel<Pi>> update(@Body Model model);

    /**
     * 添加反馈(需传uId、fSubject、fContent)
     *
     * @param feedback Feedback
     * @return RspModel<Feedback>
     */
    @POST("feedback/insert")
    Call<RspModel<Feedback>> insertFeedback(@Body Feedback feedback);

    /**
     * 查看所有反馈(需传uId)
     *
     * @param feedback Feedback
     * @return RspModel<List       <       Feedback>>
     */
    @POST("feedback/selectByUid")
    Call<RspModel<List<Feedback>>> selectFeedback(@Body Feedback feedback);


    /**
     * 获取设备的图片
     *
     * @param pId pId
     * @return RspModel<List   <   Image>>
     */
    @POST("image/selectByPid")
    Call<RspModel<List<Image>>> getPic(@Body Pi pId);

    /**
     * 获取设备的图片
     *
     * @param page page
     * @return RspModel<PageInfo < Image>>
     */
    @POST("image/selectPageListByPid")
    Call<RspModel<PageInfo<Image>>> getPic(@Body ImagePage page);


    /**
     * QQ绑定
     *
     * @param user User(uUsername,uTelephone,uPassword)
     * @return RspModel<User>
     */
    @POST("user/qqbind")
    Call<RspModel<User>> bindQQ(@Body RegisterModel user);

    /**
     * QQ注册绑定
     *
     * @param user User(uUsername,uTelephone,uPassword,code)
     * @return RspModel<User>
     */
    @POST("user/qqreg")
    Call<RspModel<User>> regQQ(@Body RegisterModel user);

    /**
     * QQ登陆
     *
     * @param user User(uUsername)
     * @return RspModel<User>
     */
    @POST("user/qqlogin")
    Call<RspModel<User>> loginQQ(@Body User user);

    /**
     * 获取版本信息
     *
     * @return RspModel<Version>
     */
    @POST("getVersion")
    Call<RspModel<Version>> getVersion();


    /**
     * 下载文件
     *
     * @param fileUrl
     * @return ResponseBody
     */
    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
