package com.wdl.monitoringofforest.fragments.account;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdl.common.app.Application;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.factory.Factory;
import com.wdl.factory.presenter.account.LoginContract;
import com.wdl.factory.presenter.account.LoginPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.MainActivity;
import com.wdl.monitoringofforest.activities.QQBindActivity;
import com.wdl.utils.LogUtils;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        implements LoginContract.View, PlatformActionListener, View.OnClickListener {

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

    private AlertDialog dialog;
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
    void loginByQQ() {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);
        if (!platform.isAuthValid()) {
            showError(R.string.label_go_start);
        }
        authorize(platform);
    }

    private void authorize(Platform platform) {
        if (platform == null) return;
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


    //跳转绑定页面
    @Override
    public void qqLoginDefault() {
        dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.__picker_bg_dialog);
        view.findViewById(R.id.go_bind).setOnClickListener(this);
        view.findViewById(R.id.go_reg).setOnClickListener(this);
    }


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Platform platform = (Platform) msg.obj;
                    String userId = platform.getDb().getUserId();//获取用户账号
                    mPresenter.qqLogin(userId);
                    break;
                case 2:
                    showError(R.string.data_oss_cancel);
                    break;
                case 3:
                    showError(R.string.data_oss_defeat);
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = platform;
        handler.sendMessage(message);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = platform;
        handler.sendMessage(message);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Message message = Message.obtain();
        message.what = 3;
        message.obj = platform;
        handler.sendMessage(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_bind:
                QQBindActivity.show(getContext(),1,mPresenter.getUserId());
                dialog.cancel();
                break;
            case R.id.go_reg:
                QQBindActivity.show(getContext(),2,mPresenter.getUserId());
                dialog.cancel();
                break;
            default:
                break;
        }
    }
}
