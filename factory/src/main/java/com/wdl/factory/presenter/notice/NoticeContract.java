package com.wdl.factory.presenter.notice;

import com.wdl.factory.model.db.NoticeDb;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.notice
 * 创建者：   wdl
 * 创建时间： 2018/9/12 15:53
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public interface NoticeContract {

    interface View extends BaseContract.RecyclerView<NoticeDb,Presenter>{

    }

    interface Presenter extends BaseContract.Presenter{
    }
}
