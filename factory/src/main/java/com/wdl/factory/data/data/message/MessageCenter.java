package com.wdl.factory.data.data.message;

import com.wdl.factory.model.card.Message;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.message
 * 创建者：   wdl
 * 创建时间： 2018/9/15 18:39
 * 描述：    TODO
 */
public interface MessageCenter {
    void dispatch(Message...messages);
}
