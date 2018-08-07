package com.wdl.factory.data;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    factory.data
 * 创建者：   wdl
 * 创建时间： 2018/8/6 14:42
 * 描述：    基础的数据库数据源接口定义
 */
@SuppressWarnings("unused")
public interface DbDataSource<Data> extends DataSource {

    /**
     * 基本的数据源加载方法
     *
     * @param callback SucceedCallback<List<Data>>
     */
    void load(SucceedCallback<List<Data>> callback);
}
