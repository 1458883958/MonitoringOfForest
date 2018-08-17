package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.utils.DataCleanManager;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends ToolbarActivity {

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mCache.setText("0.0KB");
                    break;
                case 0x02:
                    break;
            }
            return true;
        }
    });

    @BindView(R.id.cache)
    TextView mCache;

    @OnClick(R.id.clean_cache)
    void clean() {
        boolean flag = DataCleanManager.cleanInternalCache(getApplicationContext());
        Message message = new Message();
        if (flag) {
            message.what = 0x01;
        } else
            message.what = 0x02;
        handler.sendMessage(message);
    }

    /**
     * 显示入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle(R.string.label_account_setting);
        // /data/data/com.wdl.monio.../cache
        File file = new File(this.getCacheDir().getPath());
        try {
            mCache.setText(DataCleanManager.getCacheSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_setting;
    }
}
