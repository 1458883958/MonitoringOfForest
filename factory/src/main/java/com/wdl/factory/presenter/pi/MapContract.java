package com.wdl.factory.presenter.pi;

import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.presenter.BaseContract;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.pi
 * 创建者：   wdl
 * 创建时间： 2018/9/4 10:03
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public interface MapContract {
    interface View extends BaseContract.View<Presenter>{
        void result(List<PiDb> dbs);
        void failed();
    }

    interface Presenter extends BaseContract.Presenter{
        void query();
   }
}
