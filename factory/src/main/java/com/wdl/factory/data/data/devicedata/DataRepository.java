package com.wdl.factory.data.data.devicedata;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.BaseDbRepository;
import com.wdl.factory.model.db.ImageDb;
import com.wdl.factory.model.db.ImageDb_Table;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.devicedata
 * 创建者：   wdl
 * 创建时间： 2018/8/16 15:54
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class DataRepository extends BaseDbRepository<ImageDb> implements DeviceDataSource{

    private int pId;

    public DataRepository(int pId) {
        this.pId = pId;
    }

    @Override
    public void load(SucceedCallback<List<ImageDb>> callback) {
        super.load(callback);
        SQLite.select()
                .from(ImageDb.class)
                .where(ImageDb_Table.piId.eq(pId))
                .orderBy(ImageDb_Table.id,true)
                .limit(20)
                .async()
                //此方法起主导作用
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(ImageDb datum) {
        return true;
    }
}
