package com.wdl.factory.presenter.account;

import com.wdl.common.common.Common;
import com.wdl.factory.R;
import com.wdl.factory.data.data.helper.AccountHelper;
import com.wdl.factory.model.api.account.RegisterModel;
import com.wdl.factory.model.card.User;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.Objects;
import java.util.regex.Pattern;
import factory.data.DataSource;
import factory.presenter.BasePresenter;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.account
 * 创建者：   wdl
 * 创建时间： 2018/8/4 10:25
 * 描述：    注册P层
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<String> {
    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    /**
     * 注册
     *
     * @param phone    用户名
     * @param passwordA 密码
     * @param passwordB 密码
     * @param name     姓名
     * @param code     验证码
     */
    @Override
    public void register(String phone, String passwordA, String passwordB,String name, String code) {
        start();
        final RegisterContract.View view = getView();
        if (checkPhone(phone)) {
            if (checkPassword(passwordA,passwordB)) {
                RegisterModel model = new RegisterModel(phone, passwordA, code);
                AccountHelper.register(model, this);
            }else {
                view.showError(R.string.data_inconsistencies_in_input_passwords);
            }
        } else {
            view.showError(R.string.data_phone_invalid_parameter);
        }
    }

    /**
     * 获取验证码
     *
     * @param phone 手机号
     */
    @Override
    public void getSms(String phone) {
        final RegisterContract.View view = getView();
        if (checkPhone(phone)) {
            User user = new User();
            user.setuTelephone(phone);
            AccountHelper.getCode(user, this);
        } else {
            view.showError(R.string.data_phone_invalid_parameter);
        }
    }

    /**
     * 检查手机格式
     *
     * @param phone 手机号
     * @return boolean
     */
    @Override
    public boolean checkPhone(String phone) {
        return Pattern.matches(Common.Constance.REGEX_PHONE, phone);
    }

    /**
     * @param passwordA 密码
     * @param passwordB 密码
     * @return  boolean
     */
    @Override
    public boolean checkPassword(String passwordA, String passwordB) {
        return Objects.equals(passwordA,passwordB);
    }

    @Override
    public void onLoaded(final String data) {
        final RegisterContract.View view = getView();
        if (view == null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (data.contains("成功"))
                    view.registerSucceed();
                else
                    view.setCode(data);
            }
        });
    }

    @Override
    public void onNotAvailable(final int res) {
        final RegisterContract.View view = getView();
        if (view == null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(res);
            }
        });
    }
}
