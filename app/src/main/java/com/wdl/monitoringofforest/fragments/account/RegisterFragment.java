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
public class RegisterFragment extends Fragment {

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
}
