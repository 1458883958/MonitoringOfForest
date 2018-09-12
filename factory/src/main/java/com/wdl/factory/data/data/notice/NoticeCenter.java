package com.wdl.factory.data.data.notice;

import com.wdl.factory.model.card.Notice;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.notice
 * 创建者：   wdl
 * 创建时间： 2018/9/12 16:03
 * 描述：    TODO
 */
public interface NoticeCenter {
    void dispatch(Notice...notices);
}
