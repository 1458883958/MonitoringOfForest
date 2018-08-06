package com.wdl.monitoringofforest.fragments.main;

import android.support.v7.widget.RecyclerView;

import com.wdl.common.common.app.Fragment;
import com.wdl.common.common.widget.EmptyView;
import com.wdl.monitoringofforest.R;

import butterknife.BindView;

/**
 * 设备模块
 */
@SuppressWarnings("unused")
public class DeviceFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty)
    EmptyView emptyView;
    public DeviceFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_device;
    }

}
