package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdl.common.common.app.Activity;
import com.wdl.common.common.widget.PortraitView;
import com.wdl.monitoringofforest.R;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;


/**
 * 主页面
 */
@SuppressWarnings("unused")
public class MainActivity extends Activity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.appBar)
    View appBar;
    @BindView(R.id.mPortrait)
    PortraitView mPortrait;
    @BindView(R.id.mTitle)
    TextView mTitle;
    @BindView(R.id.mSearch)
    ImageView mSearch;
    @BindView(R.id.lay_container)
    FrameLayout mContainer;
    @BindView(R.id.fab_action)
    FloatActionButton mFab;
    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;


    /**
     * 显示的入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        return super.initArgs(bundle);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 底部导航栏的点击事件
     *
     * @param item MenuItem单击的子项
     * @return false:不做任何改变,true:切换
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
