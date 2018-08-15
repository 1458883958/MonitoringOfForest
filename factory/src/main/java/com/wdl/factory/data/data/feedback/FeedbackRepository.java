package com.wdl.factory.data.data.feedback;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.BaseDbRepository;
import com.wdl.factory.model.card.Feedback;
import com.wdl.factory.model.db.FeedbackDb;
import com.wdl.factory.model.db.FeedbackDb_Table;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.persistence.Account;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.feedback
 * 创建者：   wdl
 * 创建时间： 2018/8/14 15:04
 * 描述：    反馈记录 数据仓库
 */
@SuppressWarnings("unused")
public class FeedbackRepository extends BaseDbRepository<FeedbackDb> implements FeedbackDataSource {
    @Override
    public void load(SucceedCallback<List<FeedbackDb>> callback) {
        super.load(callback);
        //数据加载
        SQLite.select()
                .from(FeedbackDb.class)
                .where(FeedbackDb_Table.userId.eq(Account.getUserId()))
                .limit(100)
                .async()
                //此方法起主导作用
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(FeedbackDb datum) {
        return true;
    }
}
