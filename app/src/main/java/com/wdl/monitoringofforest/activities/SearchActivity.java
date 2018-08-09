package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

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
        LogUtils.e("typetype:"+type);
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
        if (type==TYPE_SCAN){
            setTitle(R.string.title_add_device);
            ScanQRCodeFragment scanFragment = new ScanQRCodeFragment();
            fragment = scanFragment;
        }else if (type==TYPE_FOLLOW){
            SearchUserFragment searchFragment = new SearchUserFragment();
            fragment = searchFragment;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container,fragment)
                .commit();
    }
}
