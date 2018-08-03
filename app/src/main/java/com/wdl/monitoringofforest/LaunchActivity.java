package com.wdl.monitoringofforest;


import com.wdl.common.common.app.Activity;
import com.wdl.common.common.app.Application;
import com.wdl.monitoringofforest.activities.AccountActivity;
import com.wdl.monitoringofforest.activities.MainActivity;
import com.wdl.monitoringofforest.fragments.assist.PermissionsFragment;
/**
 * 引导页面
 */
public class LaunchActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        toApp();
    }

    /**
     * 界面跳转的判断
     */
    private void toApp() {
        //已获取全部权限
        if (PermissionsFragment.haveAllPermission(this,getSupportFragmentManager())){
            boolean isLogin = false;
            if (isLogin)
                MainActivity.show(this);
            else
                AccountActivity.show(this);
            finish();
        }else {
            Application.showToast("-----");
        }
    }
}
