package com.wdl.monitoringofforest.fragments.account;


import android.content.Context;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.widget.EditText;

import com.wdl.common.common.app.Fragment;
import com.wdl.common.common.app.PresenterFragment;
import com.wdl.factory.model.db.User;
import com.wdl.factory.presenter.account.RegisterContract;
import com.wdl.factory.presenter.account.RegisterPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.helper.MyCountDownTimer;

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
        RegisterContract.View {

    private AccountTrigger accountTrigger;
    private MyCountDownTimer countTime;
    @BindView(R.id.et_phone)
    EditText mPhone;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.et_re_password)
    EditText mPasswordB;
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
    public void onResume() {
        super.onResume();
        initTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countTime.cancel();
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
    void triggerView() {
        accountTrigger.triggerView();
    }


    @OnClick(R.id.submit)
    void register() {
        String phone = mPhone.getText().toString().trim();
        String passwordA = mPassword.getText().toString().trim();
        String passwordB = mPasswordB.getText().toString().trim();
        String name = mName.getText().toString().trim();
        String code = mCode.getText().toString().trim();
        mPresenter.register(phone, passwordA,passwordB, name, code);
    }

    @OnClick(R.id.get_code)
    void getCode() {
        countTime.timeStart(true);
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


    /**
     * 初始化
     * 判断是否是第一次开始计时
     * <p>
     * 1.页面切换后的判断在此进行
     */
    public void initTimer() {
        long time = MyCountDownTimer.currentMillis + 60000;
        //不是第一次进并且时间+60>当前时间
        if (!MyCountDownTimer.FLAG_FIRST_IN
                && time > System.currentTimeMillis()) {
            //设置倒计时时间,减去已经倒计时的时间
            setCountDownTimer(time - System.currentTimeMillis());
            countTime.timeStart(false);
        } else {
            //第一次进入
            setCountDownTimer(60 * 1000);
        }
    }

    private void setCountDownTimer(final long l) {
        countTime = new MyCountDownTimer(l, 1000) {
            /**
             * 完成一次间隔的回调
             *
             * @param millisUntilFinished long
             */
            @Override
            public void onTick(long millisUntilFinished) {
                mGetCode.setText(String.format(getString(R.string.label_the_verifying_code),
                        "" + (millisUntilFinished / 1000)));
                mGetCode.setClickable(false);
            }

            /**
             * 倒计时完成的回调
             */
            @Override
            public void onFinish() {
                mGetCode.setText(R.string.label_re_get_the_verifying_code);
                mGetCode.setClickable(true);
                mGetCode.setEnabled(true);
                //完成后重新复位
                if (l != 60 * 1000)
                    setCountDownTimer(60 * 1000);
            }
        };
    }

}
