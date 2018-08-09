package com.wdl.monitoringofforest.fragments.search;


import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wdl.common.app.Application;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.factory.presenter.search.PiContract;
import com.wdl.factory.presenter.search.PiPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.ScanActivity;
import com.wdl.utils.LogUtils;

import net.qiujuer.genius.ui.widget.Button;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 二维码扫描并添加设备
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class ScanQRCodeFragment extends
        PresenterFragment<PiContract.Presenter> implements PiContract.View{

    @BindView(R.id.et_pid)
    EditText editPid;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.scan)
    ImageView scan;
    @BindView(R.id.submit)
    Button submit;

    public ScanQRCodeFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_scan_qrcode;
    }


    /**
     * 绑定
     */
    @OnClick(R.id.submit)
    void submit() {
        String pId = editPid.getText().toString().trim();
        String pPassword = mPassword.getText().toString().trim();
        mPresenter.bind(Integer.valueOf(pId),pPassword);
    }


    /**
     * 进行二维码扫描
     */
    @OnClick(R.id.scan)
    void scan() {
        IntentIntegrator
                .forSupportFragment(ScanQRCodeFragment.this)
                .setCaptureActivity(ScanActivity.class)
                .initiateScan();
    }

    /**
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        Intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                LogUtils.e("result.getContents()");
                Application.showToast("扫描结果为空。。");
            } else {
                LogUtils.e("" + result.getContents());
                //获取扫描结果
                editPid.setText(result.getContents());

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected PiContract.Presenter initPresenter() {
        return new PiPresenter(this);
    }

    /**
     * 绑定成功
     */
    @Override
    public void bindSucceed() {
        Objects.requireNonNull(getActivity()).finish();
    }
}
