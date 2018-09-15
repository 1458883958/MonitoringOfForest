package com.wdl.factory.data.data.message;


import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wdl.factory.data.data.BaseDbRepository;
import com.wdl.factory.data.data.helper.DbHelper;
import com.wdl.factory.data.data.notice.NoticeDataSource;
import com.wdl.factory.model.db.MessageDb;
import com.wdl.factory.model.db.MessageDb_Table;
import com.wdl.factory.model.db.NoticeDb;
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
public class MessageRepository extends BaseDbRepository<MessageDb> implements MessageDataSource{

    private int receiver;
    public MessageRepository(int receiver) {
        this.receiver = receiver;
    }

    @Override
    public void load(SucceedCallback<List<MessageDb>> callback) {
       super.load(callback);
       //数据加载
       SQLite.select()
               .from(MessageDb.class)
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
    protected boolean isRequired(MessageDb datum) {
        return (datum.getSenderId()==Account.getUserId()&&datum.getReceiverId()==receiver)
                ||(datum.getReceiverId()==Account.getUserId()&&datum.getSenderId()==receiver);
    }
}
