package com.wdl.monitoringofforest.activities;


import android.content.Context;
import android.content.Intent;

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
