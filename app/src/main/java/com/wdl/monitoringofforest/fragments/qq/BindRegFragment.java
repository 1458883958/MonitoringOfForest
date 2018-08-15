package com.wdl.monitoringofforest.fragments.qq;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wdl.common.app.Application;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.factory.presenter.account.QQContract;
import com.wdl.factory.presenter.account.QQPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.AccountActivity;
import com.wdl.monitoringofforest.activities.QQBindActivity;
import com.wdl.utils.LogUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class BindRegFragment extends PresenterFragment<QQContract.Presenter>
    implements QQContract.View{

    private String userId;

    @BindView(R.id.uTelephone)
    EditText mTelPhone;
    @BindView(R.id.uPassword)
    EditText mPassword;
    @BindView(R.id.et_code)
    EditText mCode;

    public BindRegFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        userId = bundle.getString(QQBindActivity.QQ_USER_ID);
    }
    public static BindRegFragment newInstance(){
        return new BindRegFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_bind_reg;
    }

    @Override
    protected QQContract.Presenter initPresenter() {
        return new QQPresenter(this);
    }

    @Override
    public void succeed() {
        LogUtils.e("QQ注册绑定成功");
        AccountActivity.show(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void setCode(String code) {
        mCode.setText(code);
    }


    @OnClick(R.id.get_code)
    void getCode(){
        String tel = mTelPhone.getText().toString().trim();
        mPresenter.getSms(tel);
    }

    @OnClick(R.id.submit)
    void submit(){
        String password = mPassword.getText().toString().trim();
        String tel = mTelPhone.getText().toString().trim();
        String code = mCode.getText().toString().trim();
        mPresenter.register(userId,tel,password,code);
    }
}
