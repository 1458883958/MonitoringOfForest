package com.wdl.factory.data.data.helper;

import com.wdl.factory.model.api.account.LoginModel;
import com.wdl.factory.model.db.User;
import com.wdl.factory.presenter.account.LoginPresenter;

import factory.data.DataSource;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/3 15:06
 * 描述：    账户的工具类
 */
@SuppressWarnings("unused")
public class AccountHelper {
    /**
     * 登陆
     *
     * @param model    LoginModel
     * @param callback Callback
     */
    public static void login(LoginModel model, DataSource.Callback<User> callback) {
    }
}
