package factory.data;

import android.support.annotation.StringRes;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    factory.data
 * 创建者：   wdl
 * 创建时间： 2018/8/3 15:09
 * 描述：    数据源的接口封装
 */
@SuppressWarnings("unused")
public interface DataSource {

    /**
     * 总接口
     *
     * @param <T> T
     */
    interface Callback<T> extends SucceedCallback<T>, FailedCallback {

    }

    //请求成功的回调
    interface SucceedCallback<T> {
        /**
         * 请求成功的回调
         *
         * @param data T
         */
        void onLoaded(T data);
    }

    //请求失败的回调
    interface FailedCallback {
        /**
         * @param res 资源ID
         */
        void onNotAvailable(@StringRes int res);
    }

}
