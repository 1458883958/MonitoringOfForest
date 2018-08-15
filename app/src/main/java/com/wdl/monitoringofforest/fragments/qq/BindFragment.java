package com.wdl.monitoringofforest.fragments.qq;


import android.os.Bundle;
import android.widget.EditText;

import com.wdl.common.app.Application;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.factory.presenter.account.QQContract;
import com.wdl.factory.presenter.account.QQPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.AccountActivity;
import com.wdl.monitoringofforest.activities.QQBindActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BindFragment extends PresenterFragment<QQContract.Presenter>
        implements QQContract.View {

    private String userId;

    @BindView(R.id.uTelephone)
    EditText mTelPhone;
    @BindView(R.id.uPassword)
    EditText mPassword;

    public BindFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        userId = bundle.getString(QQBindActivity.QQ_USER_ID);
    }

    public static BindFragment newInstance() {
        return new BindFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_bind;
    }

    @OnClick(R.id.submit)
    void submit() {
        String password = mPassword.getText().toString().trim();
        String tel = mTelPhone.getText().toString().trim();
        mPresenter.bind(userId, tel, password);
    }

    @Override
    protected QQContract.Presenter initPresenter() {
        return new QQPresenter(this);
    }

    @Override
    public void succeed() {
        AccountActivity.show(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void setCode(String code) {
    }
}
