package com.wdl.factory.data.data.pi;

import com.wdl.factory.data.DbDataSource;
import com.wdl.factory.model.db.PiDb;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/7 14:16
 * 描述：    设备数据源，监听数据源,回调给presenter
 */
@SuppressWarnings("unused")
public interface PiDataSource extends DbDataSource<PiDb>{
}
