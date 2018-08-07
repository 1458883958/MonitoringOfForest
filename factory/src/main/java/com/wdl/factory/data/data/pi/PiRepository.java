package com.wdl.factory.data.data.pi;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.wdl.factory.data.data.BaseDbRepository;
import com.wdl.factory.data.data.helper.DbHelper;
import com.wdl.factory.model.db.PiDb;

import java.util.LinkedList;
import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/7 14:15
 * 描述：    设备仓库
 */
@SuppressWarnings("unused")
public class PiRepository extends BaseDbRepository<PiDb> implements PiDataSource{

    @Override
    public void load(SucceedCallback<List<PiDb>> callback) {
       super.load(callback);
       //数据加载
       SQLite.select()
               .from(PiDb.class)
               .limit(10)
               .async()
               //此方法起主导作用
               .queryListResultCallback(this)
               .execute();
    }

    /**
     * 判断是否是我想要的数据
     *
     * @param datum PiDb
     * @return true:是我需要的数据
     */
    @Override
    protected boolean isRequired(PiDb datum) {
        return true;
    }
}
