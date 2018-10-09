package com.wdl.monitoringofforest.fragments.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdl.common.app.Application;
import com.wdl.common.app.Fragment;
import com.wdl.factory.Factory;
import com.wdl.factory.data.data.helper.UserHelper;
import com.wdl.factory.persistence.Account;
import com.wdl.monitoringofforest.App;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.AboutAppActivity;
import com.wdl.monitoringofforest.activities.AccountActivity;
import com.wdl.monitoringofforest.activities.AccountInfoActivity;
import com.wdl.monitoringofforest.activities.SettingActivity;
import com.wdl.monitoringofforest.activities.ShareActivity;
import com.wdl.monitoringofforest.activities.ShopActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {

    @BindView(R.id.logout)
    Button mLogout;
    @BindView(R.id.tel)
    TextView number;

    @OnClick(R.id.share)
    void share() {
        ShareActivity.show(Objects.requireNonNull(getContext()));
    }

    @OnClick(R.id.setting)
    void setting() {
        SettingActivity.show(Objects.requireNonNull(getContext()));
    }

    @OnClick(R.id.user_info)
    void update() {
        AccountInfoActivity.show(Objects.requireNonNull(getContext()));
    }

    @OnClick(R.id.shop)
    void shop() {
        ShopActivity.show(Objects.requireNonNull(getContext()));
    }

    @OnClick(R.id.call)
    void call() {
        //跳转拨号界面,传递电话号码
        String tel = number.getText().toString().trim();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        startActivity(intent);
    }

    @OnClick(R.id.about)
    void about() {
        AboutAppActivity.show(Objects.requireNonNull(getContext()));
    }

    /**
     * 退出登陆
     */
    @OnClick(R.id.logout)
    void logout() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                Objects.requireNonNull(getContext()), R.style.AlertDialog);

        builder.setTitle(R.string.label_account_logout)
                .setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Account.clear(Factory.application());
                        UserHelper.clear();
                        Factory.application().exit();
//                        AccountActivity.show(getContext());
//                        Objects.requireNonNull(getActivity()).finish();
                    }
                })
                .create();
        builder.show();
    }

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_personal;
    }
}
