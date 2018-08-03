package com.wdl.monitoringofforest.fragments.account;



import android.content.Context;
import android.widget.EditText;
import com.wdl.common.common.app.Fragment;
import com.wdl.monitoringofforest.R;
import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class LoginFragment extends Fragment {

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

    @OnClick(R.id.trigger)
    void triggerView(){
        accountTrigger.triggerView();
    }
}
