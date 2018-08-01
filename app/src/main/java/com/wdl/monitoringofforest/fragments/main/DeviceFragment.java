package com.wdl.monitoringofforest.fragments.main;


import com.wdl.common.common.app.Fragment;
import com.wdl.common.common.widget.PortraitView;
import com.wdl.monitoringofforest.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备模块
 */
public class DeviceFragment extends Fragment{

    @BindView(R.id.mPortrait)
    PortraitView mPortrait;
    public DeviceFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_device;
    }

    @OnClick(R.id.mPortrait)
    void selectPortrait(){

    }
}
