package com.wdl.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.wdl.common.widget.convention.PlaceHolderView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.app
 * 创建者：   wdl
 * 创建时间： 2018/7/31 13:36
 * 描述：    基础封装的Activity
 */

@SuppressWarnings("unused")
public abstract class Activity extends AppCompatActivity {
    protected PlaceHolderView placeHolderView;

    /**
     * 设置占位布局
     *
     * @param placeHolderView 占位布局
     */
    public void setPlaceHolderView(PlaceHolderView placeHolderView) {
        this.placeHolderView = placeHolderView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化窗口
        initWindow();
        //如果初始化参数成功,进入初始化
        if (initArgs(getIntent().getExtras())) {
            //得到当前界面的资源文件的Id
            int layoutId = getContentLayoutId();
            //设置id
            setContentView(layoutId);
            initBefore();
            //初始化控件
            initWidget();
            //初始化数据
            initData();
        } else finish();
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化控件,绑定ButterKnife
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化之前
     */
    protected void initBefore() {
    }

    /**
     * 得到当前界面的资源文件的Id
     *
     * @return 布局id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化窗口
     */
    protected void initWindow() {
    }

    /**
     * 初始化参数
     *
     * @param bundle Bundle参数
     * @return true代表初始化成功
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 当点击当前界面导航返回时,Finish当前页面
     *
     * @return super.onSupportNavigateUp()
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 返回按键的处理
     */
    @Override
    public void onBackPressed() {
        //得到当前activity下的所有fragment
        List<android.support.v4.app.Fragment> fragmentList =
                getSupportFragmentManager().getFragments();
        //为空判断
        if (fragmentList != null && fragmentList.size() > 0) {
            //取出我们能够处理的fragment,即我们自定义的fragment
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof com.wdl.common.app.Fragment) {
                    //判断是否已经处理过onBackPressed
                    if (((com.wdl.common.app.Fragment) fragment).onBackPressed()) {
                        //处理过 直接return
                        return;
                    }
                }
            }

        }
        super.onBackPressed();
        finish();
    }
}
