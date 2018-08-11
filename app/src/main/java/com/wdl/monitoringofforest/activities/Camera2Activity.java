package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;

import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.camera2.Camera2Fragment;

public class Camera2Activity extends ToolbarActivity {


    /**
     * 显示入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, Camera2Activity.class));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container, Camera2Fragment.newInstance())
                .commit();

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_camera2;
    }
}
