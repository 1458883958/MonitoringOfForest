package com.wdl.monitoringofforest.activities;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.search.ScanQRCodeFragment;
import com.wdl.monitoringofforest.fragments.search.SearchUserFragment;
import com.wdl.utils.LogUtils;

/**
 * 标题栏icon的处理中心
 */
@SuppressWarnings("unused")
public class SearchActivity extends ToolbarActivity {
    private static final String EXTRA_TYPE = "EXTRA_TYPE";
    //进入扫描fragment
    public static final int TYPE_SCAN = 1;
    //进入联系人fragment
    public static final int TYPE_FOLLOW = 2;
    private int type;

    private SearchListener listener;

    /**
     * 显示入口
     *
     * @param context 上下文
     * @param type    类型
     */
    public static void show(Context context, int type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
    }

    /**
     * 获取type值
     *
     * @param bundle Bundle参数
     * @return boolean
     */
    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt(EXTRA_TYPE);
        LogUtils.e("typetype:" + type);
        return type == TYPE_SCAN || type == TYPE_FOLLOW;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Fragment fragment = null;
        if (type == TYPE_SCAN) {
            setTitle(R.string.title_add_device);
            ScanQRCodeFragment scanFragment = new ScanQRCodeFragment();
            fragment = scanFragment;
        } else if (type == TYPE_FOLLOW) {
            setTitle(R.string.title_search);
            SearchUserFragment searchFragment = new SearchUserFragment();
            fragment = searchFragment;
            listener = searchFragment;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载xml文件到menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        //找到SearchView
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        if (searchView != null) {
            //拿到搜索管理器
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            assert manager != null;
            searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            //设置监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                //点击提交按钮
                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(query);
                    return true;
                }

                //内容改变时,不会即时搜索,为空搜索
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (TextUtils.isEmpty(newText)) {
                        search("");
                        return true;
                    }
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 搜索发起点
     *
     * @param query 搜索内容
     */
    private void search(String query) {
        if (listener==null)
            return;
        listener.search(query);
    }

    /**
     * 搜索监听接口
     */
    public interface SearchListener {
        void search(String content);
    }
}
