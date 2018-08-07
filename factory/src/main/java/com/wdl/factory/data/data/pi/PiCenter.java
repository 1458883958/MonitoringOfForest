package com.wdl.factory.data.data.pi;

import com.wdl.factory.model.card.Pi;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/7 11:13
 * 描述：    设备中心
 */
@SuppressWarnings("unused")
public interface PiCenter {

    /**
     * 分发处理一堆设备信息,并更新到数据库
     *
     * @param pis 设备列表
     */
    void dispatch(Pi... pis);
}
