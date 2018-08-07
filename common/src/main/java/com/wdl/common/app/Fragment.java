package com.wdl.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdl.common.widget.convention.PlaceHolderView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.app
 * 创建者：   wdl
 * 创建时间： 2018/7/31 13:49
 * 描述：    基础Fragment的封装
 */
@SuppressWarnings("unused")
public abstract class Fragment extends android.support.v4.app.Fragment {

    protected PlaceHolderView placeHolderView;
    protected Unbinder binder;
    protected View mRoot;
    //是否第一次初始化数据
    protected boolean isFirstInit = true;

    /**
     * 设置占位布局
     *
     * @param placeHolderView 占位布局
     */
    public void setPlaceHolderView(PlaceHolderView placeHolderView) {
        this.placeHolderView = placeHolderView;
    }

    /**
     * 此方法在onCreateView之前执行
     *
     * @param context 上下文
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化参数,不可控
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            int layoutId = getContentLayoutId();
            //初始化根布局,不把root放入container,在return时才放入container
            View root = inflater.inflate(layoutId, container, false);
            initWidget(root);
            mRoot = root;
        } else {
            //判断布局的父容器是否存在
            if (mRoot.getParent() != null) {
                //将布局从父容器中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //判断是否是第一次加载数据
        if (isFirstInit) {
            isFirstInit = false;
            onFirstInit();
        }
        //view加载完成时加载数据
        initData();
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 第一次初始化数据
     */
    protected void onFirstInit() {
    }

    /**
     * 将布局绑定到ButterKnife
     *
     * @param root View
     */
    protected void initWidget(View root) {
        binder = ButterKnife.bind(this, root);
    }

    /**
     * 获取ContentLayoutId
     *
     * @return layoutId
     */
    protected abstract int getContentLayoutId();


    /**
     * 初始化相关参数
     *
     * @param bundle 参数
     */
    protected void initArgs(Bundle bundle) {
    }

    /**
     * 返回按键触发时调用
     *
     * @return true:代表已处理返回,activity不用finish
     * false:代表未处理,activity走自己的返回逻辑
     */
    protected boolean onBackPressed() {
        return false;
    }

}
