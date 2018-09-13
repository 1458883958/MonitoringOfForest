package com.wdl.factory.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.wdl.common.common.Common;
import com.wdl.factory.Factory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

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
    private Retrofit retrofit2;
    private Retrofit retrofit3;

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
     * @return Retrofit
     */
    public static Retrofit getRetrofit2() {
        if (instance.retrofit2 != null) return instance.retrofit2;

        Retrofit.Builder builder = new Retrofit.Builder();
        instance.retrofit2 = builder
                .baseUrl(Common.Constance.BAI_D)
                //设置gson解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
        return instance.retrofit2;
    }

    /**
     * @return Retrofit
     */
    public static Retrofit getRetrofit3() {
        if (instance.retrofit3 != null) return instance.retrofit3;
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = gsonBuilder.create();


        Retrofit.Builder builder = new Retrofit.Builder();
        instance.retrofit3 = builder
                .baseUrl(Common.Constance.API_URL)
                .client(client)
                //设置gson解析器
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return instance.retrofit3;
    }


    /**
     * 得到一个请求代理
     *
     * @return RemoteService
     */
    public static RemoteService remoteService() {
        return Network.getRetrofit().create(RemoteService.class);
    }

    /**
     * 得到一个请求代理
     *
     * @return RemoteService
     */
    public static RemoteService remoteService2() {
        return Network.getRetrofit2().create(RemoteService.class);
    }

    /**
     * 得到一个请求代理
     *
     * @return RemoteService
     */
    public static RemoteService remoteService3() {
        return Network.getRetrofit3().create(RemoteService.class);
    }
}
