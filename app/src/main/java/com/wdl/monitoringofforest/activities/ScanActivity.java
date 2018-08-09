package com.wdl.monitoringofforest.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.monitoringofforest.activities
 * 创建者：   wdl
 * 创建时间： 2018/8/8 14:20
 * 描述：    二维码扫描ac
 */
public class ScanActivity extends ToolbarActivity {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        barcodeScannerView = findViewById(R.id.dbv_custom);
        setTitle(R.string.label_qr_code);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
