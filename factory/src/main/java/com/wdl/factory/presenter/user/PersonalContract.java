package com.wdl.factory.presenter.user;

import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.user
 * 创建者：   wdl
 * 创建时间： 2018/8/10 10:15
 * 描述：    user信息契约接口
 */
@SuppressWarnings("unused")
public interface PersonalContract {
    interface View extends BaseContract.View<Presenter> {
        //获取id
        int getId();

        //加载成功
        void onDone(UserDb user);

        /**
         * 是否允许聊天
         *
         * @param f boolean
         */
        void allowChat(boolean f);

        /**
         * 是否关注
         *
         * @param f boolean
         */
        void isFollow(boolean f);
    }

    interface Presenter extends BaseContract.Presenter {
        /**
         * 获取用户信息
         *
         * @return User
         */
        UserDb getUserInfo();
    }
}
