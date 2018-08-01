package com.wdl.monitoringofforest.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;


/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.monitoringofforest.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/1 13:56
 * 描述：    解决对fragment的调度与重用问题,使得fragment切换最优
 */
@SuppressWarnings("unused")
public class NavHelper<T> {
    //所有tab集合
    private SparseArray<Tab<T>> tabs = new SparseArray<>();
    //上下文
    private Context context;
    //当前tab
    private Tab<T> currentTab;
    //fragment管理器
    private FragmentManager manager;
    //布局id
    private int containerId;
    //切换监听回调
    private OnTabChangedListener<T> listener;

    public NavHelper(Context context,
                     FragmentManager manager,
                     int containerId,
                     OnTabChangedListener<T> listener) {
        this.context = context;
        this.manager = manager;
        this.containerId = containerId;
        this.listener = listener;
    }

    /**
     * 添加tab
     *
     * @param menuId key
     * @param tab    要添加的tab
     * @return NavHelper<T>
     */
    public NavHelper<T> addTab(int menuId, Tab<T> tab) {
        tabs.put(menuId, tab);
        return this;
    }

    /**
     * 获取当前tab
     *
     * @return Tab<T>
     */
    public Tab<T> getCurrentTab() {
        return currentTab;
    }

    /**
     * 执行点击菜单的操作
     *
     * @param itemId 子项id
     * @return true可处理, false不可处理
     */
    public boolean performClickMenu(int itemId) {
        //从所有的tab中查找当前的点击的项,有则处理，没有则返回false
        Tab<T> tab = tabs.get(itemId);
        if (tab != null) {
            doSelected(tab);
            return true;
        }
        return false;
    }

    /**
     * 进行tab的选择操作
     *
     * @param tab 选择的项
     */
    private void doSelected(Tab<T> tab) {
        Tab<T> oldTab = null;
        //如果当前的tab存在
        if (currentTab != null) {
            //当前的变为旧的
            oldTab = currentTab;
            //如果当前的和点击的是同一个tab则不作处理
            if (currentTab == tab) {
                notifyReSelected(tab);
                return;
            }
        }
        //赋值并切换
        currentTab = tab;
        doTabChanged(oldTab, currentTab);
    }

    /**
     * 切换tab,进行fragment的调度
     *
     * @param oldTab 旧的项
     * @param newTab 新的项
     */
    private void doTabChanged(Tab<T> oldTab, Tab<T> newTab) {
        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = manager.beginTransaction();
        //如果旧的tab存在
        if (oldTab != null) {
            //fragment的缓存也存在
            if (oldTab.fragment != null) {
                //移除到缓存空间中
                ft.detach(oldTab.fragment);
            }
        }
        //存在点击的项
        if (newTab != null) {
            //缓存为空
            if (newTab.fragment == null) {
                //首次新建实例
                Fragment fragment = Fragment
                        .instantiate(context, newTab.tClass.getName(), null);
                //进行缓存
                newTab.fragment = fragment;
                //添加到事物中
                ft.add(containerId, fragment, newTab.tClass.getName());
            } else {
                //如果缓存存在,将缓存移到界面中显示
                ft.attach(newTab.fragment);
            }
        }
        //提交事物,通知回调
        ft.commit();
        notifySelected(oldTab, newTab);
    }

    /**
     * 通知回调
     *
     * @param oldTab 旧
     * @param newTab 新
     */
    private void notifySelected(Tab<T> oldTab, Tab<T> newTab) {
        if (listener != null)
            listener.onTabChanged(oldTab, newTab);
    }

    /**
     * 重复点击
     *
     * @param tab Tab
     */
    private void notifyReSelected(Tab<T> tab) {
        //TODO 重复点击
    }

    /**
     * 界面切换的回调接口
     *
     * @param <T> 泛型
     */
    public interface OnTabChangedListener<T> {
        /**
         * @param oldTab 旧
         * @param newTab 新
         */
        void onTabChanged(Tab<T> oldTab, Tab<T> newTab);
    }

    /**
     * static 避免循环使用,一个子项的信息
     *
     * @param <T> 泛型
     */
    public static class Tab<T> {
        //fragment类的信息,用来实例化fragment
        public Class<? extends Fragment> tClass;
        //额外信息(标题),动画效果等
        public T extra;
        //缓存
        Fragment fragment;

        public Tab(Class<? extends Fragment> tClass, T extra) {
            this.tClass = tClass;
            this.extra = extra;
        }
    }
}
