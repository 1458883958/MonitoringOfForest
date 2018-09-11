package com.wdl.monitoringofforest.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.wdl.common.app.Activity;
import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.utils.LogUtils;

import butterknife.BindView;

public class PreviewNoticeActivity extends ToolbarActivity {

    @BindView(R.id.tv_content)
    TextView mContent;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_preview_notice;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("公告");
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        String title = intent.getStringExtra("title");
        LogUtils.e("preview:"+content+" "+title);
        if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(title))
            mContent.setText(title + "\n" + content);
        else
            LogUtils.e("空");
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String host = uri.getHost();
                String dataString = intent.getDataString();
                String id = uri.getQueryParameter("id");
                String path = uri.getPath();
                String path1 = uri.getEncodedPath();
                String queryString = uri.getQuery();
                LogUtils.e(host+" "+dataString+" "+id+" "+path+" "+path1+" "+queryString);
            }
        }
    }
}
