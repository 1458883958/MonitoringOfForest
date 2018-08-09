package com.wdl.factory.presenter.search;

import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.search
 * 创建者：   wdl
 * 创建时间： 2018/8/9 11:27
 * 描述：    添加设备
 */
@SuppressWarnings("unused")
public interface PiContract {
    interface View extends BaseContract.View<Presenter>{
        /**
         * 绑定成功
         */
        void bindSucceed();
    }

    interface Presenter extends BaseContract.Presenter{
        /**
         * 绑定设备
         * @param pId 设备Id 扫码获取
         * @param password 密码
         */
        void bind(Integer pId,String password);
    }
}
