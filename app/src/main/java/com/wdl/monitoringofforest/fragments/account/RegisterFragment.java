package com.wdl.monitoringofforest.fragments.account;


import android.content.Context;
import android.widget.EditText;

import com.wdl.common.common.app.Fragment;
import com.wdl.common.common.app.PresenterFragment;
import com.wdl.factory.model.db.User;
import com.wdl.factory.presenter.account.RegisterContract;
import com.wdl.factory.presenter.account.RegisterPresenter;
import com.wdl.monitoringofforest.R;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;
import factory.data.DataSource;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements
    RegisterContract.View{

    private AccountTrigger accountTrigger;
    @BindView(R.id.et_phone)
    EditText mPhone;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.et_name)
    EditText mName;
    @BindView(R.id.et_code)
    EditText mCode;
    @BindView(R.id.loading)
    Loading mLoading;
    @BindView(R.id.get_code)
    android.widget.Button mGetCode;
    @BindView(R.id.submit)
    Button submit;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.accountTrigger = (AccountTrigger) context;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }


    @OnClick(R.id.trigger)
    void triggerView(){
        accountTrigger.triggerView();
    }


    @OnClick(R.id.submit)
    void register(){
        String phone = mPhone.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String name = mName.getText().toString().trim();
        String code = mCode.getText().toString().trim();
        mPresenter.register(phone,password,name,code);
    }

    @OnClick(R.id.get_code)
    void getCode(){
        String phone = mPhone.getText().toString().trim();
        mPresenter.getSms(phone);
    }

    @Override
    public void registerSucceed() {
        accountTrigger.triggerView();
    }

    @Override
    public void setCode(String code) {
        mCode.setText(code);
    }

}
