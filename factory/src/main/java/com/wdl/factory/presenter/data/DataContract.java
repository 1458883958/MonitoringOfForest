package com.wdl.factory.presenter.data;

import com.wdl.factory.model.db.ImageDb;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.data
 * 创建者：   wdl
 * 创建时间： 2018/8/16 15:44
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public interface DataContract {
    interface Presenter extends BaseContract.Presenter{

    }

    interface View extends BaseContract.RecyclerView<ImageDb,Presenter>{
        int getPiId();
    }
}
