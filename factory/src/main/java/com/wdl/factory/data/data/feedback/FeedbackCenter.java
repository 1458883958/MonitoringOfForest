package com.wdl.factory.data.data.feedback;

import com.wdl.factory.model.card.Feedback;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.feedback
 * 创建者：   wdl
 * 创建时间： 2018/8/14 12:32
 * 描述：    反馈中心
 */
@SuppressWarnings("unused")
public interface FeedbackCenter {

    void dispatch(Feedback... feeds);
}
