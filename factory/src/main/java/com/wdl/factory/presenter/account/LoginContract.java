package com.wdl.factory.presenter.account;

import factory.presenter.BaseContract;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.account
 * 创建者：   wdl
 * 创建时间： 2018/8/3 14:48
 * 描述：    登陆的契约接口
 */
@SuppressWarnings("unused")
public interface LoginContract {
    interface View extends BaseContract.View<Presenter> {
        /**
         * 登陆成功
         */
        void loginSucceed();
    }

    interface Presenter extends BaseContract.Presenter {
        /**
         * 发起登陆
         *
         * @param phone    用户名
         * @param password 密码
         */
        void login(String phone, String password);
    }
}
