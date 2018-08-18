package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wdl.common.app.PresenterToolbarActivity;
import com.wdl.common.app.ToolbarActivity;
import com.wdl.factory.model.card.User;
import com.wdl.factory.presenter.account.UpdateContract;
import com.wdl.factory.presenter.account.UpdatePresenter;
import com.wdl.monitoringofforest.R;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressWarnings("unused")
public class UpdateInfoActivity extends PresenterToolbarActivity<UpdateContract.Presenter>
    implements UpdateContract.View{
    public static final int TYPE_UPDATE_NAME = 1;
    public static final int TYPE_UPDATE_MAIL = 2;
    public static final int TYPE_UPDATE_PSD = 3;
    public static final String TYPE_UPDATE = "TYPE_UPDATE";
    private int type;

    @BindView(R.id.mInfo_1)
    EditText mInfo_1;
    @BindView(R.id.mInfo_2)
    EditText mInfo_2;
    @BindView(R.id.submit)
    Button submit;

    @OnClick(R.id.submit)
    void submit(){
        String info = mInfo_1.getText().toString().trim();
        mPresenter.update(type,info);
    }
    /**
     * 显示入口
     *
     * @param context 上下文
     * @param type    更新的类型
     */
    public static void show(Context context, int type) {
        Intent intent = new Intent(context, UpdateInfoActivity.class);
        intent.putExtra(TYPE_UPDATE, type);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt(TYPE_UPDATE);
        return type == TYPE_UPDATE_NAME || type == TYPE_UPDATE_MAIL || type == TYPE_UPDATE_PSD;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        if (type == TYPE_UPDATE_NAME) {
            setTitle(R.string.title_update_name);
            mInfo_1.setHint(R.string.label_account_name);
        } else if (type==TYPE_UPDATE_MAIL){
            setTitle(R.string.title_update_mail);
            mInfo_1.setHint(R.string.label_account_mail);
        }else {
            setTitle(R.string.title_update_password);
            mInfo_1.setHint(R.string.label_account_old_password);
            mInfo_2.setVisibility(View.VISIBLE);
            mInfo_2.setHint(R.string.label_account_new_password);
        }
        //初始化 button对edit 监听
        initContent();
    }

    private void initContent() {
        mInfo_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                boolean needSend = !TextUtils.isEmpty(content);
                submit.setEnabled(needSend);
                submit.setClickable(needSend);
            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_update_info;
    }

    @Override
    protected UpdateContract.Presenter initPresenter() {
        return new UpdatePresenter(this);
    }

    @Override
    public void succeed(User user) {

    }
}
