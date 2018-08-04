package com.wdl.factory.net;

import com.wdl.common.common.Common;
import com.wdl.factory.Factory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.net
 * 创建者：   wdl
 * 创建时间： 2018/8/3 15:44
 * 描述：    网络请求的封装
 */
@SuppressWarnings("unused")
public class Network {
    private static Network instance;
    private Retrofit retrofit;

    static {
        instance = new Network();
    }

    private Network() {
    }

    /**
     * @return Retrofit
     */
    public static Retrofit getRetrofit() {
        if (instance.retrofit != null) return instance.retrofit;
        //获取一个client
        OkHttpClient client = new OkHttpClient
                .Builder()
               //添加一个拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder();
                        //添加一个请求头
                        builder.addHeader("Content-Type", "application/json;charset=UTF-8");
                        return chain.proceed(builder.build());
                    }
                })
                .build();
        Retrofit.Builder builder = new Retrofit.Builder();
        instance.retrofit = builder
                .baseUrl(Common.Constance.API_URL)
                .client(client)
                //设置gson解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
        return instance.retrofit;
    }

    /**
     * 得到一个请求代理
     *
     * @return RemoteService
     */
    public static RemoteService remoteService() {
        return Network.getRetrofit().create(RemoteService.class);
    }
}
