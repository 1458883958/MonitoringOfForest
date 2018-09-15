package com.wdl.factory.data.data.user;


import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wdl.factory.data.data.BaseDbRepository;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.model.db.UserDb_Table;
import com.wdl.factory.persistence.Account;
import com.wdl.utils.LogUtils;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/7 14:15
 * 描述：    设备仓库
 */
@SuppressWarnings("unused")
public class UserRepository extends BaseDbRepository<UserDb> implements UserDataSource{

    @Override
    public void load(SucceedCallback<List<UserDb>> callback) {
       super.load(callback);
        LogUtils.e("userId:callback  "+Account.getUserId() );
       //数据加载
       SQLite.select()
               .from(UserDb.class)
               .where(UserDb_Table.id.notEq(Account.getUserId()))
               .async()
               //此方法起主导作用
               .queryListResultCallback(this)
               .execute();
    }

    /**
     * 判断是否是我想要的数据
     *
     * @param datum UserDb
     * @return true:是我需要的数据
     */
    @Override
    protected boolean isRequired(UserDb datum) {
        return !datum.getId().equals(Account.getUserId());
    }
}
