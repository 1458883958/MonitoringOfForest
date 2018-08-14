package com.wdl.factory.presenter.feedback;

import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.feedback
 * 创建者：   wdl
 * 创建时间： 2018/8/14 11:02
 * 描述：    添加反馈锲约接口
 */
@SuppressWarnings("unused")
public interface AddFeedbackContract {
    interface View extends BaseContract.View<Presenter>{
        /**
         * 反馈成功
         */
        void insertSucceed();

    }
    interface Presenter extends BaseContract.Presenter{
        /**
         * 添加反馈
         * @param subject 标题
         * @param content 内容
         */
        void insertFeedback(String subject,String content);
    }
}
