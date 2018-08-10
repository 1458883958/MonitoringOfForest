package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wdl.common.app.Activity;
import com.wdl.common.widget.PortraitView;
import com.wdl.factory.persistence.Account;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.main.ContactFragment;
import com.wdl.monitoringofforest.fragments.main.DealFragment;
import com.wdl.monitoringofforest.fragments.main.DeviceFragment;
import com.wdl.monitoringofforest.fragments.main.PersonalFragment;
import com.wdl.monitoringofforest.helper.NavHelper;
import com.wdl.utils.LogUtils;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 主页面
 */
@SuppressWarnings("unused")
public class MainActivity extends Activity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer> {

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
    //底部导航栏的工具类
    private NavHelper<Integer> helper;


    /**
     * 导航栏搜索按钮点击事件
     */
    @OnClick(R.id.mSearch)
    void search() {
        int type = Objects.equals(helper.getCurrentTab().extra, R.string.title_device) ?
                SearchActivity.TYPE_SCAN : SearchActivity.TYPE_FOLLOW;
        LogUtils.e("type:"+type+" helper.getCurrentTab().extra:"+helper.getCurrentTab().extra);
        SearchActivity.show(this, type);
    }

    @OnClick(R.id.mPortrait)
    void portraitClick(){
        PersonalActivity.show(this,Account.getUserId());
    }


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
        //初始化工具类
        helper = new NavHelper<>(this, getSupportFragmentManager(),
                R.id.lay_container, this);
        //添加素有子项
        helper.addTab(R.id.action_device, new NavHelper.Tab<>(DeviceFragment.class, R.string.title_device))
                .addTab(R.id.action_deal, new NavHelper.Tab<>(DealFragment.class, R.string.title_deal))
                .addTab(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact))
                .addTab(R.id.action_personal, new NavHelper.Tab<>(PersonalFragment.class, R.string.title_personal));
        //添加底部按钮点击监听
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //加载背景
        Glide.with(this)
                .load(R.drawable.bg_src_city)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(appBar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //this.view ==== appBar
                        this.view.setBackground(resource);
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        //从底部导航中接管menu,手动触发第一次点击
        Menu menu = bottomNavigationView.getMenu();
        //首次启动触发，会执行onNavigationItemSelected方法
        menu.performIdentifierAction(R.id.action_device, 0);

        //初始化头像
        mPortrait.setUp(Glide.with(this), Account.getUserDb());

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
        //将事件流交给工具类处理
        return helper.performClickMenu(item.getItemId());
    }


    /**
     * 处理后的回调
     *
     * @param oldTab 旧
     * @param newTab 新
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> oldTab, NavHelper.Tab<Integer> newTab) {
        //从额外的字段中取出标题
        mTitle.setText(newTab.extra);
        //对浮动按钮进行隐藏和显示的动画
        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra, R.string.title_device)) {
            //旋转角度
            rotation = -360;
            mFab.setImageResource(R.drawable.ic_audio);
            mSearch.setImageResource(R.drawable.ic_add_device);
        } else {
            //transY默认为0则显示
            //主界面时隐藏
            transY = Ui.dipToPx(getResources(), 76);
            mSearch.setImageResource(R.drawable.ic_search);
        }
        mFab.animate()
                //旋转
                .rotation(rotation)
                //Y轴位移
                .translationY(transY)
                //弹性差值
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                //时间
                .setDuration(480)
                .start();
    }
}
