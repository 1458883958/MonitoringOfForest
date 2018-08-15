package com.wdl.factory.presenter.account;

import com.wdl.factory.R;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.AccountHelper;
import com.wdl.factory.model.api.account.RegisterModel;
import com.wdl.factory.model.card.User;
import com.wdl.factory.presenter.BasePresenter;
import com.wdl.utils.LogUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.account
 * 创建者：   wdl
 * 创建时间： 2018/8/15 13:46
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class QQPresenter extends BasePresenter<QQContract.View>
        implements QQContract.Presenter,DataSource.Callback<String>{
    public QQPresenter(QQContract.View view) {
        super(view);
    }

    @Override
    public void getSms(String phone) {
        final QQContract.View view = getView();
        if (checkPhone(phone)) {
            User user = new User();
            user.setuTelephone(phone);
            AccountHelper.getCode(user, this);
        } else {
            view.showError(R.string.data_phone_invalid_parameter);
        }
    }

    @Override
    public boolean checkPhone(String phone) {
        return Pattern.matches(com.wdl.common.common.Common.Constance.REGEX_PHONE, phone);
    }

    @Override
    public void bind(String userId, String phone, String password) {
        start();
        final QQContract.View view = getView();
        if (checkPhone(phone)) {
            RegisterModel model = new RegisterModel();
            model.setuUsername(userId);
            model.setuTelephone(phone);
            model.setuPassword(password);
            AccountHelper.qqBind(model,this);
        } else {
            view.showError(R.string.data_phone_invalid_parameter);
        }
    }

    @Override
    public void register(String userId, String phone, String password, String code) {
        start();
        final QQContract.View view = getView();
        if (checkPhone(phone)) {
            RegisterModel model = new RegisterModel(userId,phone, password, code);
            AccountHelper.qqRegister(model,this);
        } else {
            view.showError(R.string.data_phone_invalid_parameter);
        }
    }

    @Override
    public void onLoaded(final String data) {
        final QQContract.View view = getView();
        LogUtils.e("bind:"+data);
        if (view == null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (data.length()>9) {
                    view.showToast(R.string.label_bind_ok);
                    view.succeed();
                }
                else
                    view.setCode(data);
            }
        });
    }

    @Override
    public void onNotAvailable(final int res) {
        final QQContract.View view = getView();
        if (view == null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(res);
            }
        });
    }
}
