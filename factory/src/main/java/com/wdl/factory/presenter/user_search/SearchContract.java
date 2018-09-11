package com.wdl.factory.presenter.user_search;

import com.wdl.factory.model.card.User;
import com.wdl.factory.presenter.BaseContract;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.user_search
 * 创建者：   wdl
 * 创建时间： 2018/9/11 21:07
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public interface SearchContract {

    interface Presenter extends BaseContract.Presenter {
        //搜索
        void search(String query);
    }

    interface UserView extends BaseContract.View<Presenter> {
        /**
         * 搜索完成
         *
         * @param users 搜索结果
         */
        void onSearchDone(List<User> users);
    }
}
