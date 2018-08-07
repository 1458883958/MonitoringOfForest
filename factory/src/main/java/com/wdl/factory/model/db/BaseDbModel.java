package com.wdl.factory.model.db;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.wdl.factory.utils.DiffUiDataCallback;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.db
 * 创建者：   wdl
 * 创建时间： 2018/8/7 16:03
 * 描述：    app中基础的一个BaseDbModel,继承DbFlow数据库框架的BaseModel
 * 实现我们的对比接口,扩展了我们想要使用的方法
 */
@SuppressWarnings("unused")
public abstract class BaseDbModel<Data> extends BaseModel implements DiffUiDataCallback.UiDataDiff<Data>{
}
