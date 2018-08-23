package com.wdl.factory.presenter.recognition;

import com.wdl.factory.model.card.RecResult;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.recognition
 * 创建者：   wdl
 * 创建时间： 2018/8/22 14:47
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public interface RecognitionContract {
    interface View extends BaseContract.View<Presenter>{
        void succeed(RecResult result);
    }

    interface Presenter extends BaseContract.Presenter{
        void recognition(String path);
    }
}
