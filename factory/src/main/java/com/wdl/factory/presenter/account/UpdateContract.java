package com.wdl.factory.presenter.account;

import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter
 * 创建者：   wdl
 * 创建时间： 2018/8/18 14:28
 * 描述：    更新账户信息契约接口
 */
@SuppressWarnings("unused")
public interface UpdateContract {
    interface Presenter extends BaseContract.Presenter {
        void update(int type,String info);
        void update(int type,String info,double lat,double lot);
        UserDb getUser();
    }

    interface View extends BaseContract.View<Presenter>{
        void succeed(User user);
    }

}
