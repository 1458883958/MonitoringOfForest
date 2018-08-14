package com.wdl.factory.presenter.feedback;

import com.wdl.factory.model.db.FeedbackDb;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.feedback
 * 创建者：   wdl
 * 创建时间： 2018/8/14 14:56
 * 描述：    历史反馈
 */
@SuppressWarnings("unused")
public interface HisFeedbackContract {
    interface View extends BaseContract.RecyclerView<FeedbackDb,Presenter>{

    }

    interface Presenter extends BaseContract.Presenter{
    }
}
