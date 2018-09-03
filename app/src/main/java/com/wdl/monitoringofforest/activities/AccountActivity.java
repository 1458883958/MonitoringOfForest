package com.wdl.monitoringofforest.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.wdl.common.app.Activity;
import com.wdl.common.app.Fragment;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.account.AccountTrigger;
import com.wdl.monitoringofforest.fragments.account.LoginFragment;
import com.wdl.monitoringofforest.fragments.account.RegisterFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

/**
 * 账户注册/登陆调度的activity
 */
@SuppressWarnings("unused")
public class AccountActivity extends Activity implements AccountTrigger {

    private Fragment mCurrentFragment;
    private Fragment loginFragment;
    private Fragment registerFragment;

    @BindView(R.id.imageView)
    ImageView mBg;

    /**
     * 显示入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //默认是登陆fragment
        mCurrentFragment = loginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurrentFragment)
                .commit();

        //初始化背景
        Glide.with(this)
                .load(R.drawable.bg_forest)
                .centerCrop()
                .into(new ViewTarget<ImageView, GlideDrawable>(mBg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //拿到当前drawable
                        Drawable drawable = resource.getCurrent();
                        //使用适配类进行包装
                        drawable = DrawableCompat.wrap(drawable);
                        //设置着色效果以及颜色   蒙板模式
//                        drawable.setColorFilter(UiCompat.getColor(getResources(), R.color.green_a400),
//                                PorterDuff.Mode.SCREEN);
                        //设置进imageView
                        this.view.setImageDrawable(drawable);
                    }
                });
    }

    /**
     * 切换方法
     */
    @Override
    public void triggerView() {
        //要切换的页面
        Fragment fragment;
        //当前页面为登陆--->注册
        if (mCurrentFragment == loginFragment) {
            //注册页面未初始化,进行初始化
            if (registerFragment == null) {
                registerFragment = new RegisterFragment();
            }
            //赋值
            fragment = registerFragment;
        } else {
            //当前是注册--->登陆
            //登陆已初始化,默认加载的就是登陆
            fragment = loginFragment;
        }

        mCurrentFragment = fragment;
        //替换
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, fragment)
                .commit();
    }
}
