package com.wdl.monitoringofforest;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.wdl.common.app.Activity;
import com.wdl.common.app.Application;
import com.wdl.factory.Factory;
import com.wdl.factory.persistence.Account;
import com.wdl.monitoringofforest.activities.AccountActivity;
import com.wdl.monitoringofforest.activities.MapActivity;
import com.wdl.monitoringofforest.fragments.assist.PermissionsFragment;
import com.wdl.monitoringofforest.service.LongRunService;
import com.wdl.monitoringofforest.service.MessageIntentService;
import com.wdl.utils.ApkVersionCodeUtils;
import com.wdl.utils.LogUtils;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

/**
 * 引导页面
 */
public class LaunchActivity extends Activity {

    @BindView(R.id.versionName)
    TextView versionName;
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

    @Override
    protected void initBefore() {
        super.initBefore();
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), MessageIntentService.class);
        Intent intent = new Intent(this, LongRunService.class);
        startService(intent);
        boolean isOpen = isOPen(this);
        LogUtils.e("isOPen:"+isOpen);
        if (!isOpen){
            App.showToast("请前往开启位置信息");
        }

    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        assert locationManager != null;
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }


    /**
     * 强制帮用户打开GPS
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取个推的pushID
     */
    private void receiverPushID() {
        //如果登陆
        if (Account.isLogin()) {
            //判断是否绑定pushId
            if (Account.isBind()){
                skip();
                return;
            }
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

        //设置版本号
        String verName = ApkVersionCodeUtils.getVersionName(this);
        versionName.setText(verName);

    }

    /**
     * 界面跳转的判断
     */
    private void toApp() {
        //已获取全部权限
        if (PermissionsFragment.haveAllPermission(this, getSupportFragmentManager())) {
            if (Account.isLogin())
                MapActivity.show(LaunchActivity.this);
            else
                AccountActivity.show(LaunchActivity.this);
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
