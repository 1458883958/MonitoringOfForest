package com.wdl.factory.presenter.set;

import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.set
 * 创建者：   wdl
 * 创建时间： 2018/8/19 16:49
 * 描述：   清空缓存
 */
public interface SetContract extends BaseContract{
    interface View extends BaseContract.View<Presenter>{
        void cleanSucceed();
    }

    interface Presenter extends BaseContract.Presenter{
        void cleanCache();
    }
}
