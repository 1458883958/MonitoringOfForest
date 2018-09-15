package com.wdl.factory.presenter.contact;

import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.contact
 * 创建者：   wdl
 * 创建时间： 2018/9/15 13:41
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public interface ContactContract {

    interface View extends BaseContract.RecyclerView<UserDb,Presenter>{

    }

    interface Presenter extends BaseContract.Presenter{

    }
}
