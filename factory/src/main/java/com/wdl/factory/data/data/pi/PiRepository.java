package com.wdl.factory.data.data.pi;


import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wdl.factory.data.data.BaseDbRepository;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.model.db.PiDb_Table;
import com.wdl.factory.persistence.Account;

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
               .where(PiDb_Table.userId.eq(Account.getUserId()))
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
