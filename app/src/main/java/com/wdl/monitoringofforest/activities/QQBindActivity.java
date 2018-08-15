package com.wdl.monitoringofforest.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.qq.BindFragment;
import com.wdl.monitoringofforest.fragments.qq.BindRegFragment;

public class QQBindActivity extends ToolbarActivity {
    private static final String QQ_BIND_OR_REG = "QQ_BIND_OR_REG";
    public static final String QQ_USER_ID = "QQ_USER_ID";
    private static final int BIND = 1;
    private static final int BIND_REG = 2;

    private int type;
    private String userId;

    /**
     * 显示入口
     *
     * @param context 上下文
     * @param type    类型
     */
    public static void show(Context context, int type, String userId) {
        Intent intent = new Intent(context,QQBindActivity.class);
        intent.putExtra(QQ_BIND_OR_REG, type);
        intent.putExtra(QQ_USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt(QQ_BIND_OR_REG);
        userId = bundle.getString(QQ_USER_ID);
        return type == BIND || type == BIND_REG || !TextUtils.isEmpty(userId);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_qqbind;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Fragment fragment = null;
        if (type == BIND) {
            fragment = BindFragment.newInstance();
        } else if (type == BIND_REG) {
            fragment = BindRegFragment.newInstance();
        }
        setTitle(R.string.label_add);
        Bundle bundle = new Bundle();
        bundle.putString(QQ_USER_ID,userId);
        assert fragment != null;
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, fragment)
                .commit();
    }
}
