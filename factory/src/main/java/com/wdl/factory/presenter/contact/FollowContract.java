package com.wdl.factory.presenter.contact;

import com.wdl.factory.model.card.User;
import com.wdl.factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.contact
 * 创建者：   wdl
 * 创建时间： 2018/9/15 14:14
 * 描述：    TODO
 */
public interface FollowContract {
    interface View extends BaseContract.View<Presenter> {
        /**
         * 关注成功
         */
        void onFollowSucceed();

    }

    interface Presenter extends BaseContract.Presenter {
        /**
         * 关注
         *
         * @param user 被关注者
         */
        void follow(User user);
    }
}
