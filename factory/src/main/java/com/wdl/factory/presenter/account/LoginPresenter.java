package com.wdl.factory.presenter.account;

import android.text.TextUtils;

import com.wdl.factory.R;
import com.wdl.factory.data.data.helper.AccountHelper;
import com.wdl.factory.model.api.account.LoginModel;
import com.wdl.factory.model.db.User;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import factory.data.DataSource;
import factory.presenter.BasePresenter;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter
 * 创建者：   wdl
 * 创建时间： 2018/8/3 14:45
 * 描述：    登陆具体的presenter
 */
@SuppressWarnings("unused")
public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter,DataSource.Callback<User>{

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {
        start();
        final LoginContract.View view = getView();
        //检验账号密码
        if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(password))
            view.showError(R.string.data_login_account_invalid_parameter);
        //构建请求model
        LoginModel model = new LoginModel(phone,password);
        //请求
        AccountHelper.login(model,this);
    }

    @Override
    public void onLoaded(User data) {
        final LoginContract.View view = getView();
        if (view==null)return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.loginSucceed();
            }
        });
    }

    @Override
    public void onNotAvailable(final int res) {
        final LoginContract.View view = getView();
        if (view==null)return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(res);
            }
        });
    }
}
