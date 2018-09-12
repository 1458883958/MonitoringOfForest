package com.wdl.monitoringofforest.activities;

import android.content.Context;
import android.content.Intent;

import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.notice.NoticeFragment;

public class NoticeActivity extends ToolbarActivity {

    public static void show(Context context){
        context.startActivity(new Intent(context,NoticeActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle(R.string.title_notice);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, NoticeFragment.newInstance())
                .commit();
    }
}
