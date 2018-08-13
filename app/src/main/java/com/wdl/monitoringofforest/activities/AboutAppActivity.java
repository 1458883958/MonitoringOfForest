package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;

import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.set_info.AboutFragment;

public class AboutAppActivity extends ToolbarActivity {


    /**
     * 显示入口
     * @param context 上下文
     */
    public static void show(Context context){
        context.startActivity(new Intent(context,AboutAppActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_about_app;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle(R.string.title_about);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, AboutFragment.newInstance())
                .commit();
    }
}
