package com.wdl.monitoringofforest.fragments.account;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import com.wdl.common.app.Application;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.factory.Factory;
import com.wdl.factory.presenter.account.LoginContract;
import com.wdl.factory.presenter.account.LoginPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.MainActivity;
import com.wdl.utils.LogUtils;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class LoginFragment extends PresenterFragment<LoginContract.Presenter>
        implements LoginContract.View ,PlatformActionListener{

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

    @OnClick(R.id.btn_login_qq)
    void loginByQQ(){
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);
        if (!platform.isAuthValid()){
            Application.showToast(R.string.label_go_start);
        }
        authorize(platform);
    }

    private void authorize(Platform platform) {
        if (platform==null)return;
        if (platform.isAuthValid())
            platform.removeAccount(true);
        platform.showUser(null);
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

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message message = Message.obtain();
        message.obj = platform;
        handler.sendMessage(message);
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Platform platform = (Platform) msg.obj;
            String userId = platform.getDb().getUserId();//获取用户账号
            String userName = platform.getDb().getUserName();//获取用户名字
            String userIcon = platform.getDb().getUserIcon();//获取用户头像
            String userGender = platform.getDb().getUserGender();
            //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
            LogUtils.e(""+userId+"\n"+userName+"\n"+userIcon);
            return true;
        }
    });

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
