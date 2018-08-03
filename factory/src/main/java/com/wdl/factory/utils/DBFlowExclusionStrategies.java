package com.wdl.factory.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.utils
 * 创建者：   wdl
 * 创建时间： 2018/8/3 18:36
 * 描述：    gson解析中过滤掉数据库级别的字段
 */
@SuppressWarnings("unused")
public class DBFlowExclusionStrategies implements ExclusionStrategy{
    //过滤字段
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaredClass().equals(ModelAdapter.class);
    }

    //过滤类
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
