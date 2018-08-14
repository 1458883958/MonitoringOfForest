package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.wdl.common.app.Fragment;
import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.feedback.FeedbackFragment;
import com.wdl.monitoringofforest.fragments.feedback.FeedbackHisFragment;
import com.wdl.monitoringofforest.fragments.feedback.FeedbackTrigger;

@SuppressWarnings("unused")
public class FeedbackActivity extends ToolbarActivity implements FeedbackTrigger{

    private Fragment mCurrentFragment;
    private Fragment addFeedbackFragment;
    private Fragment hisFeedbackFragment;

    /**
     * 显示入口
     * @param context 上下文
     */
    public static void show(Context context){
        context.startActivity(new Intent(context,FeedbackActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle(R.string.title_feedback);
        mCurrentFragment = addFeedbackFragment = FeedbackFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, mCurrentFragment)
                .commit();
    }

    /**
     * 初始化menu
     * @param menu Menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feed_back,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.go_feedback)
            triggerView();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void triggerView() {
        Fragment fragment;
        //当前为添加反馈的fragment 切换为历史反馈
        if (mCurrentFragment==addFeedbackFragment){
            //历史反馈没有实例化
            if (hisFeedbackFragment==null){
                hisFeedbackFragment = FeedbackHisFragment.newInstance();
            }
            fragment = hisFeedbackFragment;
        }else {
            fragment = addFeedbackFragment;
        }
        mCurrentFragment = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, fragment)
                .commit();
    }
}
