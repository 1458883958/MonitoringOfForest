package com.wdl.monitoringofforest;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;

import com.wdl.common.app.Activity;
import com.wdl.common.app.Application;
import com.wdl.factory.persistence.Account;
import com.wdl.monitoringofforest.activities.AccountActivity;
import com.wdl.monitoringofforest.activities.MainActivity;
import com.wdl.monitoringofforest.fragments.assist.PermissionsFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

/**
 * 引导页面
 */
public class LaunchActivity extends Activity {

    private ColorDrawable colorDrawable;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //动画进行50%时,获取个推的pushID
        startAnim(0.5f, new Runnable() {
            @Override
            public void run() {
                receiverPushID();
            }
        });
    }

    /**
     * 获取个推的pushID
     */
    private void receiverPushID() {
        //如果登陆
        if (!Account.isLogin()) {
            skip();
            return;
        } else {
            if (!TextUtils.isEmpty(Account.getPushId())) {
                skip();
                return;
            }
        }
        //循环等待
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                receiverPushID();
            }
        }, 500);
    }

    private void skip() {
        startAnim(1f, new Runnable() {
            @Override
            public void run() {
                toApp();
            }
        });
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //获取根布局
        View root = findViewById(R.id.activity_launch);
        //获取颜色
        int color = UiCompat.getColor(getResources(), R.color.colorPrimary);
        ColorDrawable drawable = new ColorDrawable(color);
        //给根布局设置drawable
        root.setBackground(drawable);
        colorDrawable = drawable;

    }

    /**
     * 界面跳转的判断
     */
    private void toApp() {
        //已获取全部权限
        if (PermissionsFragment.haveAllPermission(this, getSupportFragmentManager())) {
            if (!Account.isLogin())
                MainActivity.show(this);
            else
                AccountActivity.show(this);
            finish();
        } else {
            Application.showToast("-----");
        }
    }


    /**
     * 给背景设置一个动画
     *
     * @param currentProgress 进度颜色
     * @param endCallback     endCallback
     */
    @SuppressWarnings("unchecked")
    private void startAnim(float currentProgress, final Runnable endCallback) {
        //最终的颜色
        int finalColor = UiCompat.getColor(getResources(), R.color.white);
        //运算当前进度的颜色
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        //currentProgress当前进度
        //colorDrawable.getColor()开始时的颜色
        //finalColor 最后的颜色
        int currentColor = (int) argbEvaluator
                .evaluate(currentProgress, colorDrawable.getColor(), finalColor);
        //构建一个属性动画
        ValueAnimator animator = ObjectAnimator.ofObject(this, property
                , argbEvaluator, currentColor);
        //设置时间
        animator.setDuration(1500);
        //开始结束值
        animator.setIntValues(colorDrawable.getColor(), currentColor);
        //设置监听
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //结束时触发
                endCallback.run();
            }
        });
        //启动
        animator.start();

    }

    private final Property<LaunchActivity, Object> property =
            new Property<LaunchActivity, Object>(Object.class, "color") {
                @Override
                public void set(LaunchActivity object, Object value) {
                    object.colorDrawable.setColor((Integer) value);
                }

                @Override
                public Object get(LaunchActivity object) {
                    return object.colorDrawable.getColor();
                }
            };
}
