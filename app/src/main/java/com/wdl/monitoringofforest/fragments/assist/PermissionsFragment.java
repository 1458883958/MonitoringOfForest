package com.wdl.monitoringofforest.fragments.assist;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdl.common.app.Application;
import com.wdl.common.widget.TransStateBottomSheetDialog;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.main.PersonalFragment;

import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权限引导的fragment
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class PermissionsFragment extends BottomSheetDialogFragment
        implements EasyPermissions.PermissionCallbacks {
    //权限申请的请求码
    private static final int RC = 0x0100;

    public PermissionsFragment() {
        // Required empty public constructor
    }

    /**
     * 刷新状态
     */
    @Override
    public void onResume() {
        super.onResume();
        refreshState(getView());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStateBottomSheetDialog(Objects.requireNonNull(getContext()));
    }

    /**
     * 判断是否获取全部权限,以及显示
     *
     * @param context         上下文
     * @param fragmentManager manager
     * @return true:获取全部权限
     */
    public static boolean haveAllPermission(Context context, FragmentManager fragmentManager) {
        boolean haveAll = haveNetPermission(context)
                && haveCameraPermission(context)
                && haveReadAudioPermission(context)
                && haveReadPermission(context)
                && haveWritePermission(context)
                && havePhoneStatePermission(context)
                && haveLocationPermission(context);
        if (!haveAll) {
            //跳转申请权限的fragment--PermissionFragment
            show(fragmentManager);
        }
        return haveAll;
    }

    /**
     * 显示权限申请的页面
     *
     * @param fragmentManager manager
     */
    private static void show(FragmentManager fragmentManager) {
        new PermissionsFragment().show(fragmentManager, PersonalFragment.class.getName());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_permissions, container, false);
        //授权按钮的监听
        root.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请权限
                requestPerms();
            }
        });
        return root;
    }

    /**
     * 申请权限的方法
     */
    @AfterPermissionGranted(RC)
    private void requestPerms() {
        String[] perms = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };
        //判断权限
        if (EasyPermissions.hasPermissions(Objects.requireNonNull(getContext()), perms)) {
            //提示
            Application.showToast(R.string.label_permission_ok);
            //fragment调用getView()获取根布局必须在onCreateView之后
            //刷新界面的状态
            refreshState(getView());
        } else {
            //申请权限
            EasyPermissions.requestPermissions(this,
                    getString(R.string.title_assist_permissions), RC, perms);
        }
    }

    /**
     * 刷新界面的状态
     *
     * @param view fragment
     */
    private void refreshState(View view) {
        if (view == null) return;
        Context context = getContext();
        view.findViewById(R.id.iv_state_permission_network)
                .setVisibility(haveNetPermission(context) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.iv_state_permission_read)
                .setVisibility(haveReadPermission(context) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.iv_state_permission_write)
                .setVisibility(haveWritePermission(context) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.iv_state_permission_record_audio)
                .setVisibility(haveReadAudioPermission(context) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.iv_state_permission_camera)
                .setVisibility(haveCameraPermission(context) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.iv_state_permission_phone_state)
                .setVisibility(havePhoneStatePermission(context) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.iv_state_permission_location)
                .setVisibility(haveLocationPermission(context) ? View.VISIBLE : View.GONE);
    }

    /**
     * 判断是否获取网络权限
     *
     * @param context 上下文
     * @return true代表已经获取权限
     */
    private static boolean haveNetPermission(Context context) {
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 判断是否获取外部写入权限
     *
     * @param context 上下文
     * @return true:以获取
     */
    private static boolean haveWritePermission(Context context) {
        String[] perms = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 判断是否获取外部读取权限
     *
     * @param context 上下文
     * @return true:以获取
     */
    private static boolean haveReadPermission(Context context) {
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 判断是否获取audio权限
     *
     * @param context 上下文
     * @return true:以获取
     */
    private static boolean haveReadAudioPermission(Context context) {
        String[] perms = new String[]{
                Manifest.permission.RECORD_AUDIO
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 判断是否获取手机状态权限
     *
     * @param context 上下文
     * @return true:以获取
     */
    private static boolean havePhoneStatePermission(Context context) {
        String[] perms = new String[]{
                Manifest.permission.READ_PHONE_STATE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 判断是否获取定位权限
     *
     * @param context 上下文
     * @return true:以获取
     */
    private static boolean haveLocationPermission(Context context) {
        String[] perms = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 判断是否获取相机权限
     *
     * @param context 上下文
     * @return true:以获取
     */
    private static boolean haveCameraPermission(Context context) {
        String[] perms = new String[]{
                Manifest.permission.CAMERA
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 成功的回调
     *
     * @param requestCode 请求码
     * @param perms       成功的权限
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    /**
     * 申请失败的回调
     *
     * @param requestCode 请求码
     * @param perms       失败的权限
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //弹窗提示
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .build()
                    .show();
        }
    }

    /**
     * 将权限请求的结果交给EasyPermissions处理
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
