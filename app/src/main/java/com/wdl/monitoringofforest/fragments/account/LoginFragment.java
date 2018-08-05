package com.wdl.monitoringofforest.fragments.account;


import android.content.Context;
import android.widget.EditText;

import com.wdl.common.common.app.Fragment;
import com.wdl.common.common.app.PresenterFragment;
import com.wdl.factory.presenter.account.LoginContract;
import com.wdl.factory.presenter.account.LoginPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.MainActivity;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import utils.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class LoginFragment extends PresenterFragment<LoginContract.Presenter> implements
        LoginContract.View {

    private AccountTrigger accountTrigger;
    @BindView(R.id.et_phone)
    EditText mPhone;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.loading)
    Loading mLoading;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.btn_login_qq)
    Button loginByQQ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //添加对界面切换的引用
        this.accountTrigger = (AccountTrigger) context;

    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    /**
     * 初始化
     *
     * @return LoginPresenter
     */
    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @OnClick(R.id.trigger)
    void triggerView() {
        accountTrigger.triggerView();
    }

    @OnClick(R.id.submit)
    void submit() {
        String phone = mPhone.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        mPresenter.login(phone, password);
    }

    @Override
    public void showError(int res) {
        super.showError(res);
        //正在登录时,控件不可操作
        mLoading.stop();
        setStatus(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        //正在登录时,控件不可操作
        mLoading.start();
        setStatus(false);
    }

    private void setStatus(boolean flag) {
        //输入框是否可编辑
        mPhone.setEnabled(flag);
        mPassword.setEnabled(flag);
        //按钮是否可点击
        submit.setEnabled(flag);
        loginByQQ.setEnabled(flag);
    }

    @Override
    public void loginSucceed() {
        MainActivity.show(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }
}
