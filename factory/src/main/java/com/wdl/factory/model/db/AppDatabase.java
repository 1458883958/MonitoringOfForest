package com.wdl.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.db
 * 创建者：   wdl
 * 创建时间： 2018/8/4 12:55
 * 描述：    数据库的基本信息
 */
@SuppressWarnings("unused")
@Database(name = AppDatabase.NAME,version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "AppDatabase";
    public static final int VERSION = 12;

}
