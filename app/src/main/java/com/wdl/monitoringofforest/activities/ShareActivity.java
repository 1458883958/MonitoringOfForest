package com.wdl.monitoringofforest.activities;

import android.content.Context;
import android.content.Intent;

import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.set_info.ShareFragment;

public class ShareActivity extends ToolbarActivity {

    /**
     * 显示入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context,ShareActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle(R.string.title_share);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, ShareFragment.newInstance())
                .commit();
    }
}
